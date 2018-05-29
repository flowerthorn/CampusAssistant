package com.csxy.box.business.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.bean.Weather;
import com.csxy.box.constant.UrlConstant;
import com.csxy.box.net.ApiRequest;
import com.lib.mylibrary.utils.StatusBarUtils;
import com.lib.mylibrary.utils.TimeUtils;
import com.pocketmoney.net.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/29.
 * 很久之前的代码 直接迁移。没有mvp
 */
public class WeatherActivity extends BaseActivity {
    private RelativeLayout rlWeather;
    private TextView city_tv, pm_tv, temp_tv, wind_tv, date_tv, weather_tv;
    private TextView item_weather_tv, item_week_tv, item_temp_tv;
    private ImageView search_iv;
    private HorizontalScrollView weather_other_sv;
    private LinearLayout weather_other_ll;
    private int cityCode = 101070203;
    private MyHandler myHandler = new MyHandler();
    private List<Weather> weatherList;   //用数组存储一周的天气情况
    private String[] weekStr = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static void actionShow(Context context) {
        Intent intent = new Intent(context, WeatherActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        StatusBarUtils.fillStatusBar(mContext);
        initView(); //初始化控件
        initBg();//初始化背景
        getData();  //获取天气数据
        search_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WeatherSearchActivity.class);
                // 如果想在Activity中得到新打开Activity 关闭后返回的数据
                startActivityForResult(intent, 100);
            }
        });
    }

    private void initBg() {
        Date d = new Date();
        if (d.getHours() < 18) {
            rlWeather.setBackgroundDrawable(getResources().getDrawable(R.drawable.weather_bg_day));
        } else {
            rlWeather.setBackgroundDrawable(getResources().getDrawable(R.drawable.weather_bg_night));
        }
    }


    /**
     * 更新界面数据
     */
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            city_tv.setText(weatherList.get(0).getCity());
            pm_tv.setText(weatherList.get(0).getPm());
            temp_tv.setText(weatherList.get(0).getTemp());
            wind_tv.setText(weatherList.get(0).getWind());
            date_tv.setText(weatherList.get(0).getDate());
            weather_tv.setText(weatherList.get(0).getWeather());
            weather_other_ll.removeAllViews();

            for (int i = 1; i < 6; i++) {
                Weather weather = weatherList.get(i);
                View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.weather_item, null);
                item_temp_tv = view.findViewById(R.id.item_temp_tv);
                item_weather_tv = view.findViewById(R.id.item_weather_tv);
                item_week_tv = view.findViewById(R.id.item_week_tv);
                item_temp_tv.setText(weather.getItem_temp());
                item_weather_tv.setText(weather.getItem_weather());
                item_week_tv.setText(weather.getItem_week());
                weather_other_ll.addView(view);
            }

        }
    }

    private List<Weather> getData() {
        weatherList = new ArrayList<>();

        String url = UrlConstant.Weather_BASE_URL + cityCode + ".html";
        ApiRequest.stringGet(this, url, new StringCallback() {
            @Override
            public void onResponse(String url, String response, int statusCode) {
                String data = response;
                data = data.substring(data.indexOf("(") + 1, data.indexOf(")"));
                try {
                    JSONObject rootObject = new JSONObject(data); //得到JSON数据的根对象
                    JSONObject jsObject = rootObject.getJSONObject("weatherinfo"); //取出weatherinfo对应的值

                    for (int i = 0; i < 6; i++) {
                        Weather weather = new Weather();
                        if (i == 0) {
                            weather.setCity(jsObject.getString("city"));
                            weather.setPm("PM:" + jsObject.getString("pm") + " " + jsObject.getString("pm-level"));
                            weather.setTemp(jsObject.getString("temp") + "°");
                            weather.setWind(jsObject.getString("wd") + "" + jsObject.getString("ws"));
                            weather.setDate(jsObject.getString("date_y") + " " + jsObject.getString("week"));
                            weather.setWeather(jsObject.getString("weather1") + " " + jsObject.getString("temp1"));
                            weather.setPm_color(jsObject.getInt("pm"));
                            for (int j = 0; j < 7; j++) {
                                if (weekStr[j].equals(jsObject.getString("week"))) {
                                    weather.setWeek(j);
                                }
                            }
                            weatherList.add(weather);
                        } else {
                            setWeek(weather, i);
                            weather.setItem_weather(jsObject.getString("weather" + (i + 1)));
                            weather.setItem_temp(jsObject.getString("temp" + (i + 1)));
                            weatherList.add(weather);
                        }
                    }
                    Message message = Message.obtain();
                    myHandler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String url, int statusCode) {

            }
        });
        return weatherList;
    }

    private void setWeek(Weather weather, int i) {
        int a = (weatherList.get(0).getWeek() + i) % 7;
        switch (a) {
            case 0:
                weather.setItem_week("星期日");
                break;
            case 1:
                weather.setItem_week("星期一");
                break;
            case 2:
                weather.setItem_week("星期二");
                break;
            case 3:
                weather.setItem_week("星期三");
                break;
            case 4:
                weather.setItem_week("星期四");
                break;
            case 5:
                weather.setItem_week("星期五");
                break;
            case 6:
                weather.setItem_week("星期六");
                break;
        }
    }

    private void initView() {
        rlWeather = findViewById(R.id.weather_rl);
        city_tv = findViewById(R.id.city_tv);
        pm_tv = findViewById(R.id.pm_tv);
        temp_tv = findViewById(R.id.temp_tv);
        wind_tv = findViewById(R.id.wind_tv);
        date_tv = findViewById(R.id.date_tv);
        weather_tv = findViewById(R.id.weather_tv);
        search_iv = findViewById(R.id.search_iv);
        weather_other_sv = findViewById(R.id.weather_other_sv);
        weather_other_ll = findViewById(R.id.weather_other_ll);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            cityCode = data.getIntExtra("CODE", 0);
            getData();
        }
    }

}
