package com.mailapp.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mailapp.R;
import com.mailapp.bean.NewsInfo;
import com.mailapp.enumeration.SourceType;
import com.mailapp.handler.MyHandler;
import com.mailapp.manager.NewsManager;
import com.mailapp.properties.AppProperties;

import java.util.List;

public class NewsFragment extends Fragment {

    private ListView lv_news;
    private MyHandler myHandler;
    private NewsManager newsManager;
    private SharedPreferences config;  //记录上次退出的操作
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = View.inflate(getActivity(), R.layout.fragment_news, null);
        config = getActivity().getSharedPreferences("config", 0);
        String type = config.getString("type", SourceType.SOURCE_FROM_SERVER.getType());
        String path = config.getString("path", AppProperties.SERVER_PATH);
        initView();
        initData();
        List<NewsInfo> newsListFromDB = newsManager
                .getNewsListFromSource(getActivity(), SourceType.valueOf("SOURCE_FROM_DB"), path);
        if (newsListFromDB.isEmpty()) {
            showNewsList(SourceType.valueOf(type),path);
        } else {
            showNewsList(SourceType.valueOf("SOURCE_FROM_DB"),path);
        }
        return rootView;
    }

    private void initView() {
        lv_news = rootView.findViewById(R.id.lv_news);
    }

    private void showNewsList(final SourceType sourceType,final String path) {
        new Thread() {
            @Override
            public void run() {
                List<NewsInfo> newsList = newsManager.getNewsListFromSource
                        (getActivity(), sourceType, path);
                Message msy = Message.obtain();
                msy.obj = newsList;
                msy.what = AppProperties.NEWSLISTSUCCESS;
                myHandler.sendMessage(msy);
            }
        }.start();
    }

    private void initData() {
        myHandler = new MyHandler(lv_news, getActivity());
        newsManager = new NewsManager();
    }

}