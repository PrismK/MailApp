package com.mailapp.sourceop.sqlutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.mailapp.bean.NewsInfo;
import com.mailapp.properties.AppProperties;

import java.util.ArrayList;
import java.util.List;

public class NewsSqlOP {

    private NewsSqliteHelper helper;

    public NewsSqlOP(Context context) {

        helper = new NewsSqliteHelper(context);

    }

    public boolean insert(List<NewsInfo> newsList) {

        SQLiteDatabase db = helper.getWritableDatabase();
        long start = -1;
        long end = -1;
        db.beginTransaction();
        try {
            for (NewsInfo news : newsList) {
                ContentValues values = new ContentValues();
                values.put("title", news.getTitle());
                values.put("link", news.getLink());
                values.put("author", news.getAuthor());
                values.put("image", news.getImage());
                values.put("pubDate", news.getPubDate());
                values.put("type", news.getType());
                values.put("description", news.getDescription());
                long insert = db.insert(AppProperties.NEWSTABLE, null, values);
                if(start == -1){
                    start = insert;
                }
                end = insert + 1;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();

            if (newsList.size() == (end - start)){
                return true;
            }else {
                return false;
            }
        }
    }
    public List<NewsInfo> queryNews(){
        List<NewsInfo> newsList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor query = db.query(AppProperties.NEWSTABLE, null, null, null, null, null, null);
        if(query!=null&&query.getCount()>0){
            while (query.moveToNext()){
                NewsInfo newsInfo = new NewsInfo();
                newsInfo.setTitle(query.getString(1));
                newsInfo.setLink(query.getString(2));
                newsInfo.setAuthor(query.getString(3));
                newsInfo.setImage(query.getString(4));
                newsInfo.setPubDate(query.getString(5));
                newsInfo.setType(query.getString(6));
                newsInfo.setDescription(query.getString(7));
                newsList.add(newsInfo);
            }
        }
        query.close();
        db.close();
        return newsList;
    }
}
