package com.xinyu.mwp.entity;

import android.widget.TextView;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Administrator on 2017/2/21.
 */
public class VerifyCodeReturnEntry extends BaseEntity {
    @FieldJsonKey("timestamp")
    private String timestamp;  //时间戳
    @FieldJsonKey("vToken")
    private String vToken;  //验证码签名

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getvToken() {
        return vToken;
    }

    public void setvToken(String vToken) {
        this.vToken = vToken;
    }
}
