package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

import java.util.List;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyPushOrderEntity extends BaseEntity {
    @FieldJsonKey("dayCount")
    private String dayCount;
    @FieldJsonKey("weekCount")
    private String weekCount;
    @FieldJsonKey("monthCount")
    private String monthCount;

    @FieldJsonKey("pushOrders")
    private List<MyPushOrderItemEntity> pushOrders;

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(String weekCount) {
        this.weekCount = weekCount;
    }

    public String getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(String monthCount) {
        this.monthCount = monthCount;
    }

    public List<MyPushOrderItemEntity> getPushOrders() {
        return pushOrders;
    }

    public void setPushOrders(List<MyPushOrderItemEntity> pushOrders) {
        this.pushOrders = pushOrders;
    }
}
