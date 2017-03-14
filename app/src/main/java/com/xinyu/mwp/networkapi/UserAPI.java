package com.xinyu.mwp.networkapi;

import com.xinyu.mwp.entity.BalanceInfoEntity;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.RegisterReturnEntity;
import com.xinyu.mwp.entity.UserinfoEntity;
import com.xinyu.mwp.entity.VerifyCodeReturnEntry;
import com.xinyu.mwp.listener.OnAPIListener;

import org.json.JSONObject;

/**
 * Created by yaowang on 2017/2/20.
 * 用户相关API接口
 */

public interface UserAPI {
    void login(String phone, String password,String deviceId, OnAPIListener<LoginReturnEntity> listener);
    void register(String phone, String password,String vCode, OnAPIListener<RegisterReturnEntity> listener);
    void verifyCode(String phone,int verifyType, OnAPIListener<VerifyCodeReturnEntry> listener);
    void resetDealPwd(String phone,String pwd, String vCode,int type,OnAPIListener<Object> listener); //修改交易/用户密码
    void test(int testID,OnAPIListener<Object> listener);
    void loginWithToken(OnAPIListener<LoginReturnEntity> listener);
    void balance(OnAPIListener<BalanceInfoEntity> listener);
}
