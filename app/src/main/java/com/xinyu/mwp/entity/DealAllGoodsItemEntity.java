package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealAllGoodsItemEntity extends BaseEntity {
    @FieldJsonKey("time")
    private String time;
    @FieldJsonKey("buyUp")
    private String buyUp;
    @FieldJsonKey("buyDown")
    private String buyDown;
    @FieldJsonKey("createDepot")
    private String createDepot;
    @FieldJsonKey("flatDepot")
    private String flatDepot;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
}
