package com.xinyu.mwp.entity;

/**
 * Created by Benjamin on 16/6/5.
 */
public class MyPopupMenuEntity extends BaseEntity {
    private String text;
    private int iconId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
