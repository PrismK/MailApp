package com.mailapp.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mailapp.bean.MailInfo;

import java.util.ArrayList;
import java.util.List;

public class MailManager {

    public static final String DB_NAME = "mailapp";
    public static final String TABLE_NAME = "mailinfo";
    public static final String MAIL_ID = "mailid";
    public static final String SEND_USER_NAME = "send_user_name";
    public static final String GET_USER_NAME = "get_user_name";
    public static final String MAIL_TITLE = "mail_title";
    public static final String MAIL_CONTEXT = "mail_context";
    public static final String MAIL_IMGURL = "mail_imgurl";
    public static final String MAIL_SENDTIME = "mail_sendtime";
    public static final String ISDRAFTS = "isdrafts";

    private static final int DB_VERSION = 1;

    private Context mContext;

    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + MAIL_ID + " integer primary key autoincrement,"
            + SEND_USER_NAME + " varchar(50)," + GET_USER_NAME + " varchar(50),"
            + MAIL_TITLE + " varchar(100)," + MAIL_CONTEXT + " varchar(500),"
            + MAIL_IMGURL + " varchar(100)," + MAIL_SENDTIME + " varchar(500),"
            + ISDRAFTS + " integer"
            + ");";

    private SQLiteDatabase mSQLiteDatabase = null;
    private MailManager.DataBaseManagementHelper mDatabaseHelper = null;

    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }
    }

    public MailManager(Context context) {
        mContext = context;
    }

    public void openDataBase() throws SQLException {
        mDatabaseHelper = new MailManager.DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }

    public long insertMailInfo(MailInfo mailInfo) {
        String mailSendUserName = mailInfo.getMailSendUserName();
        String mailGetUserName = mailInfo.getMailGetUserName();
        String mailTitle = mailInfo.getMailTitle();
        String mailContext = mailInfo.getMailContext();
        String mailImgUrl = mailInfo.getMailImgUrl();
        String mailSendTime = mailInfo.getMailSendTime();
        int isDrafts = mailInfo.getIsDrafts();
        ContentValues values = new ContentValues();
        values.put(SEND_USER_NAME, mailSendUserName);
        values.put(GET_USER_NAME, mailGetUserName);
        values.put(MAIL_TITLE, mailTitle);
        values.put(MAIL_CONTEXT, mailContext);
        values.put(MAIL_IMGURL, mailImgUrl);
        values.put(MAIL_SENDTIME, mailSendTime);
        values.put(ISDRAFTS, isDrafts);
        return mSQLiteDatabase.insert(TABLE_NAME, SEND_USER_NAME, values);
    }

    public boolean updateMailInfo(MailInfo mailInfo) {
        int mailId = mailInfo.getMailId();
        String mailGetUserName = mailInfo.getMailGetUserName();
        String mailTitle = mailInfo.getMailTitle();
        String mailContext = mailInfo.getMailContext();
        String mailImgUrl = mailInfo.getMailImgUrl();
        String mailSendTime = mailInfo.getMailSendTime();
        int isDrafts = mailInfo.getIsDrafts();
        ContentValues values = new ContentValues();
        values.put(GET_USER_NAME, mailGetUserName);
        values.put(MAIL_TITLE, mailTitle);
        values.put(MAIL_CONTEXT, mailContext);
        values.put(MAIL_IMGURL, mailImgUrl);
        values.put(MAIL_SENDTIME, mailSendTime);
        values.put(ISDRAFTS, isDrafts);
        String where = MAIL_ID + "=" + "=\"" + mailId + "\"";
        return mSQLiteDatabase.update(TABLE_NAME, values, where, null) > 0;
    }

    public List<MailInfo> getComeMailInfoFromDB(String getMailUserName) {
        List<MailInfo> list = new ArrayList<>();
        if (mSQLiteDatabase != null) {
            list = queryComeMailInfo(getMailUserName);
        } else {
            openDataBase();
            list = queryComeMailInfo(getMailUserName);
        }
        return list;
    }

    private List<MailInfo> queryComeMailInfo(String getMailUserName) {
        List<MailInfo> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from "
                + TABLE_NAME + " where " + GET_USER_NAME + " = ?" +
                        " and " + ISDRAFTS + " = ? order by " + MAIL_ID + " desc",
                new String[]{String.valueOf(getMailUserName), "0"});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String sendMailUserName = cursor.getString(1);
                String mailTitle = cursor.getString(3);
                String mailContext = cursor.getString(4);
                String mailImgUrl = cursor.getString(5);
                String mailSendTime = cursor.getString(6);
                MailInfo mailInfo = new MailInfo();
                mailInfo.setMailSendUserName(sendMailUserName);
                mailInfo.setMailGetUserName(getMailUserName);
                mailInfo.setMailTitle(mailTitle);
                mailInfo.setMailContext(mailContext);
                mailInfo.setMailImgUrl(mailImgUrl);
                mailInfo.setMailSendTime(mailSendTime);
                list.add(mailInfo);
            }
        }
        return list;
    }

    public List<MailInfo> getSendMailInfoFromDB(String sendMailUserName) {
        List<MailInfo> list = new ArrayList<>();
        if (mSQLiteDatabase != null) {
            list = queryComeMailInfo(sendMailUserName);
        } else {
            openDataBase();
            list = queryComeMailInfo(sendMailUserName);
        }
        return list;
    }

    private List<MailInfo> querySendMailInfo(String sendMailUserName) {
        List<MailInfo> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from "
                        + TABLE_NAME + " where " + GET_USER_NAME + " = ?" +
                        " and " + ISDRAFTS + " = ? order by " + MAIL_ID + " desc",
                new String[]{String.valueOf(sendMailUserName), "0"});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String getMailUserName = cursor.getString(2);
                String mailTitle = cursor.getString(3);
                String mailContext = cursor.getString(4);
                String mailImgUrl = cursor.getString(5);
                String mailSendTime = cursor.getString(6);
                MailInfo mailInfo = new MailInfo();
                mailInfo.setMailSendUserName(sendMailUserName);
                mailInfo.setMailGetUserName(getMailUserName);
                mailInfo.setMailTitle(mailTitle);
                mailInfo.setMailContext(mailContext);
                mailInfo.setMailImgUrl(mailImgUrl);
                mailInfo.setMailSendTime(mailSendTime);
                list.add(mailInfo);
            }
        }
        return list;
    }

}
