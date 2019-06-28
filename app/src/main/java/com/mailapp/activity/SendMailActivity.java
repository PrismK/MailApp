package com.mailapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.mailapp.R;
import com.mailapp.adapters.MailAdapter;
import com.mailapp.bean.MailInfo;
import com.mailapp.manager.MailManager;

import java.io.Serializable;
import java.util.List;

public class SendMailActivity extends AppCompatActivity {

    private ListView lsv_getmail;
    private MailManager mailManager;
    private String userName;
    private List<MailInfo> comeMailInfoFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmail);
        SharedPreferences login_sp = getSharedPreferences("userInfo", 0);
        userName = login_sp.getString("USER_NAME", "");
        initView();
        initMailManager();
        initData();
        initListView();
    }

    private void initData() {
        if (mailManager != null) {
            comeMailInfoFromDB = mailManager.getSendMailInfoFromDB(userName);
        }
    }

    private void initMailManager() {
        if (mailManager == null) {
            mailManager = new MailManager(this);
            mailManager.openDataBase();
        }
    }

    private void initListView() {
        MailAdapter mailAdapter = new MailAdapter(this, comeMailInfoFromDB);
        lsv_getmail.setAdapter(mailAdapter);
        lsv_getmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SendMailActivity.this, MailDetailActivity.class);
                intent.putExtra("mailinfo", (Serializable) comeMailInfoFromDB);//序列化，传递带泛型的List
                intent.putExtra("position", position);
                intent.putExtra("isSend", 1);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        ImageView imv_back = findViewById(R.id.imv_back);
        lsv_getmail = findViewById(R.id.lsv_getmail);
        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
