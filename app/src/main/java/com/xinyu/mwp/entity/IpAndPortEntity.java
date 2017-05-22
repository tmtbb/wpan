package com.xinyu.mwp.entity;

/**
 * Created by Administrator on 2017/5/22.
 */
public class IpAndPortEntity extends BaseEntity {
    private double port;
    private String ip;

    public double getPort() {
        return port;
    }

    public void setPort(double port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
