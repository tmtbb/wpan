package com.xinyu.mwp.entity;

/**
 * Created by Administrator on 2017/3/28.
 */
public class CurrentPositionEntity extends BaseEntity{

    /**
     * gid : 12
     * id : 7
     * name : JL884
     */

    private int gid;
    private int id;
    private String name = "";
    private String currentPositionName = "";

    public String getCurrentPositionName() {
        return currentPositionName;
    }

    public void setCurrentPositionName(String currentPositionName) {
        this.currentPositionName = currentPositionName;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
