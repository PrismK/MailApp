package com.mailapp.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.mailapp.activity.AllNewsInfoActivity;
import com.mailapp.adapters.NewsAdapter;
import com.mailapp.bean.NewsInfo;
import com.mailapp.properties.AppProperties;

import java.util.List;


public class MyHandler extends Handler {
    View view;
    Context context;
    public MyHandler(View view, Context context) {
        this.view = view;
        this.context =  context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProperties.NEWSLISTSUCCESS:
                final List<NewsInfo> newsInfoList = (List<NewsInfo>) msg.obj;
                NewsAdapter newsAdapter = new NewsAdapter(context, newsInfoList);
                ListView lv_news = (ListView) this.view;
                lv_news.setAdapter(newsAdapter);
                lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent((Activity)context, AllNewsInfoActivity.class);
                                intent.putExtra("newsInfo", (Parcelable) newsInfoList.get(position));
                                context.startActivity(intent);
                    }
                });
                break;
        }
    }
}
