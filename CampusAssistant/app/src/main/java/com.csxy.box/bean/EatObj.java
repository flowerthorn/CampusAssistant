package com.csxy.box.bean;

import java.util.List;

/**
 * Created by lihongxin on 2018/6/10.
 */
public class EatObj {

    private  List<Data> yy;

    public  List<Data> getYy() {
        return yy;
    }

    public  void setYy(List<Data> yY) {
        yy = yY;
    }

    public class Data{
        List<EatItem> data;

        public List<EatItem> getData() {
            return data;
        }

        public void setData(List<EatItem> data) {
            this.data = data;
        }
    }
}
