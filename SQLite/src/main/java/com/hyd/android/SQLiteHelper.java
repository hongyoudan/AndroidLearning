package com.hyd.android;

import static com.hyd.android.Constant.SQLiteConstant.DB_NAME;
import static com.hyd.android.Constant.SQLiteConstant.DB_VERSION;
import static com.hyd.android.Constant.SQLiteConstant.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {


    private static SQLiteHelper mInstance;
    private SQLiteDatabase database = null;

    /**
     * 利用单例模式获取数据库帮助器的实例
     *
     * @param context 上下文
     * @param dbName  数据库名称
     * @param factory 工厂类
     * @param version 数据库版本
     */
    private SQLiteHelper(@Nullable Context context, @Nullable String dbName, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
    }

    public static synchronized SQLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SQLiteHelper(context, DB_NAME, null, DB_VERSION);
        }
        return mInstance;
    }

    /**
     * 打开数据库的写连接
     * <p>
     * getWritableDatabase()与getReadableDatabase() 这两个方法都可以获取到数据库的连接
     * 正常情况下没有区别，当手机存储空间不够了
     * getReadableDatabase()就不能进行插入操作了，执行插入没有效果
     * getWritableDatabase()：也不能进行插入操作，如果执行插入数据的操作，则会抛异常。对于现在来说不会出现这种情况，用哪种方式都可以
     *
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getWritableDB() {

        if (database == null || !database.isOpen()) {
            database = mInstance.getWritableDatabase();
        }
        return database;
    }

    // 关闭数据库的读连接
    public void closeDB() {
        if (database != null && database.isOpen()) {
            database.close();
            database = null;
        }
    }

    /**
     * 数据库初始化
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username VARCHAR(10) NOT NULL,"
                + "password VARCHAR(10) NOT NULL"
                + ");";
        db.execSQL(createTable);
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
