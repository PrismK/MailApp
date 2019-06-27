
package com.mailapp.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mailapp.bean.UserData;


public class UserDBManager {

    public static final String DB_NAME = "user";
    public static final String TABLE_NAME = "users";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";

    private static final int DB_VERSION = 1;

    private Context mContext;

    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + USER_NAME + " varchar(15)," + USER_PWD + " varchar(15)" + ");";

    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

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

    public UserDBManager(Context context) {
        mContext = context;
    }

    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }

    public long insertUser(UserData userData) {
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        return mSQLiteDatabase.insert(TABLE_NAME, USER_NAME, values);
    }

    public boolean updateUserPW(UserData userData) {
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_PWD, userPwd);
        String where = USER_NAME + "=" + "=\"" + userName + "\"";
        return mSQLiteDatabase.update(TABLE_NAME, values, where, null) > 0;
    }

    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findUserByName(String userName) {
        int result = 0;
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME + "=?", new String[]{userName}, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
        }
        return result;
    }

    //根据用户名和密码找用户，用于登录
    public int findUserByNameAndPwd(String userName, String pwd) {
        int result = 0;
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME + "=?" + " and " + USER_PWD + "=?",
                new String[]{userName, pwd}, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
        }
        return result;
    }

}
