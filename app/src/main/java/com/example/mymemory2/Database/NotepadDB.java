package com.example.mymemory2.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotepadDB {
    public static final String DATABASE_NAME = "Notepad.db"; // 数据库名称
    public static final int DATABASE_VERSION = 1; // 数据库版本

    // 用户表建表语句
    private static final String CREATE_TABLE_USER = "create table if not exists " + UserTB.USER_TABLE + " (" +
            UserTB.USER_ID + " integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
            UserTB.USER_ACCOUNT + " VARCHAR NOT NULL," +
            UserTB.USER_NAME + " VARCHAR DEFAULT 'momo', " +
            UserTB.USER_PWD + " VARCHAR NOT NULL);";

    // 记事本表建表语句
    private static final String CREATE_TABLE_NOTE = "create table if not exists " + NoteTB.NOTEBOOK_TABLE + "(" +
            NoteTB.NOTEBOOK_ID + " integer primary key autoincrement, " +
            NoteTB.NOTEBOOK_CONTENT + " text, " +
            NoteTB.NOTEBOOK_TIME + " text);";

    // 记账本表建表语句
    private static final String CREATE_TABLE_TALLY = "create table if not exists " + TallyTB.TALLY_TABLE + "(" +
            TallyTB.TALLY_ID + " integer primary key autoincrement," +
            TallyTB.TALLY_DATE + " text," +
            TallyTB.TALLY_TYPE + " text," +
            TallyTB.TALLY_MONEY + " float," +
            TallyTB.TALLY_STATE + " text);";

    private Context context;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    public NotepadDB(Context ctx) {
        this.context = ctx;
        this.mDBHelper = new DatabaseHelper(this.context);
    }

    // 内部类，用于创建和升级数据库
    public static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // 创建数据库表
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_NOTE); // 创建记事本表
            db.execSQL(CREATE_TABLE_USER); // 创建用户表
            db.execSQL(CREATE_TABLE_TALLY); // 创建记账本表
        }

        // 数据库升级时的操作
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // 此处为空，不做任何操作
        }
    }

    // 打开数据库
    public void open() throws SQLException {
        this.mDatabase = this.mDBHelper.getWritableDatabase();
    }

    // 关闭数据库
    public void close() {
        this.mDBHelper.close();
    }
}
