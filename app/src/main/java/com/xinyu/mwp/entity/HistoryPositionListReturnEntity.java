package com.xinyu.mwp.entity;

/**
 * Created by Administrator on 2017/3/1.
 */
public class HistoryPositionListReturnEntity extends BaseEntity {

    /**
     * positionId : 1000001
     * id : 1000
     * code : sz10001
     * typeCode : st1001
     * name : 白银
     * buySell : -1
     * amount : 12
     * openPrice : 12.1
     * positionTime : 1483137783
     * openCost : 120
     * openCharge : 12.1
     * closeTime : 1483137783
     * closePrice : 119
     * grossProfit : 1.2
     * limit : 1
     * stop : 1.1
     * closeType : 1
     * isDeferred : 1
     * deferred : 12.1
     */

    private boolean result;
    private long positionId;
    private long id;
    private String code;
    private String typeCode;
    private String name;
    private int buySell;
    private double amount;
    private double openPrice;
    private long positionTime;
    private double openCost;
    private double openCharge;
    private long closeTime;
    private double closePrice;
    private double grossProfit;
    private double limit;
    private double stop;
    private int closeType;
    private boolean isDeferred;
    private double deferred;
    private int handle;
    private int codeId;

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuySell() {
        return buySell;
    }

    public void setBuySell(int buySell) {
        this.buySell = buySell;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public long getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(long positionTime) {
        this.positionTime = positionTime;
    }

    public double getOpenCost() {
        return openCost;
    }

    public void setOpenCost(double openCost) {
        this.openCost = openCost;
    }

    public double getOpenCharge() {
        return openCharge;
    }

    public void setOpenCharge(double openCharge) {
        this.openCharge = openCharge;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getStop() {
        return stop;
    }

    public void setStop(double stop) {
        this.stop = stop;
    }

    public int getCloseType() {
        return closeType;
    }

    public void setCloseType(int closeType) {
        this.closeType = closeType;
    }

    public boolean isDeferred() {
        return isDeferred;
    }

    public void setDeferred(boolean deferred) {
        isDeferred = deferred;
    }

    public double getDeferred() {
        return deferred;
    }

    public void setDeferred(double deferred) {
        this.deferred = deferred;
    }
}
