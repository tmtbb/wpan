package com.xinyu.mwp.entity;

import com.xinyu.mwp.annotation.FieldJsonKey;

/**
 * Created by Benjamin on 17/1/11.
 */

public class UnitEntity extends BaseEntity {
    @FieldJsonKey("id")
    private String id;
    @FieldJsonKey("name")
    private String name;
    @FieldJsonKey("icon")
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
