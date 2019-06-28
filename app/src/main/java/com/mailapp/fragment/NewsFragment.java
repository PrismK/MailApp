package com.mailapp.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mailapp.R;
import com.mailapp.activity.LoginActivity;
import com.mailapp.bean.NewsInfo;
import com.mailapp.enumeration.SourceType;
import com.mailapp.handler.MyHandler;
import com.mailapp.manager.NewsManager;
import com.mailapp.properties.AppProperties;
import com.mailapp.reciever.NetStatusReceiver;

import java.util.List;

public class NewsFragment extends Fragment {

    private ListView lv_news;
    private MyHandler myHandler;
    private NewsManager newsManager;
    private SharedPreferences config;  //记录上次退出的操作
    private View rootView;
    private NetStatusReceiver netReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = View.inflate(getActivity(), R.layout.fragment_news, null);
        netReceiver = new NetStatusReceiver();
        setRegisterReceiver(netReceiver);
        setNetListener();
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
        TextView tv_logout = rootView.findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("警告！").setMessage("确定退出登陆吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences login_sp = getActivity().getSharedPreferences("userInfo", 0);
                                SharedPreferences.Editor edit = login_sp.edit();
                                edit.clear();
                                edit.commit();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
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

    public void setRegisterReceiver(NetStatusReceiver netReceiver){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(netReceiver, filter);
    }

    public void setNetListener(){
        netReceiver.setNetStateListener(new NetStatusReceiver.INetStatusListener() {
            @Override
            public void getNetState(int state) {
                if (state == NetStatusReceiver.NETSTATUS_INAVAILABLE) {
                    Toast.makeText(getActivity(), "网络未连接", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "网络已连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}