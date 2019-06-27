package com.mailapp.sourceop.sqlutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mailapp.properties.AppProperties;

public class NewsSqliteHelper extends SQLiteOpenHelper {
    public NewsSqliteHelper(Context context) {
        super(context, "NewsDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ AppProperties.NEWSTABLE +"(_id Integer primary key autoincrement," +
                "title varchar(100),link varchar(200),author varchar(200),image varchar(300)," +
                "pubDate varchar(100),type varchar(100),description text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
