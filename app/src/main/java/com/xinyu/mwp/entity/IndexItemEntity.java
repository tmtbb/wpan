package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/13 0013
 * @describe : com.xinyu.mwp.entity
 */
public class IndexItemEntity extends BaseEntity {

    @FieldJsonKey("title")
    private String title;
    @FieldJsonKey("unit")
    private String unit;
    @FieldJsonKey("price")
    private String price;
    @FieldJsonKey("percent")
    private String percent;
    @FieldJsonKey("value")
    private String value;
    @FieldJsonKey("highPrice")
    private String highPrice;
    @FieldJsonKey("lowPrice")
    private String lowPrice;
    @FieldJsonKey("todayPrice")
    private String todayPrice;
    @FieldJsonKey("yesterdayPrice")
    private String yesterdayPrice;
    @FieldJsonKey("priceChange")
    private String priceChange;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }

    public String getYesterdayPrice() {
        return yesterdayPrice;
    }

    public void setYesterdayPrice(String yesterdayPrice) {
        this.yesterdayPrice = yesterdayPrice;
    }
}
