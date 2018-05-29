package com.csxy.box.business.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.utils.XmlParser;
import com.lib.mylibrary.utils.StatusBarUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihongxin on 2018/5/29.
 * 很久之前的代码 直接迁移。没有mvp
 */
public class WeatherSearchActivity extends BaseActivity {
    private EditText search_et;
    private Button beijing;
    private Button shanghai;
    private Button shenzhen;
    private Button suzhou;
    private Button zhengzhou;
    private Button nanjing;
    private Button wuhan;
    private Button xian;
    private Button tianjin;
    private Button chengdu;
    private Button dalian;
    private Button xiamen;
    private ImageView city_search_iv;
    private LinearLayout ll_weather_search;
    private Map<String, String> cityMap = new HashMap<String, String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_search);
        StatusBarUtils.fillStatusBar(mContext);
        initData();
        ll_weather_search = findViewById(R.id.weather_search_ll);
        search_et = (EditText) findViewById(R.id.search_et);
        city_search_iv = (ImageView) findViewById(R.id.city_search_iv);
        beijing = (Button) findViewById(R.id.beijing);
        shenzhen = (Button) findViewById(R.id.shenzhen);
        shanghai = (Button) findViewById(R.id.shanghai);
        suzhou = (Button) findViewById(R.id.suzhou);
        zhengzhou = (Button) findViewById(R.id.zhengzhou);
        nanjing = (Button) findViewById(R.id.nanjing);
        wuhan = (Button) findViewById(R.id.wuhan);
        xian = (Button) findViewById(R.id.xian);
        tianjin = (Button) findViewById(R.id.tianjin);
        tianjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("天津");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        xian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("西安");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        wuhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("武汉");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        nanjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("南京");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        zhengzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("郑州");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        suzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("苏州");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        MyClickListener listener = new MyClickListener();
        city_search_iv.setOnClickListener(listener);
        beijing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("北京");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });

        shenzhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("深圳");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        shanghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("上海");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        chengdu = (Button) findViewById(R.id.chengdu);
        chengdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("成都");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        dalian = (Button) findViewById(R.id.dalian);
        xiamen = (Button) findViewById(R.id.xiamen);
        dalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("大连");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        xiamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityCode = cityMap.get("厦门");
                int code = Integer.parseInt(cityCode);
                Intent intent = new Intent();
                intent.putExtra("CODE", code);
                setResult(200, intent);
                finish();
            }
        });
        initBg();
    }

    private void initBg() {
        Date d = new Date();
        if (d.getHours() < 18) {
            ll_weather_search.setBackgroundDrawable(getResources().getDrawable(R.drawable.weather_bg_day));
        } else {
            ll_weather_search.setBackgroundDrawable(getResources().getDrawable(R.drawable.weather_bg_night));
        }
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String cityName = search_et.getText().toString();
            if (TextUtils.isEmpty(cityName)) {
                finish();
            } else {
                String cityCode = cityMap.get(cityName);
                if (TextUtils.isEmpty(cityCode)) {
                    Toast.makeText(getApplicationContext(), "城市输入不合法，请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    int code = Integer.parseInt(cityCode);
                    Intent intent = new Intent();
                    intent.putExtra("CODE", code);
                    setResult(200, intent);
                    finish();
                }
            }
        }
    }

    private void initData() {
        try {
            InputStream is = getAssets().open("city_code.xml");
            cityMap = XmlParser.parseCity(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
