package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/11.
 */

public class UserAssetsItemEntity extends BaseEntity {
    @FieldJsonKey("time")
    private String time;
    @FieldJsonKey("silverPrice")
    private String silverPrice;
    @FieldJsonKey("oilPrice")
    private String oilPrice;
    @FieldJsonKey("cafePrice")
    private String cafePrice;

    private String timeTag;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSilverPrice() {
        return silverPrice;
    }

    public void setSilverPrice(String silverPrice) {
        this.silverPrice = silverPrice;
    }

    public String getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(String oilPrice) {
        this.oilPrice = oilPrice;
    }

    public String getCafePrice() {
        return cafePrice;
    }

    public void setCafePrice(String cafePrice) {
        this.cafePrice = cafePrice;
    }

    public String getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(String timeTag) {
        this.timeTag = timeTag;
    }
}
