package com.mailapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mailapp.R;
import com.mailapp.bean.MailInfo;

import java.util.ArrayList;
import java.util.List;

public class MailDetailActivity extends AppCompatActivity {

    private String mailSendUserName;
    private String mailGetUserName;
    private String mailTitle;
    private String mailContext;
    private String mailImgUrl;
    private String mailSendTime;
    private TextView tv_mail_send_user;
    private TextView tv_mail_get_user;
    private TextView tv_mail_send_time;
    private TextView tv_title;
    private TextView tv_context;
    private TextView tv_mail_img;
    private ImageView imv_mail_img;
    private TextView tv_page_title;
    private int isSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maildetail);
        initIntent();
        initView();
    }

    private void initView() {
        tv_page_title = (TextView) findViewById(R.id.tv_page_title);
        tv_mail_send_user = (TextView) findViewById(R.id.tv_mail_send_user);
        tv_mail_get_user = (TextView) findViewById(R.id.tv_mail_get_user);
        tv_mail_send_time = (TextView) findViewById(R.id.tv_mail_send_time);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_context = (TextView) findViewById(R.id.tv_context);
        tv_mail_img = (TextView) findViewById(R.id.tv_mail_img);
        imv_mail_img = (ImageView) findViewById(R.id.imv_mail_img);
        if (isSend == 1) {
            tv_page_title.setText("已发送的邮件");
        }
        tv_mail_send_user.setText(mailSendUserName);
        tv_mail_get_user.setText(mailGetUserName);
        tv_mail_send_time.setText(mailSendTime);
        tv_title.setText(mailTitle);
        tv_context.setText(mailContext);
        if (mailImgUrl == null) {
            tv_mail_img.setVisibility(View.GONE);
            imv_mail_img.setVisibility(View.GONE);
        } else {
            imv_mail_img.setImageURI(Uri.parse(mailImgUrl));
        }
    }

    private void initIntent() {
        List<MailInfo> mailInfoList = new ArrayList<>();
        Intent intent = getIntent();
        mailInfoList = (List<MailInfo>) intent.getSerializableExtra("mailinfo");
        int position = intent.getIntExtra("position", 0);
        isSend = intent.getIntExtra("isSend", 0);
        MailInfo mailInfo = mailInfoList.get(position);
        mailSendUserName = mailInfo.getMailSendUserName();
        mailGetUserName = mailInfo.getMailGetUserName();
        mailTitle = mailInfo.getMailTitle();
        mailContext = mailInfo.getMailContext();
        mailImgUrl = mailInfo.getMailImgUrl();
        mailSendTime = mailInfo.getMailSendTime();
    }

}
