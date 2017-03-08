package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by yaowang on 2017/2/20.
 */

public class LoginReturnEntity extends BaseEntity {
    @FieldJsonKey("userinfo")
    private UserinfoEntity userinfo;
    @FieldJsonKey("token")
    private String token;

    public UserinfoEntity getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoEntity userinfo) {
        this.userinfo = userinfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
