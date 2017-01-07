package com.xinyu.mwp.configs;

/**
 * @author RainDrop
 * @date 2015/12/30
 * @time 13:06.
 */
public class Constant {

    public static final String  TAG      = "Wpan";
    public static final boolean DEBUG    = true;


    public static final String WEIXIN_APP_ID = "wx080b018aba0c2e76";
    public static String WEIXIN_APP_SECRET;

    /**
     * 正则表达式:验证手机号,简单判断
     */
    public static final String REGEX_MOBILE = "[1][3578]\\d{9}";

    /**
     * 正则表达式:验证密码(不包含特殊字符)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
}
