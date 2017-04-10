package com.xinyu.mwp.entity;

/**
 * Created by Administrator on 2017/4/10.
 */
public class BankInfoEntity extends BaseEntity {

    /**
     * {"bankId":165,"bankName":"中国农业银行","cardNO":"6228480402564890018","cardName":"农业银行·金穗通宝卡(银联卡)"}
     */
    private int result;
    private long bankId;
    private long bid;  //银行卡id
    private String brachName; //支行
    private String name; //姓名
    private String bankName;
    private String cardNO;
    private String cardName;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getBrachName() {
        return brachName;
    }

    public void setBrachName(String brachName) {
        this.brachName = brachName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNO() {
        return cardNO;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
