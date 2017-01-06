package com.xinyu.mwp.bean;

/**
 * Created by Don on 2017/1/6.
 * Describe ${TODO}
 * Modified ${TODO}
 */

public class ProductBean {
    private String name;
    private float price;
    private float wavePerPrice;
    private float wavePrice;
    private float maxPrice;
    private float minPrice;
    private float todayPrice;
    private float yesterdayPrice;

    public ProductBean() {
    }

    public ProductBean(String name, float price, float wavePerPrice, float wavePrice, float maxPrice, float minPrice, float todayPrice, float yesterdayPrice) {
        this.name = name;
        this.price = price;
        this.wavePerPrice = wavePerPrice;
        this.wavePrice = wavePrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.todayPrice = todayPrice;
        this.yesterdayPrice = yesterdayPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWavePerPrice() {
        return wavePerPrice;
    }

    public void setWavePerPrice(float wavePerPrice) {
        this.wavePerPrice = wavePerPrice;
    }

    public float getWavePrice() {
        return wavePrice;
    }

    public void setWavePrice(float wavePrice) {
        this.wavePrice = wavePrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(float todayPrice) {
        this.todayPrice = todayPrice;
    }

    public float getYesterdayPrice() {
        return yesterdayPrice;
    }

    public void setYesterdayPrice(float yesterdayPrice) {
        this.yesterdayPrice = yesterdayPrice;
    }
}
