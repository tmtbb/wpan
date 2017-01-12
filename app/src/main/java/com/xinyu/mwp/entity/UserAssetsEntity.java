package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

import java.util.List;

/**
 * Created by Benjamin on 17/1/11.
 */

public class UserAssetsEntity extends BaseEntity {
    @FieldJsonKey("money")
    private String money;
    @FieldJsonKey("assets")
    private List<UserAssetsItemEntity> assets;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<UserAssetsItemEntity> getAssets() {
        return assets;
    }

    public void setAssets(List<UserAssetsItemEntity> assets) {
        this.assets = assets;
    }
}
