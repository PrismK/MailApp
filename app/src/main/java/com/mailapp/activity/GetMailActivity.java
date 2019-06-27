package com.mailapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mailapp.R;

public class GetMailActivity extends AppCompatActivity {

    private ListView lsv_getmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmail);
        initView();
        initListView();
    }

    private void initListView() {

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

}
