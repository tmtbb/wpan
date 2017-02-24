package com.xinyu.mwp.networkapi;

import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.VerifyCodeReturnEntry;
import com.xinyu.mwp.listener.OnAPIListener;

/**
 * Created by yaowang on 2017/2/20.
 * 用户相关API接口
 */

public interface UserAPI {
    void login(String phone, String password,String deviceId, OnAPIListener<LoginReturnEntity> listener);
    void register(String phone, String password,String vCode, OnAPIListener<LoginReturnEntity> listener);
    void verifyCode(String phone,int verifyType, OnAPIListener<VerifyCodeReturnEntry> listener);
    void resetDealPwd(String phone,String pwd, String vCode,OnAPIListener<Object> listener);
    void test(int testID,OnAPIListener<Object> listener);
}
