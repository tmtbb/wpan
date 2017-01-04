package com.xinyu.mwp.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Don on 2016/12/28.
 * Describe ${TODO}
 * Modified ${TODO}
 */

public class RealmBaseBean extends RealmObject {
    @PrimaryKey
    protected String id;
    protected String name;

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
}
