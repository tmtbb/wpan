package com.xinyu.mwp.networkapi;

import android.text.TextUtils;

/**
 * Created by wsz on 2016/4/27.
 */
public enum Host {
    INTERNAL_253("http://172.16.1.253"),
    INTERNAL_19("http://172.16.3.19"),
    INTERNAL_14("http://172.16.3.14/modou"),
    INTERNAL_165("http://172.16.1.165"),
    INTERNAL_TEST("http://code.ywwl.com/modou"),
    EXTERNAL_TEST("http://tandroidapi.modou.com"),
    EXTERNAL_RELEASE("http://androidapi.modou.com");

    private String host;
    private String oldHost;
    private int imPort = 5222;

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

    public static String getSocketServerIp() {
        return "61.147.114.87";
    }

    public static short getSocketServerPort() {
        return (short)160001;
    }

}
