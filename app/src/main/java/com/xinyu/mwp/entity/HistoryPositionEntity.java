package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * 历史持仓记录bean对象
 */

public class HistoryPositionEntity extends BaseEntity {
    @FieldJsonKey("name")
    private String name;
    @FieldJsonKey("time")
    private String time;
    @FieldJsonKey("price")
    private String price;
    @FieldJsonKey("stop")
    private String stop; //止损
    @FieldJsonKey("limit")
    private String limit; //止盈

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
