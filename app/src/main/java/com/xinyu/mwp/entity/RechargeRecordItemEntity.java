package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/16.
 */

public class RechargeRecordItemEntity extends BaseEntity {
    @FieldJsonKey("timeWeek")
    private String timeWeek;
    @FieldJsonKey("timeDate")
    private String timeDate;
    @FieldJsonKey("time")
    private String time;
    @FieldJsonKey("status")
    private String status;
    @FieldJsonKey("icon")
    private String icon;
    @FieldJsonKey("money")
    private String money;
    @FieldJsonKey("info")
    private String info;

    public String getTimeWeek() {
        return timeWeek;
    }

    public void setTimeWeek(String timeWeek) {
        this.timeWeek = timeWeek;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
