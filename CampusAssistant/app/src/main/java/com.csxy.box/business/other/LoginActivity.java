package com.csxy.box.business.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.pref.UserPreferences;
import com.csxy.box.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.input_number)
    EditText inputNumber;
    @BindView(R.id.input_password)
    EditText inputPassword;

    public static void actionShow(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                gotoLogin();
                break;

        }
    }

    private void gotoLogin() {
        // TODO: 2018/6/11  应该为网络请求，因校网现在禁止外网访问，自己模拟请求
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //模拟服务端判断
                if ("201411110".equals(inputNumber.getText().toString().trim()) && "303020".equals(inputPassword.getText().toString().trim())) {
                    //json返回处理
                    ToastUtils.showLongToast("登陆成功");
                    UserPreferences.setLoginStatus(true);
                    UserPreferences.setUserName("李鸿鑫");
                    UserPreferences.setId("201411110");
                    //广播通知更新页面
                    sendLoginSuccessBroadCast();
                    finish();

                } else {
                    ToastUtils.showLongToast("用户名或密码错误");
                }
            }
        }, 500);
    }

    private void sendLoginSuccessBroadCast() {
        Intent intent = new Intent();
        intent.setAction("LoginActivity.login.success");
        sendBroadcast(intent);
    }
}
