package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyPushOrderItemEntity extends BaseEntity {
    @FieldJsonKey("sucOdds")
    private String sucOdds;
    @FieldJsonKey("profit")
    private String profit;
    @FieldJsonKey("sucCount")
    private String sucCount;
    @FieldJsonKey("orderNname")
    private String orderNname;
    @FieldJsonKey("time")
    private String time;
    @FieldJsonKey("orderPrice")
    private String orderPrice;

    public String getOrderNname() {
        return orderNname;
    }

    public void setOrderNname(String orderNname) {
        this.orderNname = orderNname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getSucOdds() {
        return sucOdds;
    }

    public void setSucOdds(String sucOdds) {
        this.sucOdds = sucOdds;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getSucCount() {
        return sucCount;
    }

    public void setSucCount(String sucCount) {
        this.sucCount = sucCount;
    }
}
