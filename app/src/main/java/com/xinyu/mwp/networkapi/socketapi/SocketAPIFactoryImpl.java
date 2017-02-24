package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.networkapi.DealAPI;
import com.xinyu.mwp.networkapi.NetworkAPIConfig;
import com.xinyu.mwp.networkapi.NetworkAPIFactory;
import com.xinyu.mwp.networkapi.UserAPI;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPIRequestManage;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketAPIFactoryImpl implements NetworkAPIFactory {

    private NetworkAPIConfig config;

    private static SocketAPIFactoryImpl socketAPIFactory = null;

    public static synchronized NetworkAPIFactory getInstance() {
        if (socketAPIFactory == null) {
            socketAPIFactory = new SocketAPIFactoryImpl();
        }
        return socketAPIFactory;
    }

    @Override
    public void initConfig(NetworkAPIConfig config) {
        this.config = config;
        SocketAPIRequestManage.getInstance().start();
    }

    @Override
    public NetworkAPIConfig getConfig() {
        return config;
    }

    @Override
    public UserAPI getUserAPI() {
        return new SocketUserAPI();
    }

    @Override
    public DealAPI getDealAPI() {
        return new SocketDealAPI();
    }

}
