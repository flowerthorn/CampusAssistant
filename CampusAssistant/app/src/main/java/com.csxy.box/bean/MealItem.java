package com.csxy.box.bean;


import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by lihongxin on 2018/6/10.
 */
public class MealItem extends DataSupport implements Serializable {

    private String name;
    private String price;
    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
