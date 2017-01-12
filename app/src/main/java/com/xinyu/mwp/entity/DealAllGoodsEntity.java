package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

import java.util.List;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealAllGoodsEntity extends BaseEntity {
    @FieldJsonKey("buyUp")
    private String buyUp;
    @FieldJsonKey("buyDown")
    private String buyDown;
    @FieldJsonKey("createDepot")
    private String createDepot;
    @FieldJsonKey("flatDepot")
    private String flatDepot;

    @FieldJsonKey("goodsInfo")
    private List<DealAllGoodsItemEntity> goodsInfo;

    public String getBuyUp() {
        return buyUp;
    }

    public void setBuyUp(String buyUp) {
        this.buyUp = buyUp;
    }

    public String getBuyDown() {
        return buyDown;
    }

    public void setBuyDown(String buyDown) {
        this.buyDown = buyDown;
    }

    public String getCreateDepot() {
        return createDepot;
    }

    public void setCreateDepot(String createDepot) {
        this.createDepot = createDepot;
    }

    public String getFlatDepot() {
        return flatDepot;
    }

    public void setFlatDepot(String flatDepot) {
        this.flatDepot = flatDepot;
    }

    public List<DealAllGoodsItemEntity> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<DealAllGoodsItemEntity> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
