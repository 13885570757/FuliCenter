package com.wuyunlong.fulicenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wuyunlong.fulicenter.I;

/**
 * Created by Administrator on 2016/10/24.
 * 打开数据库
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;//版本
    private static DBOpenHelper instance;

    private static final String CREATE_USER_TABLE = "";//数据库语句
    private static final String FuliCenter_USER_TABLE_CREATE="CREATA TABLE"
            +UserDao.USER_TABLE_NAME+"("
            +UserDao.USER_COLUMN_NAME+"TEXT PRIMARY KEY,"
            +UserDao.USER_COLUMN_NICK+"TEXT,"
            +UserDao.USER_COLUMN_AVATAR_ID+"INTEGER,"
            +UserDao.USER_COLUMN_AVATAR_TYPE+"INTEGER,"
            +UserDao.USER_COLUMN_AVATAR_PATH+"TEXT,"
            +UserDao.USER_COLUMN_AVATAR_SUFFIX+"TEXT,"
            +UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME+"TEXT;";




    public static DBOpenHelper onInit(Context context){
        if (instance==null){
            instance = new DBOpenHelper(context);
        }
        return instance;
    }

    public DBOpenHelper(Context context) {
        super(context,getUserDatabaseName(),null,DATABASE_VERSION);
    }


    private static String getUserDatabaseName(){
        return I.User.TABLE_NAME+ "_demo.db";
    }

    /**
     * 执行数据库语句
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 关闭方法
     */
    public void closeDB(){
        if (instance!=null){
            instance.close();
            instance=null;//初始化为空
        }
    }
}
