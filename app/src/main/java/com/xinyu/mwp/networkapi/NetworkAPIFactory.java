package com.xinyu.mwp.networkapi;

public interface NetworkAPIFactory {
    void initConfig(NetworkAPIConfig config);

    NetworkAPIConfig getConfig();

    SocializeAPI getSocializeAPI();

}
