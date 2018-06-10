package com.csxy.box.business.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.utils.ToastUtils;

/**
 * Created by lihongxin on 2018/6/11.
 * 问题反馈
 */
public class FeedBackActivity extends BaseActivity {
    private TextView tvSubmit;
    private EditText etInput;
    private TextView tvLengthInfo;
    private EditText et_feed_contact_way;
    private TextView tvTitle;
    private ImageView ivBack;

    public static void actionShow(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        tvTitle = findViewById(R.id.tv_title);
        ivBack=findViewById(R.id.iv_backTb);
        tvSubmit = findViewById(R.id.tv_submit);
        etInput = findViewById(R.id.et_input);
        tvLengthInfo = findViewById(R.id.tv_LengthInfo);
        et_feed_contact_way = findViewById(R.id.et_feed_contact_way);
        tvSubmit.setEnabled(false);
        tvTitle.setText("反馈");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if (length == 0) {
                    tvSubmit.setEnabled(false);
                } else {
                    tvSubmit.setEnabled(true);
                }

                tvLengthInfo.setText(length + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_feed_contact_way.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvSubmit.setBackgroundResource(R.drawable.feedback_shape_selector);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etInput.getText().toString();
                String text_contact = et_feed_contact_way.getText().toString();
                if (text.length() > 200) {
                    ToastUtils.showShortToast(R.string.feed_most_limit);
                } else if (text.length() < 10) {
                    ToastUtils.showShortToast(R.string.feed_least_limit);
                } else {
                    // TODO: 2018/6/11 反馈接口
                    finish();
                    ToastUtils.showLongToast("提交成功");

                }

            }

        });

    }
}