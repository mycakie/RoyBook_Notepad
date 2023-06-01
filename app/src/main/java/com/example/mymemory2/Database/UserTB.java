package com.example.mymemory2.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mymemory2.Bean.User;
import com.example.mymemory2.Database.NotepadDB.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserTB {
    public static final String USER_ID = "_id";             // 主键
    public static final String USER_ACCOUNT = "account";    // 账号
    public static final String USER_PWD = "password";       // 密码
    public static final String USER_NAME = "username";      // 用户名
    public static final String USER_TABLE = "user_info";    // 表名

    private Context context;
    private DatabaseHelper mHelper;
    private SQLiteDatabase mWdb;
    private SQLiteDatabase mRdb;

    public UserTB(Context ctx) {
        this.context = ctx;
    }

    public UserTB open() throws SQLException {
        mHelper = new DatabaseHelper(context);
        mWdb = mHelper.getWritableDatabase();
        mRdb = mHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        if (mHelper != null) {
            mHelper.close();
        }
    }

    /**
     * 删除用户
     *
     * @param user 要删除的用户对象
     * @return 受影响的行数
     */
    public long delete(User user) {
        return mWdb.delete(USER_TABLE, "account=?", new String[]{user.account});
    }

    /**
     * 插入用户，即注册用户
     *
     * @param user 要插入的用户对象
     * @return 插入的行id
     */
    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("account", user.getAccount());
        values.put("password", user.getPassword());
        return mWdb.insert(USER_TABLE, null, values);
    }

    /**
     * 通过账号和密码查询用户，即用户登录
     *
     * @param account  账号
     * @param password 密码
     * @return 符合条件的用户列表
     */
    @SuppressLint("Range")
    public List<User> selectByAccountAndPass(String account, String password) {
        Cursor cursor = mRdb.query(USER_TABLE, null, "account=? and password=?", new String[]{account, password}, null, null, null);
        List<User> userList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String account1 = cursor.getString(cursor.getColumnIndex("account"));
                String password1 = cursor.getString(cursor.getColumnIndex("password"));
                User user = new User();
                user.setAccount(account1);
                user.setPassword(password1);
                userList.add(user);
            }
            return userList;
        }
        cursor.close();
        return null;
    }

    /**
     * 通过账号查询用户，即判断账号是否存在
     *
     * @param account 账号
     * @return 符合条件的用户列表
     */
    @SuppressLint("Range")
    public List<User> selectByAccount(String account) {
        Cursor cursor = mRdb.query(USER_TABLE, null, "account=?", new String[]{account}, null, null, null);
        List<User> userList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String account1 = cursor.getString(cursor.getColumnIndex("account"));
                User user = new User();
                user.setAccount(account1);
                userList.add(user);
            }
            return userList;
        }
        return null;
    }
}
