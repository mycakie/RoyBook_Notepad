package com.example.mymemory2.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mymemory2.Bean.Tally;
import com.example.mymemory2.Database.NotepadDB.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TallyTB {

    public static final String TALLY_ID = "id";                // 主键id
    public static final String TALLY_DATE = "date";             // 账目日期
    public static final String TALLY_TYPE = "type";             // 账目项类型（收入/支出）
    public static final String TALLY_MONEY = "money";           // 金额
    public static final String TALLY_STATE = "state";           // 说明
    static final String TALLY_TABLE = "tally";                  // 表名

    private Context context;
    private DatabaseHelper mHelper;
    private SQLiteDatabase mWdb;
    private SQLiteDatabase mRdb;

    public TallyTB(Context ctx) {
        this.context = ctx;
    }

    public TallyTB open() throws SQLException {
        mHelper = new NotepadDB.DatabaseHelper(context);
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
     * 添加账目项
     *
     * @param tally 账目项对象
     * @return 是否添加成功
     */
    public boolean insert(Tally tally) {
        ContentValues values = new ContentValues();
        values.put(TALLY_TYPE, tally.getTallyType());
        values.put(TALLY_DATE, tally.getTallyTime());
        values.put(TALLY_MONEY, tally.getTallyMoney());
        values.put(TALLY_STATE, tally.getTallyState());
        return mWdb.insert(TALLY_TABLE, null, values) > 0;
    }

    /**
     * 删除账目项
     *
     * @param id 账目项id
     * @return 是否删除成功
     */
    public boolean delete(int id) {
        String sql = TALLY_ID + "=?";
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return mWdb.delete(TALLY_TABLE, sql, contentValuesArray) > 0;
    }

    /**
     * 根据日期和收支类型查询账目项
     *
     * @param date 日期
     * @param type 收支类型
     * @return 符合条件的账目项列表
     */
    @SuppressLint("Range")
    public List<Tally> selectByDateAndType(String date, String type) {
        Cursor cursor = mRdb.query(TALLY_TABLE, null, "date=? and type=?", new String[]{date, type}, null, null, "date asc");
        List<Tally> tallyList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String tallyDate = cursor.getString(cursor.getColumnIndex("date"));
                String tallyType = cursor.getString(cursor.getColumnIndex("type"));
                double tallyMoney = cursor.getDouble(cursor.getColumnIndex("money"));
                String tallyState = cursor.getString(cursor.getColumnIndex("state"));
                Tally tally = new Tally();
                tally.setTallyTime(tallyDate);
                tally.setTallyType(tallyType);
                tally.setTallyMoney(tallyMoney);
                tally.setTallyState(tallyState);
                tallyList.add(tally);
            }
            return tallyList;
        }
        cursor.close();
        return null;
    }

    /**
     * 根据月份和收支类型查询账目项
     *
     * @param month 月份
     * @param type  收支类型
     * @return 符合条件的账目项列表
     */
    @SuppressLint("Range")
    public List<Tally> selectByMonthAndType(String month, String type) {
        Cursor cursor = mRdb.query(TALLY_TABLE, null, TALLY_DATE + " like ? and type=?", new String[]{month + "%", type},
                null, null, "date asc");
        List<Tally> tallyList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String tallyDate = cursor.getString(cursor.getColumnIndex("date"));
                String tallyType = cursor.getString(cursor.getColumnIndex("type"));
                double tallyMoney = cursor.getDouble(cursor.getColumnIndex("money"));
                String tallyState = cursor.getString(cursor.getColumnIndex("state"));
                Tally tally = new Tally();
                tally.setTallyTime(tallyDate);
                tally.setTallyType(tallyType);
                tally.setTallyMoney(tallyMoney);
                tally.setTallyState(tallyState);
                tallyList.add(tally);
            }
            return tallyList;
        }
        cursor.close();
        return null;
    }

    /**
     * 查询所有账目项
     *
     * @return 所有账目项列表
     */
    @SuppressLint("Range")
    public List<Tally> query() {
        List<Tally> list = new ArrayList<>();
        Cursor cursor = mRdb.query(TALLY_TABLE, null, null, null,
                null, null, TALLY_DATE + " desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Tally tallyInfo = new Tally();
                int id = cursor.getInt(cursor.getColumnIndex(TALLY_ID));
                String time = cursor.getString(cursor.getColumnIndex(TALLY_DATE));
                String type = cursor.getString(cursor.getColumnIndex(TALLY_TYPE));
                String state = cursor.getString(cursor.getColumnIndex(TALLY_STATE));
                double money = cursor.getDouble(cursor.getColumnIndex(TALLY_MONEY));
                tallyInfo.setId(id);
                tallyInfo.setTallyState(state);
                tallyInfo.setTallyType(type);
                tallyInfo.setTallyTime(time);
                tallyInfo.setTallyMoney(money);
                list.add(tallyInfo);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 更新账目项信息
     *
     * @param id    账目项id
     * @param date  日期
     * @param type  收支类型
     * @param money 金额
     * @param state 说明
     * @return 是否更新成功
     */
    public boolean update(int id, String date, String type, double money, String state) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TALLY_DATE, date);
        contentValues.put(TALLY_TYPE, type);
        contentValues.put(TALLY_MONEY, money);
        contentValues.put(TALLY_STATE, state);
        String sql = TALLY_ID + "=?";
        String[] strings = new String[]{String.valueOf(id)};
        return mWdb.update(TALLY_TABLE, contentValues, sql, strings) > 0;
    }
}
