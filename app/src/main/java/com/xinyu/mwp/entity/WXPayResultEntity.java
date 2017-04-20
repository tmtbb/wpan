package com.xinyu.mwp.entity;

/**
 * Created by Administrator on 2017/4/4.
 */
public class WXPayResultEntity extends BaseEntity {
    private int returnCode;
    private double balance;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
