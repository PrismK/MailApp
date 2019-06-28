package com.mailapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mailapp.R;
import com.mailapp.bean.MailInfo;
import com.mailapp.manager.MailManager;
import com.mailapp.utils.PermissionsUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteMailActivity extends AppCompatActivity {

    private EditText edt_mail_get_user;
    private EditText edt_mail_title;
    private EditText edt_mail_context;
    private ImageView imv_mail_img;
    private TextView tv_send;
    private ImageView imv_back;
    private String mailSendUser;
    private MailManager mailManager;

    private static final int IMAGE_REQUEST_CODE = 1;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writemail);
        initView();
        initSP();
        initMailManager();
    }

    private void initSP() {
        SharedPreferences login_sp = getSharedPreferences("userInfo", 0);
        mailSendUser = login_sp.getString("USER_NAME", "");
    }

    private void initMailManager() {
        if (mailManager == null) {
            mailManager = new MailManager(this);
            mailManager.openDataBase();
        }
    }

    private void initView() {
        edt_mail_get_user = (EditText) findViewById(R.id.edt_mail_get_user);
        edt_mail_title = (EditText) findViewById(R.id.edt_mail_title);
        edt_mail_context = (EditText) findViewById(R.id.edt_mail_context);
        imv_mail_img = (ImageView) findViewById(R.id.imv_mail_img);
        tv_send = (TextView) findViewById(R.id.tv_send);
        imv_back = (ImageView) findViewById(R.id.imv_back);

        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        imv_mail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
    }

    private void sendMail() {
        String mailGetUser = edt_mail_get_user.getText().toString().trim();
        String mailTitle = edt_mail_title.getText().toString().trim();
        String mailContext = edt_mail_context.getText().toString().trim();
        if (mailGetUser.isEmpty() || mailTitle.isEmpty()) {
            Toast.makeText(this, "收件人或主题不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String s = String.valueOf(selectedImage);
        MailInfo mailInfo = new MailInfo(mailSendUser, mailGetUser, mailTitle, mailContext,
                String.valueOf(selectedImage), time, 0);
        mailManager.insertMailInfo(mailInfo);
        Toast.makeText(this, "邮件发送成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onResume() {
        initMailManager();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mailManager != null) {
            mailManager.closeDataBase();
            mailManager = null;
        }
        super.onPause();
    }

    private void showListDialog() {
        final String[] items = {"进入相册选取"};
        AlertDialog.Builder listDialog = new AlertDialog.Builder(WriteMailActivity.this);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    selectPhoto();
                }
            }
        });
        listDialog.show();
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    //获取系统返回的照片的Uri
                    selectedImage = data.getData();
                    imv_mail_img.setImageURI(selectedImage);
                }
                break;
        }
    }


}
