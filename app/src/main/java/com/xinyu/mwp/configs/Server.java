package com.xinyu.mwp.configs;

import com.xinyu.mwp.BuildConfig;

/**
 * Created by Tang on 2017/1/7.
 */

public class Server {
    // 给测试人员的测试包地址和正式服务器地址
    public static String URL_SERVER_BASE = BuildConfig.DEBUG ? "https://xxx/xxx"
            : "http://xxx.xxx.xxx.xxx:8090";

    //用于设置服务器接口地址
    public static void initSite(String serverDomain) {
        Server.URL_SERVER_BASE = serverDomain;
    }
}
