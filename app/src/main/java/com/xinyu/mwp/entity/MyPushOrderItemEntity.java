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
