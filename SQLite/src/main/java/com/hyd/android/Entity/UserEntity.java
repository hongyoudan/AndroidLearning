package com.hyd.android.Entity;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name = "用户表示例")
public class UserEntity {

    @SmartColumn(id = 1, name = "ID")
    private Integer _id;

    @SmartColumn(id = 2, name = "用户名", minWidth = 155)
    private String username;

    @SmartColumn(id = 3, name = "密码", minWidth = 155)
    private String password;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
