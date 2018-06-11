package com.csxy.box.bean;

import java.util.ArrayList;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class NewsObj {

    private String reason;
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getReason() {

        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public class Result{
        private String start;
        private ArrayList<NewsItem> data;

        public ArrayList<NewsItem> getData() {
            return data;
        }

        public void setData(ArrayList<NewsItem> data) {
            this.data = data;
        }

        public String getStart() {

            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

    }

}
