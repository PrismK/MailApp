package com.mailapp.manager;

import android.content.Context;

import com.mailapp.bean.NewsInfo;
import com.mailapp.enumeration.SourceType;
import com.mailapp.sourceop.sqlutils.NewsSqlOP;
import com.mailapp.sourceprovider.ISource;
import com.mailapp.sourceprovider.SourceFactory;
import com.mailapp.utils.NewsXmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsManager {
    public List<NewsInfo> getNewsListFromSource(final Context context, SourceType type, String path) {
        List<NewsInfo> newsList = new ArrayList<NewsInfo>();
        switch (type) {
            case SOURCE_FROM_SERVER:
                newsList = getNewsListFromInputStream(context, type, path);
                break;
            case SOURCE_FROM_DB:
                newsList = new NewsSqlOP(context).queryNews();
                break;
        }
        return newsList;
    }

    private List<NewsInfo> getNewsListFromInputStream(final Context context, SourceType type, String path) {
        List<NewsInfo> newsList = new ArrayList<NewsInfo>();
        InputStream in = null;
        ISource iSource = SourceFactory.sourceCreate(context, type);
        try {
            in = iSource.getInputStream(path);
            if (in != null) {
                newsList = NewsXmlParser.parse(in);
                final List<NewsInfo> finalNewsList = newsList;

                new Thread() {
                    @Override
                    public void run() {
                        NewsSqlOP newsSqlOP = new NewsSqlOP(context);
                        while (!newsSqlOP.insert(finalNewsList)) {
                            System.out.println("------------------失败----------------");
                        }
                        System.out.println("---------------------成功----------------------");
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newsList;
    }

}
