package com.xinyu.mwp.bean;

import com.xinyu.mwp.base.BaseModel;

public class UserBean extends BaseModel {
    private String userId;
    private String nickName;
    private long lastLoginTime;
    private String birthday;
    private String email;
    private String address;
    private String sex;
    private int registerType;
    private long registerTime;
    private String iconAddress;
    private String phone;

    public UserBean() {
    }

    public String getNickName() {
        if (nickName == null) {
            return "";
        }
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getBirthday() {
        if (birthday == null) {
            return "";
        }
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        if (email == null) {
            return "";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        if (address == null) {
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        if (sex == null) {
            return "";
        }
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public String getIconAddress() {
        if (iconAddress == null) {
            return "";
        }
        return iconAddress;
    }

    public void setIconAddress(String iconAddress) {
        this.iconAddress = iconAddress;
    }

    public String getPhone() {
        if (phone == null) {
            return "";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        if (userId == null) {
            return "";
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isCorrect() {
        return false;
    }
}
