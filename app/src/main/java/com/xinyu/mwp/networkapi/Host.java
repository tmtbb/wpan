package com.xinyu.mwp.networkapi;

import android.text.TextUtils;

/**
 * Created by wsz on 2016/4/27.
 */
public enum Host {
    INTERNAL_TEST("http://code.ywwl.com/modou"),
    EXTERNAL_TEST("http://tandroidapi.modou.com"),
    EXTERNAL_RELEASE("http://androidapi.modou.com");

    private String host;
    private String oldHost;

    Host(String value) {
        this.host = value;
    }

    public String getValue() {
        return host;
    }

    public void switchHost() {
        if (!EXTERNAL_RELEASE.getValue().equals(host)) {
            oldHost = host;
            host = EXTERNAL_RELEASE.getValue();
        } else if (!TextUtils.isEmpty(oldHost)) {
            host = oldHost;
        }
    }

    public static int getImPort(Host host) {

        if (host == EXTERNAL_RELEASE) {
            return 5432;
        } else {
            return 5222;
        }
    }

    public static String getImHost(Host host) {

        if (host == EXTERNAL_RELEASE) {
            return "im.modou.com";
        } else if (host == INTERNAL_TEST) {
            return "code.ywwl.com";
        } else {
            return "tim.modou.com";
        }
    }

//    public static String serverIp = "139.224.34.22";
//    public static short serverPort = 16205;

    public static String getSocketServerIp() {
        return "122.144.169.217";
//        return "139.224.34.22";
//        return serverIp;
    }

    public static short getSocketServerPort() {
        return (short) 16205;
//        return serverPort;
    }

//    public static void setSocketServerIp(String serverIp) {
//        Host.serverIp = serverIp;
//    }
//
//    public static void setSocketServerPort(short serverPort) {
//        Host.serverPort = serverPort;
//    }
}
