package com.hyd.android;

import static com.hyd.android.Constant.SQLiteConstant.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.table.TableData;
import com.blankj.utilcode.util.ToastUtils;
import com.hyd.android.Entity.UserEntity;
import com.hyd.android.Service.impl.UserServiceImpl;
import com.hyd.android.utils.AjaxResult;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartTable<UserEntity> stTable;
    private EditText etInsertUsername, etInsertPassword, etUpdateUsername, etUpdatePassword, etDeleteUsername;
    private Button btnInsert, btnUpdate, btnDelete;
    private SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图容器
        initView();
        // 渲染表格数据
        getUserList();
    }

    private void getUserList() {
        UserServiceImpl userService = new UserServiceImpl();
        List<UserEntity> userList = userService.selectAll(this);
        stTable.setData(userList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sqLiteHelper = SQLiteHelper.getInstance(this);
        sqLiteHelper.getWritableDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sqLiteHelper.closeDB();
    }

    private void initView() {
        stTable = findViewById(R.id.st_table);
        etInsertUsername = findViewById(R.id.et_insert_username);
        etInsertPassword = findViewById(R.id.et_insert_password);
        etUpdateUsername = findViewById(R.id.et_update_username);
        etUpdatePassword = findViewById(R.id.et_update_password);
        etDeleteUsername = findViewById(R.id.et_delete_username);
        btnInsert = findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_selectAll:
                selectAll(view);
                break;
            case R.id.btn_insert:
                insert(view);
                getUserList();
                break;
            case R.id.btn_update:
                update(view);
                getUserList();
                break;
            case R.id.btn_delete:
                deleteByUsername(view);
                getUserList();
                break;
        }
    }

    public void insert(View view) {
        UserEntity user = new UserEntity();
        user.setUsername(etInsertUsername.getText().toString().trim());
        user.setPassword(etInsertPassword.getText().toString().trim());

        UserServiceImpl userService = new UserServiceImpl();
        AjaxResult result = userService.insert(this, user);
        ToastUtils.showShort(Objects.requireNonNull(result.get("msg")).toString());

    }

    public void update(View view) {
        UserEntity user = new UserEntity();
        user.setUsername(etUpdateUsername.getText().toString().trim());
        user.setPassword(etUpdatePassword.getText().toString().trim());

        UserServiceImpl userService = new UserServiceImpl();
        AjaxResult result = userService.update(this, user);
        ToastUtils.showShort(Objects.requireNonNull(result.get("msg")).toString());
    }

    public void deleteByUsername(View view) {
        String username = etDeleteUsername.getText().toString().trim();
        UserServiceImpl userService = new UserServiceImpl();
        AjaxResult result = userService.deleteByUsername(this, username);
        ToastUtils.showShort(Objects.requireNonNull(result.get("msg")).toString());
    }


    public void selectAll(View view) {
        UserServiceImpl userService = new UserServiceImpl();
        List<UserEntity> userList = userService.selectAll(this);
        stTable.setData(userList);
    }
}