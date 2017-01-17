package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

import java.util.List;

/**
 * Created by Benjamin on 17/1/16.
 */

public class RechargeRecordEntity extends BaseEntity {
    @FieldJsonKey("time")
    private String time;
    @FieldJsonKey("infos")
    private List<RechargeRecordItemEntity> info;

    public List<RechargeRecordItemEntity> getInfo() {
        return info;
    }

    public void setInfo(List<RechargeRecordItemEntity> info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
