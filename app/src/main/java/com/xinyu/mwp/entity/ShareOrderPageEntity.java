package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/11 0011
 * @describe : com.xinyu.mwp.entity
 */
public class ShareOrderPageEntity extends BaseEntity {

    @FieldJsonKey("headUrl")
    private String headUrl;
    @FieldJsonKey("name")
    private String name;
    @FieldJsonKey("content")
    private String content;

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
