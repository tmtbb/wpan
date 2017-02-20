package com.xinyu.mwp.networkapi.http;

import com.xinyu.mwp.networkapi.DealAPI;
import com.xinyu.mwp.networkapi.NetworkAPIConfig;
import com.xinyu.mwp.networkapi.NetworkAPIFactory;
import com.xinyu.mwp.networkapi.SocializeAPI;
import com.xinyu.mwp.networkapi.UserAPI;

public class NetworkHttpAPIFactoryImpl implements NetworkAPIFactory {
    private NetworkAPIConfig config;
    private SocializeAPI socializeAPI;
    private static NetworkHttpAPIFactoryImpl networkHttpAPIFactory = null;

    public static synchronized NetworkAPIFactory getInstance() {
        if (networkHttpAPIFactory == null) {
            networkHttpAPIFactory = new NetworkHttpAPIFactoryImpl();
        }
        return networkHttpAPIFactory;
    }

    private NetworkHttpAPIFactoryImpl() {

    }

    @Override
    public UserAPI getUserAPI() {
        return null;
    }

    @Override
    public DealAPI getDealAPI() {
        return null;
    }

    @Override
    public void initConfig(NetworkAPIConfig config) {
        this.config = config;
        socializeAPI = new SocializeAPIImpl();
    }

    @Override
    public NetworkAPIConfig getConfig() {
        return config;
    }
//
//    @Override
//    public SocializeAPI getSocializeAPI() {
//        return socializeAPI;
//    }

}
