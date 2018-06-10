package com.csxy.box.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by lihongxin on 2018/6/10.
 */
public class EatItem extends DataSupport implements Serializable {
    private String id;//档口id标识
    private String location;//几食堂
    private String floor;//楼上楼下
    private String name;//档口名字
    private String telephone;//订餐电话

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
