package com.xinyu.mwp.entity;

/**
 * Created by yaowang on 2017/2/20.
 */

public class ProductEntity extends BaseEntity {

    private double amountPerLot;
    private double closeChargeFee;
    private String code;
    private double deferred;
    private double depositFee;
    private String exchangeName;
    private int id;
    private int maxLot;
    private int minLot;
    private String name;
    private double openChargeFee;
    private String platformName;
    private double profitPerUnit;
    private String showName;
    private String showSymbol;
    private int sort;
    private int status;
    private String symbol;
    private String unit;
    private double price;
    private double currentPrice;

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getAmountPerLot() {
        return amountPerLot;
    }

    public void setAmountPerLot(double amountPerLot) {
        this.amountPerLot = amountPerLot;
    }

    public double getCloseChargeFee() {
        return closeChargeFee;
    }

    public void setCloseChargeFee(double closeChargeFee) {
        this.closeChargeFee = closeChargeFee;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDeferred() {
        return deferred;
    }

    public void setDeferred(double deferred) {
        this.deferred = deferred;
    }

    public double getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(double depositFee) {
        this.depositFee = depositFee;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxLot() {
        return maxLot;
    }

    public void setMaxLot(int maxLot) {
        this.maxLot = maxLot;
    }

    public int getMinLot() {
        return minLot;
    }

    public void setMinLot(int minLot) {
        this.minLot = minLot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOpenChargeFee() {
        return openChargeFee;
    }

    public void setOpenChargeFee(double openChargeFee) {
        this.openChargeFee = openChargeFee;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public double getProfitPerUnit() {
        return profitPerUnit;
    }

    public void setProfitPerUnit(double profitPerUnit) {
        this.profitPerUnit = profitPerUnit;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowSymbol() {
        return showSymbol;
    }

    public void setShowSymbol(String showSymbol) {
        this.showSymbol = showSymbol;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
