package com.csxy.box.bean;


public class Weather {

    private String city;
    private String pm;
    private int pm_color;
    private String temp;
    private String wind;
    private String date;
    private String weather;
    private String item_weather, item_wind, item_temp;
    private int week;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }


    public String getItem_weather() {
        return item_weather;
    }

    public void setItem_weather(String item_weather) {
        this.item_weather = item_weather;
    }

    public String getItem_week() {
        return item_wind;
    }

    public void setItem_week(String item_wind) {
        this.item_wind = item_wind;
    }

    public String getItem_temp() {
        return item_temp;
    }

    public void setItem_temp(String item_temp) {
        this.item_temp = item_temp;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public int getPm_color() {
        return pm_color;
    }

    public void setPm_color(int pm_color) {
        this.pm_color = pm_color;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
