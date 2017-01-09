package com.xinyu.mwp.bean;

/**
 * Created by Tang on 2017/1/8.
 */

public class HoldBillProduct {
    private String name;
    private int weight;
    private float createStoragePrice;
    private float lossOrProfit;

    public HoldBillProduct() {
    }

    public HoldBillProduct(String name, int weight, float createStoragePrice, float lossOrProfit) {
        this.name = name;
        this.weight = weight;
        this.createStoragePrice = createStoragePrice;
        this.lossOrProfit = lossOrProfit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getCreateStoragePrice() {
        return createStoragePrice;
    }

    public void setCreateStoragePrice(float createStoragePrice) {
        this.createStoragePrice = createStoragePrice;
    }

    public float getLossOrProfit() {
        return lossOrProfit;
    }

    public void setLossOrProfit(float lossOrProfit) {
        this.lossOrProfit = lossOrProfit;
    }
}
