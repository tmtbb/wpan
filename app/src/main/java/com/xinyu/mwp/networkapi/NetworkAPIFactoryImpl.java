package com.xinyu.mwp.networkapi;


import com.xinyu.mwp.networkapi.http.NetworkHttpAPIFactoryImpl;

public class NetworkAPIFactoryImpl {
    private static NetworkAPIFactory networkAPIFactory = null;


    static {
        networkAPIFactory = NetworkHttpAPIFactoryImpl.getInstance();
    }


    public static void initConfig(NetworkAPIConfig config) {
        networkAPIFactory.initConfig(config);
    }

    public static NetworkAPIConfig getConfig() {
        return networkAPIFactory.getConfig();
    }

    public static SocializeAPI getSocializeAPI() {
        return networkAPIFactory.getSocializeAPI();
    }

}
