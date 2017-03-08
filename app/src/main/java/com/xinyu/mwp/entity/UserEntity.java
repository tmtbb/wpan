package com.xinyu.mwp.entity;


import com.xinyu.mwp.annotation.FieldJsonKey;

import java.io.Serializable;

public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @FieldJsonKey("id")
    private int id = -1;
    @FieldJsonKey("token")
    private String token = "";
    @FieldJsonKey("icon")
    private String icon = "";
    @FieldJsonKey("name")
    private String name = "";
    @FieldJsonKey("username")
    private String username = "";

    @FieldJsonKey("nickname")
    private String nickname = "";
    @FieldJsonKey("sex")
    private String sex = "";
    @FieldJsonKey("region")
    private String region = "";
    @FieldJsonKey("birthday")
    private String birthday = "";
    @FieldJsonKey("mobile")
    private String mobile = "";
    @FieldJsonKey("email")
    private String email = "";
    @FieldJsonKey("qq")
    private String qq = "";
    @FieldJsonKey("userSign")
    private String userSign = "";
    @FieldJsonKey("relation")
    private int relation;
    @FieldJsonKey("news")
    private int news;
    @FieldJsonKey("fans")
    private int fans;
    @FieldJsonKey("isfriend")
    private int isfriend;
    @FieldJsonKey("isprivate")
    private int isprivate;

    @FieldJsonKey("isgoddess")
    private int isgoddess;
    @FieldJsonKey("isonline")
    private int isonline;
    @FieldJsonKey("roomid")
    private int roomid;
    @FieldJsonKey("union")
    private String union = "";
    @FieldJsonKey("type")
    private String type;
    @FieldJsonKey("time")
    private String time = "";
    @FieldJsonKey("bi")
    private String bi = "";
    @FieldJsonKey("source")
    private String source = "";

    @FieldJsonKey("weixin")
    private String weixin = "";
    @FieldJsonKey("weibo")
    private String weibo = "";

    //经验详情
    @FieldJsonKey("jingyan")
    private String jingyan = "";
    @FieldJsonKey("maxRank")
    private String maxRank = "";
    @FieldJsonKey("rank")
    private String rank = "";


    //用户等级
    @FieldJsonKey("levelsName")
    private String levelsName;
    @Deprecated
    @FieldJsonKey("levels")
    private String levels = "1";
    @FieldJsonKey("integral")
    private String integral;

    //用户账户
    @FieldJsonKey("money")
    private int money = 0;
    @FieldJsonKey("stock")
    private String stock = "";
    @FieldJsonKey("minMoney")
    private String minMoney = "";

    @FieldJsonKey("beanNumber")
    private String beanNumber = "";

    //用户类型
    @FieldJsonKey("userType")
    private String userType = "0";//0：普通用户，1：充值用户
    @FieldJsonKey("balance")
    private double balance =0;
    @FieldJsonKey("spreadUser")
    private String spreadUser;//0：普通用户，1：推广用户
    @FieldJsonKey("showWindow")
    private String showWindow;
    @FieldJsonKey("isOfficial")
    private String isOfficial;//0:否,1:是

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(String minMoney) {
        this.minMoney = minMoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getSex() {
        if (sex.equals("未知")) {
            sex = "0";
        } else if (sex.equals("男")) {
            sex = "1";
        } else if (sex.equals("女")) {
            sex = "2";
        }
        return sex;
    }

    public String getFormatSex() {
        if (sex.equals("0")) {
            sex = "未知";
        } else if (sex.equals("1")) {
            sex = "男";
        } else if (sex.equals("2")) {
            sex = "女";
        }
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public UserEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public int getNews() {
        return news;
    }

    public void setNews(int news) {
        this.news = news;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsprivate() {
        return isprivate;
    }

    public void setIsprivate(int isprivate) {
        this.isprivate = isprivate;
    }

    public int getIsgoddess() {
        return isgoddess;
    }

    public void setIsgoddess(int isgoddess) {
        this.isgoddess = isgoddess;
    }

    public int getIsonline() {
        return isonline;
    }

    public void setIsonline(int isonline) {
        this.isonline = isonline;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getJingyan() {
        return jingyan;
    }

    public void setJingyan(String jingyan) {
        this.jingyan = jingyan;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(String maxRank) {
        this.maxRank = maxRank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getLevelsName() {
        return levelsName;
    }

    public void setLevelsName(String levelsName) {
        this.levelsName = levelsName;
    }

    @Deprecated
    public String getLevels() {
        return levels;
    }

    @Deprecated
    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getBeanNumber() {
        return beanNumber;
    }

    public void setBeanNumber(String beanNumber) {
        this.beanNumber = beanNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSpreadUser() {
        return spreadUser;
    }

    public void setSpreadUser(String spreadUser) {
        this.spreadUser = spreadUser;
    }

    public String getShowWindow() {
        return showWindow;
    }

    public void setShowWindow(String showWindow) {
        this.showWindow = showWindow;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    //	@Override
//	public String toString()
//	{
//		return "UserInfo [id=" + id + ", token=" + token + ", icon=" + icon + ", nickname=" + nickname + ", sex=" + sex + ", region=" + region + ", birthday=" + birthday
//				+ ", mobile=" + mobile + ", email=" + email + ", qq=" + qq + ", sign=" + sign + ", relation=" + relation + ", news=" + news + ", fans=" + fans + ", isfriend="
//				+ isfriend + ", isprivate=" + isprivate + ", rank=" + rank + ", isgoddess=" + isgoddess + ", isonline=" + isonline + "]";
//	}


}
