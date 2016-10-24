package com.wuyunlong.fulicenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wuyunlong.fulicenter.bean.User;

/**
 * Created by Administrator on 2016/10/24.
 * 执行数据库语句
 */
public class DBManager {
    private static DBManager dbMgr = new DBManager();
    private DBOpenHelper dbHelper;

    public DBManager() {
    }

    void onInit(Context context) {
        dbHelper = new DBOpenHelper(context);

    }

    public static synchronized DBManager getInstance() {
        return dbMgr;
    }

    /**
     * 保存信息,既将数据放入表中
     *
     * @param user
     * @return
     */
    public synchronized boolean saveUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
        values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
        values.put(UserDao.USER_COLUMN_AVATAR_ID, user.getMavatarId());
        values.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
        values.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
        values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
        values.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME, user.getMavatarLastUpdateTime());
        if (db.isOpen()) {
            return db.replace(UserDao.USER_TABLE_NAME, null, values) != -1;
        }
        return false;

    }

    /**
     * 获取用户,既查询数据库
     *
     * @param usernname
     * @return
     */
    public synchronized User getUser(String usernname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select*from" + UserDao.USER_TABLE_NAME + "where"
                + UserDao.USER_COLUMN_NAME + "=?";
        User user = null;
        Cursor cursor = db.rawQuery(sql, new String[]{usernname});
        if (cursor.moveToNext()) {
            user = new User();
            user.setMuserName(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
            user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));
            return user;
        }

        return user;
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    public boolean updataUser(User user) {
        int resule = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = UserDao.USER_COLUMN_NAME + "=?";
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
        if (db.isOpen()) {
            resule = db.update(UserDao.USER_TABLE_NAME, values, sql, new String[]{user.getMuserName()});
        }
        return resule > 0;
    }


}
