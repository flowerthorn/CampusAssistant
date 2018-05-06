package com.csxy.box;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.csxy.box.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    }
