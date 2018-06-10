package com.csxy.box.constant;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class UrlConstant {
    private static final String HTTP = "http://";
    private static final String SERVER_HOST = "123.206.60.17/";
    private static final String BASE = "csxyxzs-master/index.php/Home/Campus/";
    private static final String LIBRARY = "appLibrary/title/";
    private static final String EAT = "appShitang/";
    private static final String MEAL = "appCaidan/id/";
    // 图书搜索接口
    public static final String SEARCH_BOOK = HTTP + SERVER_HOST + BASE + LIBRARY;
    //食堂档口搜索接口
    public static final String SEARCH_EAT = HTTP + SERVER_HOST + BASE + EAT;
    //食堂菜单搜索接口
    public static final String SEARCH_MEAL = HTTP + SERVER_HOST + BASE + MEAL;
    //快递链接
    public static final String DELIVER_QUERY = "http://m.kuaidi100.com/index_all.html";
    //城院全景链接
    public static final String SCENER = "http://720yun.com/t/a9625wa6xnw";
    //查询天气
    public static final String Weather_BASE_URL = "http://weather.123.duba.net/static/weather_info/";
    //查询四六级
    public static final String LEVEL_QUERY = "http://weixiao.qq.com/apps/public/cet/index.html";
    //程序GitHub 链接
    public static final String MY_GITHUB = "https://github.com/flowerthorn/CampusAssistant";

}
