package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealAllGoodsTopEntity extends BaseEntity {
    @FieldJsonKey("money")
    private String money;
    @FieldJsonKey("hands")
    private String hands;
    @FieldJsonKey("orders")
    private String orders;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getHands() {
        return hands;
    }

    public void setHands(String hands) {
        this.hands = hands;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
