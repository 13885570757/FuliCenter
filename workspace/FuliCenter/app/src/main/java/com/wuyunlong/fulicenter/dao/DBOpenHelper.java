package com.wuyunlong.fulicenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wuyunlong.fulicenter.I;

/**
 * Created by Administrator on 2016/10/24.
 */
public class DBOpenHelper extends SQLiteOpenHelper {



    private static final String CREATE_USER_TABLE = "";//数据库语句
    private static final int DATABASE_VERSION = 1;//版本

    /**
     *
     * @param context
     * @param name  数据库名次， .db文件
     * @param factory  工程模式
     * @param version  版本号
     */
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,getUserDatabaseName(),null,DATABASE_VERSION);
    }


    private static String getUserDatabaseName(){
        return I.User.TABLE_NAME+ "_demo.db";
    }

    /**
     * 打开数据库语句
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
