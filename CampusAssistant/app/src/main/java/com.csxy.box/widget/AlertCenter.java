package com.csxy.box.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.csxy.box.R;

/**
 * Created by lihongxin on 2018/6/10.
 */
public class AlertCenter {

    //拨打电话的弹窗
    public static void showTelPopWindow(final Context mContext, String telephone) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_call, null);
        final PopupWindow popupWindow = new PopupWindow(root);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(root);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        TextView tvCall1 = root.findViewById(R.id.tv_call1);
        TextView tvCall2 = root.findViewById(R.id.tv_call2);
        TextView tvCancel = root.findViewById(R.id.tv_cancel);
        final String call1 = telephone.substring(0, 11);
        tvCall1.setText(call1);
        tvCall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + call1);
                intent.setData(data);
                mContext.startActivity(intent);
            }
        });
        if (telephone.length() > 11) {
            //两个电话
            final String call2 = telephone.substring(12, 23);
            tvCall2.setText(call2);
            tvCall2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + call2);
                    intent.setData(data);
                    mContext.startActivity(intent);
                }
            });
        } else {

            tvCall2.setVisibility(View.GONE);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
