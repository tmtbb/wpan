package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/16.
 */

public class RechargeRecordItemEntity extends BaseEntity {
    /**
     * {
     * "rid": 10000002,
     * "id": 10001,
     * "amount": 100.1,
     * "depositTime": 1483422506,
     * "depositType": 1,
     * "depositName": "微信",
     * "status": 1
     * },
     */
    @FieldJsonKey("rid")
    private long rid;
    @FieldJsonKey("id")
    private long id;
    @FieldJsonKey("amount")
    private double amount;
    @FieldJsonKey("depositTime")
    private String depositTime;
    @FieldJsonKey("depositType")
    private int depositType;
    @FieldJsonKey("depositName")
    private String depositName;
    @FieldJsonKey("status")
    private int status;
    @FieldJsonKey("icon")
    private String icon;
    @FieldJsonKey("money")
    private String money;
    @FieldJsonKey("info")
    private String info;

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(String depositTime) {
        this.depositTime = depositTime;
    }

    public int getDepositType() {
        return depositType;
    }

    public void setDepositType(int depositType) {
        this.depositType = depositType;
    }

    public String  getDepositName() {
        return depositName;
    }

    public void setDepositName(String depositName) {
        this.depositName = depositName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
