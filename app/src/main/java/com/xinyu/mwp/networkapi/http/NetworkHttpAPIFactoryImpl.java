package com.xinyu.mwp.networkapi.http;

import com.xinyu.mwp.networkapi.NetworkAPIConfig;
import com.xinyu.mwp.networkapi.NetworkAPIFactory;
import com.xinyu.mwp.networkapi.SocializeAPI;

public class NetworkHttpAPIFactoryImpl implements NetworkAPIFactory {
    private NetworkAPIConfig config;
    private SocializeAPI socializeAPI;
    private static NetworkHttpAPIFactoryImpl networkHttpAPIFactory = null;

    public static NetworkAPIFactory getInstance() {
        if (networkHttpAPIFactory == null) {
            networkHttpAPIFactory = new NetworkHttpAPIFactoryImpl();
        }
        return networkHttpAPIFactory;
    }

    private NetworkHttpAPIFactoryImpl() {

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

    @Override
    public SocializeAPI getSocializeAPI() {
        return socializeAPI;
    }

}
