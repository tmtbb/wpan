package com.xinyu.mwp.entity;


import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Administrator on 2017/2/21.
 */
public class VerifyCodeReturnEntry extends BaseEntity {
    public static long timeStamp = 0;
    public static String vToken = "";
    public static int status = -1;  // 0 成功  1,失败
}
