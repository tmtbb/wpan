package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/13.
 */

public class CashRecordEntity extends BaseEntity {
    @FieldJsonKey("date")
    private String date;
    @FieldJsonKey("time")
    private String time;
    @FieldJsonKey("type")
    private String type;
    @FieldJsonKey("money")
    private String money;
    @FieldJsonKey("status")
    private String status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
