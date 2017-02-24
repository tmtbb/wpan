package com.xinyu.mwp.entity;

/**
 * Created by Administrator on 2017/2/22.
 */
public class SymbolInfosEntity extends BaseEntity {

    /**
     * exchangeName : TJPME
     * platformName : JH
     * symbol : AG
     * aType : 2
     */

    private String exchangeName;
    private String platformName;
    private String symbol;
    private int aType;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getAType() {
        return aType;
    }

    public void setAType(int aType) {
        this.aType = aType;
    }
}
