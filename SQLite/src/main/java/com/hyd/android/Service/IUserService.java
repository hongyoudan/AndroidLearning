package com.hyd.android.Service;

import android.content.Context;

import com.hyd.android.Entity.UserEntity;
import com.hyd.android.utils.AjaxResult;

import java.util.List;

public interface IUserService {

    AjaxResult insert(Context context, UserEntity user);

    AjaxResult update(Context context, UserEntity user);

    AjaxResult deleteByUsername(Context context, String username);

    List<UserEntity> selectAll(Context context);
}
