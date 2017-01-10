package com.xinyu.mwp.networkapi;

import android.content.Context;

public class NetworkAPIConfig {
    private Context context;
    private String userToken;
    private int timeout = 10000;
    private int upfileloadTimeout = 60000;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public void setUpfileloadTimeout(int upfileloadTimeout) {
        this.upfileloadTimeout = upfileloadTimeout;
    }

    public int getUpfileloadTimeout() {
        return upfileloadTimeout;
    }
}
