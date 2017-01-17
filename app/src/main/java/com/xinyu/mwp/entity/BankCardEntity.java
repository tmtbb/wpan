package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/16 0016
 * @describe : com.xinyu.mwp.entity
 */
public class BankCardEntity extends BaseEntity {

    @FieldJsonKey("icon")
    private String icon;
    @FieldJsonKey("title")
    private String title;
    @FieldJsonKey("type")
    private String type;
    @FieldJsonKey("number")
    private String number;
    @FieldJsonKey("background")
    private String background;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
