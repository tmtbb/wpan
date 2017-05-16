package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/16 0016
 * @describe : com.xinyu.mwp.entity
 */
public class BankCardEntity extends BaseEntity {

    @FieldJsonKey("bid")
    private long bid;
    @FieldJsonKey("id")
    private long id;
    @FieldJsonKey("bank")
    private String bank;
    @FieldJsonKey("branchBank")
    private String branchBank;
    @FieldJsonKey("cardNo")
    private String cardNo;
    @FieldJsonKey("name")
    private String name;
    @FieldJsonKey("backGround")
    private String backGround;
    @FieldJsonKey("icon")
    private String icon;
    @FieldJsonKey("cardName")
    private String cardName;
    @FieldJsonKey("isDefault")
    private int isDefault;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranchBank() {
        return branchBank;
    }

    public void setBranchBank(String branchBank) {
        this.branchBank = branchBank;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
}
