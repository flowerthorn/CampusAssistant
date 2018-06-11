package com.csxy.box.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class NewsItem extends DataSupport implements Serializable {

    private String title;
    private String date;
    private String author_name;
    private String thumbnail_pic_s;
    private String url;


    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }


    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
