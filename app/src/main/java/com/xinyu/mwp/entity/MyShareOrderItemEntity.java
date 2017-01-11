package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyShareOrderItemEntity extends BaseEntity {
    @FieldJsonKey("name")
    private String name;
    @FieldJsonKey("status")
    private String status;
    @FieldJsonKey("createDepotTime")
    private String createDepotTime;
    @FieldJsonKey("createDepotPrice")
    private String createDepotPrice;
    @FieldJsonKey("flatDepotTime")
    private String flatDepotTime;
    @FieldJsonKey("flatDepotPrice")
    private String flatDepotPrice;
    @FieldJsonKey("profit")
    private String profit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDepotTime() {
        return createDepotTime;
    }

    public void setCreateDepotTime(String createDepotTime) {
        this.createDepotTime = createDepotTime;
    }

    public String getCreateDepotPrice() {
        return createDepotPrice;
    }

    public void setCreateDepotPrice(String createDepotPrice) {
        this.createDepotPrice = createDepotPrice;
    }

    public String getFlatDepotTime() {
        return flatDepotTime;
    }

    public void setFlatDepotTime(String flatDepotTime) {
        this.flatDepotTime = flatDepotTime;
    }

    public String getFlatDepotPrice() {
        return flatDepotPrice;
    }

    public void setFlatDepotPrice(String flatDepotPrice) {
        this.flatDepotPrice = flatDepotPrice;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
