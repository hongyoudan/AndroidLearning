package com.hyd.android.Service.impl;

import static com.hyd.android.Constant.SQLiteConstant.DB_VERSION;
import static com.hyd.android.Constant.SQLiteConstant.PASSWORD_KEY;
import static com.hyd.android.Constant.SQLiteConstant.TABLE_NAME;
import static com.hyd.android.Constant.SQLiteConstant.USERNAME_KEY;
import static com.hyd.android.Constant.SQLiteConstant.TAG;
import static com.hyd.android.Constant.SQLiteConstant._ID_KEY;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.hyd.android.Entity.UserEntity;
import com.hyd.android.SQLiteHelper;
import com.hyd.android.Service.IUserService;
import com.hyd.android.utils.AjaxResult;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService {

    @Override
    public AjaxResult insert(Context context, UserEntity user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            Log.i(TAG, "insert: 用户名或密码不能为空！");
            return AjaxResult.error("用户名或密码不能为空！");
        }

        SQLiteDatabase database = SQLiteHelper.getInstance(context).getWritableDatabase();

        // 如果用户名重复，则不允许插入
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 迭代 cursor 对象
                @SuppressLint("Range") String cursorUsername = cursor.getString(cursor.getColumnIndex(USERNAME_KEY));
                if (cursorUsername.equals(username)) {
                    Log.i(TAG, "insert: 该用户名已存在");
                    return AjaxResult.error("该用户名已存在，请重新输入！");
                }

            } while (cursor.moveToNext());
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(USERNAME_KEY, username);
        values.put(PASSWORD_KEY, password);
        long insert = database.insert(TABLE_NAME, null, values);

        Log.i(TAG, "insert: 插入成功！");
        return AjaxResult.success("插入成功！");

    }

    @Override
    public List<UserEntity> selectAll(Context context) {

        // 实例化 SQLiteHelper
        SQLiteDatabase database = SQLiteHelper.getInstance(context).getWritableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        List<UserEntity> userList = new ArrayList<>();
        while (cursor.moveToNext()) {
            UserEntity user = new UserEntity();
            @SuppressLint("Range") String _id = cursor.getString(cursor.getColumnIndex(_ID_KEY));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(USERNAME_KEY));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(PASSWORD_KEY));
            user.set_id(Integer.valueOf(_id));
            user.setUsername(username);
            user.setPassword(password);
            userList.add(user);
        }
        cursor.close();

        return userList;
    }

    @Override
    public AjaxResult update(Context context, UserEntity user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            Log.i(TAG, "insert: 用户名或密码不能为空！");
            return AjaxResult.error("用户名或密码不能为空！");
        }

        // 实例化 SQLiteHelper
        SQLiteDatabase database = SQLiteHelper.getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PASSWORD_KEY, password);

        String whereClause = "username=" + user.getUsername();

        int updateRes = database.update(TABLE_NAME, values, whereClause, null);
        if (updateRes == 1) {
            return AjaxResult.success("修改成功！");
        }

        return AjaxResult.error("修改失败！");
    }

    @Override
    public AjaxResult deleteByUsername(Context context, String username) {
        if (StringUtils.isEmpty(username)) {
            Log.i(TAG, "deleteByUsername: 用户名不能为空！");
            return AjaxResult.error("用户名不能为空！");
        }

        SQLiteDatabase database = SQLiteHelper.getInstance(context).getWritableDatabase();
        String whereClause = "username=" + username;
        int deleteRes = database.delete(TABLE_NAME, whereClause, null);
        if (deleteRes == 1) {
            return AjaxResult.success("删除成功！");
        }

        return AjaxResult.error("删除失败！");

    }
}
