package com.xinyu.mwp.bean;

import android.text.SpannableString;

/**
 * Created by Don on 2017/1/6.
 * Describe ${TODO}
 * Modified ${TODO}
 */
public class TraceBillBean {
    private int imgRes;//临时数据
    private String imgUrl;//实际开发时头像json数据为字符串url
    private String personName;
    private SpannableString product;
    private String productOverTime;
    private String productCreateTime;
    private float productPrice;
    private int numPersonAttention;

    public TraceBillBean() {
    }

    public TraceBillBean(int imgRes, String imgUrl, String personName, SpannableString product, String productOverTime, String productCreateTime, float productPrice, int numPersonAttention) {
        this.imgRes = imgRes;
        this.imgUrl = imgUrl;
        this.personName = personName;
        this.product = product;
        this.productOverTime = productOverTime;
        this.productCreateTime = productCreateTime;
        this.productPrice = productPrice;
        this.numPersonAttention = numPersonAttention;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public SpannableString getProduct() {
        return product;
    }

    public void setProduct(SpannableString product) {
        this.product = product;
    }

    public String getProductOverTime() {
        return productOverTime;
    }

    public void setProductOverTime(String productOverTime) {
        this.productOverTime = productOverTime;
    }

    public String getProductCreateTime() {
        return productCreateTime;
    }

    public void setProductCreateTime(String productCreateTime) {
        this.productCreateTime = productCreateTime;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getNumPersonAttention() {
        return numPersonAttention;
    }

    public void setNumPersonAttention(int numPersonAttention) {
        this.numPersonAttention = numPersonAttention;
    }
}
