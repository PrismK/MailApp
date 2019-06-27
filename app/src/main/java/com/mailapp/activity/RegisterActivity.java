package com.mailapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mailapp.R;
import com.mailapp.bean.UserData;
import com.mailapp.manager.UserDBManager;

public class RegisterActivity extends Activity {
    private EditText edt_register_username;
    private EditText edt_register_pw;
    private EditText edt_register_repw;
    private UserDBManager mUserDBManager;
    private Button btn_register_ok;
    private SharedPreferences login_sp;
    private SharedPreferences.Editor editor;
    private ImageView imv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initClickListener();
        initSP();
        initUserDataManager();
    }

    private void initSP() {
        login_sp = getSharedPreferences("userInfo", 0);
        editor = login_sp.edit();
    }

    private void initUserDataManager() {
        if (mUserDBManager == null) {
            mUserDBManager = new UserDBManager(this);
            mUserDBManager.openDataBase();
        }
    }

    private void initClickListener() {
        btn_register_ok.setOnClickListener(m_register_Listener);
        imv_back.setOnClickListener(m_register_Listener);
    }

    private void initView() {
        edt_register_username = findViewById(R.id.edt_register_username);
        edt_register_pw = findViewById(R.id.edt_register_pw);
        edt_register_repw = findViewById(R.id.edt_register_repw);
        btn_register_ok = findViewById(R.id.btn_register_ok);
        imv_back = findViewById(R.id.imv_back);
    }

    View.OnClickListener m_register_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_register_ok:
                    register_check();
                    break;
                case R.id.imv_back:
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
            }
        }
    };

    /**
     * 验证手机号格式正确性
     */
    public static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[4578]"代表第二位可以为3、4、5、7、8中的一个
        // d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        return mobiles.matches(telRegex);
    }

    public void register_check() {
        if (isUserNameAndPwdValid()) {
            String userName = edt_register_username.getText().toString().trim();
            String userPwd = edt_register_pw.getText().toString().trim();
            if (!isMobileNO(userName)) {
                Toast.makeText(this, "手机号码格式不正确！", Toast.LENGTH_SHORT).show();
                return;
            }
            String userPwdCheck = edt_register_repw.getText().toString().trim();
            //检查用户是否存在
            int count = mUserDBManager.findUserByName(userName);
            //用户已经存在时返回，给出提示文字
            if (count > 0) {
                Toast.makeText(this, "用户已存在！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userPwd.equals(userPwdCheck) == false) {
                Toast.makeText(this, "两次输入的密码不相同！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                UserData mUser = new UserData(userName, userPwd);
                mUserDBManager.openDataBase();
                long flag = mUserDBManager.insertUser(mUser);
                if (flag == -1) {
                    Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                    editor.putString("USER_NAME", userName);
                    editor.putString("PASSWORD", userPwd);
                    editor.commit();
                    Intent intent_Register_to_Login = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent_Register_to_Login);
                    finish();
                }
            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (edt_register_username.getText().toString().trim().equals("")) {
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_register_pw.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_register_repw.getText().toString().trim().equals("")) {
            Toast.makeText(this, "两次输入的密码不相同！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}