package com.xinyu.mwp.configs;

/**
 * Created by Tang on 2017/1/7.
 */

public class UserServer extends Server {

    /**
     * 获取验证码
     *
     * @return
     */
    public static String getUrlCode() {
        return URL_SERVER_BASE + "/xxx";
    }

    /**
     * 注册
     *
     * @return
     */
    public static String getUrlRegister() {
        return URL_SERVER_BASE + "/xxx";
    }

    /**
     * 本地登录
     *
     * @return
     */
    public static String getUrlLogin() {
        return URL_SERVER_BASE + "/xxx";
    }
}
