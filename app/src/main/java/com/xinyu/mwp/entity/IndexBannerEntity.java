package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-05-10 09:54
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class IndexBannerEntity extends BaseEntity {

    @FieldJsonKey("img")
    private String img = "";
    @FieldJsonKey("jumpType")
    private String jumpType = "";
    @FieldJsonKey("jumpUrl")
    private String jumpUrl = "";
    @FieldJsonKey("jumpCategory")
    private String jumpCategory = "";
    @FieldJsonKey("jumpId")
    private String jumpId = "";

    @FieldJsonKey("name")
    private String name = "";
    @FieldJsonKey("gname")
    private String gname = "";
    @FieldJsonKey("location")
    private String location;
    @FieldJsonKey("title")
    private String title;
    @FieldJsonKey("content")
    private String content;
    @FieldJsonKey("advertCode")
    private String advertCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getJumpCategory() {
        return jumpCategory;
    }

    public void setJumpCategory(String jumpCategory) {
        this.jumpCategory = jumpCategory;
    }

    public String getJumpId() {
        return jumpId;
    }

    public void setJumpId(String jumpId) {
        this.jumpId = jumpId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdvertCode() {
        return advertCode;
    }

    public void setAdvertCode(String advertCode) {
        this.advertCode = advertCode;
    }
}
