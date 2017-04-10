package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by yaowang on 2017/2/20.
 */

public class UserinfoEntity extends BaseEntity {

    @FieldJsonKey("id")
    private int id;
//    @FieldJsonKey("screenName")
//    private String screenName;
//    @FieldJsonKey("memberId")
//    private String memberId;
//    @FieldJsonKey("memberName")
//    private String memberName;
//    @FieldJsonKey("agentId")
//    private String agentId;
//    @FieldJsonKey("agentName")
//    private String agentName;
//    @FieldJsonKey("avatarLarge")
//    private String avatarLarge;
    @FieldJsonKey("balance")
    private double balance;
    @FieldJsonKey("phone")
    private String phone;
    @FieldJsonKey("type")
    private int type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getScreenName() {
//        return screenName;
//    }
//
//    public void setScreenName(String screenName) {
//        this.screenName = screenName;
//    }
//
//    public String getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(String memberId) {
//        this.memberId = memberId;
//    }
//
//    public String getMemberName() {
//        return memberName;
//    }

//    public void setMemberName(String memberName) {
//        this.memberName = memberName;
//    }
//
//    public String getAgentId() {
//        return agentId;
//    }
//
//    public void setAgentId(String agentId) {
//        this.agentId = agentId;
//    }
//
//    public String getAgentName() {
//        return agentName;
//    }
//
//    public void setAgentName(String agentName) {
//        this.agentName = agentName;
//    }
//
//    public String getAvatarLarge() {
//        return avatarLarge;
//    }
//
//    public void setAvatarLarge(String avatarLarge) {
//        this.avatarLarge = avatarLarge;
//    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
