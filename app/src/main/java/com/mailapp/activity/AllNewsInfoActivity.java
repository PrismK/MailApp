package com.mailapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mailapp.R;
import com.mailapp.bean.NewsInfo;
import com.mailapp.view.MyImageView;


public class AllNewsInfoActivity extends Activity {

    private TextView tv_news_title;
    private TextView tv_news_description;
    private MyImageView iv_news_image;
    private TextView tv_news_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allnewsinfo);
        initView();
        showNewsView();
    }

    private void initView() {
        iv_news_image = (MyImageView) findViewById(R.id.iv_news_image);
        tv_news_title = (TextView) findViewById(R.id.tv_news_title);
        tv_news_description = (TextView) findViewById(R.id.tv_news_description);
        tv_news_type = (TextView) findViewById(R.id.tv_news_type);
    }

    private void showNewsView() {
        Intent intent = getIntent();
        NewsInfo newsInfo = (NewsInfo) intent.getParcelableExtra("newsInfo");

        tv_news_title.setText(newsInfo.getTitle());
        tv_news_description.setText(newsInfo.getDescription());
        iv_news_image.setImageUrl(newsInfo.getImage());
        tv_news_type.setText(newsInfo.getType());
    }

}
