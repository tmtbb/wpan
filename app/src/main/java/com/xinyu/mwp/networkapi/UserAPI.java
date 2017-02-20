package com.xinyu.mwp.networkapi;

import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;

/**
 * Created by yaowang on 2017/2/20.
 * 用户相关API接口
 */

public interface UserAPI {
    void login(String phone, String password, OnAPIListener<LoginReturnEntity> listener);
}
