package com.mailapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mailapp.R;
import com.mailapp.manager.UserDBManager;

public class LoginActivity extends Activity {

    private EditText edt_username;
    private EditText edt_pw;
    private TextView tv_register;
    private Button btn_login;
    private SharedPreferences login_sp;
    private UserDBManager mUserDBManager;
    private String pwd;
    private String name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestPermission();
        initSP();
        if (!name.isEmpty() && !pwd.isEmpty()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            initView();
            initClickListener();
            initUserDataManager();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void initUserDataManager() {
        if (mUserDBManager == null) {
            mUserDBManager = new UserDBManager(this);
            mUserDBManager.openDataBase();
        }
    }

    private void initClickListener() {
        tv_register.setOnClickListener(mListener);
        btn_login.setOnClickListener(mListener);
    }

    private void initSP() {
        login_sp = getSharedPreferences("userInfo", 0);
        name = login_sp.getString("USER_NAME", "");
        pwd = login_sp.getString("PASSWORD", "");
    }

    private void initView() {
        edt_username = findViewById(R.id.edt_username);
        edt_pw = findViewById(R.id.edt_reginster_pw);
        tv_register = findViewById(R.id.tv_register);
        btn_login = findViewById(R.id.btn_login);
    }

    OnClickListener mListener = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_register:
                    Intent intent_Login_to_Register = new Intent(LoginActivity.this, RegisterActivity.class);    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.btn_login:
                    login();
                    break;
            }
        }
    };

    public void login() {
        if (isUserNameAndPwdValid()) {
            String userName = edt_username.getText().toString().trim();
            String userPwd = edt_pw.getText().toString().trim();
            SharedPreferences.Editor editor = login_sp.edit();
            int result = mUserDBManager.findUserByNameAndPwd(userName, userPwd);
            if (result == 1) {//返回1说明用户名和密码均正确
                editor.putString("USER_NAME", userName);
                editor.putString("PASSWORD", userPwd);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
            } else if (result == 0) {
                Toast.makeText(this, "用户名或密码错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (edt_username.getText().toString().trim().equals("")) {
            Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_pw.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        if (mUserDBManager == null) {
            mUserDBManager = new UserDBManager(this);
            mUserDBManager.openDataBase();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (mUserDBManager != null) {
            mUserDBManager.closeDataBase();
            mUserDBManager = null;
        }
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
