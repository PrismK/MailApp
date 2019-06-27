package com.mailapp.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mailapp.R;
import com.mailapp.fragment.MailFragment;
import com.mailapp.fragment.NewsFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imv_mail;
    private ImageView imv_news;
    private TextView tv_mail;
    private TextView tv_news;
    private NewsFragment newsFragment;
    private MailFragment mailFragment;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        switchFragment(0);
    }

    private void initFragment() {
        fManager = getFragmentManager();
    }

    private void switchFragment(int index) {
        FragmentTransaction transaction = fManager.beginTransaction();
        clearChoice();
        hideFragments(transaction);
        switch (index) {
            case 0:
                imv_mail.setImageResource(R.mipmap.mail_select);
                tv_mail.setTextColor(getResources().getColor(R.color.blue));
                imv_news.setImageResource(R.mipmap.news);
                tv_news.setTextColor(getResources().getColor(R.color.gray));
                if (mailFragment == null) {
                    mailFragment = new MailFragment();
                    transaction.add(R.id.fl_fragment, mailFragment);
                } else {
                    transaction.show(mailFragment);
                }
                break;
            case 1:
                imv_news.setImageResource(R.mipmap.news_select);
                tv_news.setTextColor(getResources().getColor(R.color.blue));
                imv_mail.setImageResource(R.mipmap.mail);
                tv_mail.setTextColor(getResources().getColor(R.color.gray));
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.fl_fragment, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mailFragment != null) {
            transaction.hide(mailFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
    }

    public void clearChoice() {

    }

    private void initView() {
        imv_mail = findViewById(R.id.imv_mail);
        imv_news = findViewById(R.id.imv_news);
        tv_mail = findViewById(R.id.tv_mail);
        tv_news = findViewById(R.id.tv_news);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mail:
                switchFragment(0);
                break;
            case R.id.ll_news:
                switchFragment(1);
                break;
        }
    }
}
