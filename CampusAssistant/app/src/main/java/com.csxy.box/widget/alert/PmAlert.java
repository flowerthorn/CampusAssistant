package com.csxy.box.widget.alert;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.csxy.box.R;
import com.lib.mylibrary.utils.ViewUtils;


/**
 * Created by wangkang on 17/5/23.
 */

public class PmAlert {


    public static final int DEFAULT_THEME = R.style.AlertDialog;

    public static Dialog getAlert(Context context, View view, int theme) {
        CustomDialog dialog = new CustomDialog(context, theme, view);
        return dialog;
    }


    public static Dialog getAlert(Context context, View view) {
        return getAlert(context,view,DEFAULT_THEME);
    }


    public static Dialog getOneButtonAlert(Context context, Object title, Object content, Object buttonText, boolean showCancelIcon , OnAlertClickListener1 listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context, DEFAULT_THEME);
        CustomDialog dialog = builder.setTitle(title).setContent(content).
                setShowPositiveButton(true).
                setShowNegativeButton(false).
                setShowCancelIcon(showCancelIcon).
                setPositiveButtonText(buttonText).
                setOnAlertClickListener1(listener).
                build();
        return dialog;
    }

    public static Dialog getTwoButtonAlert(Context context, Object title, Object content, Object positiveText, Object negativeText, boolean showCancelIcon, OnAlertClickListener2 listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context, DEFAULT_THEME);
        CustomDialog dialog = builder.setTitle(title).setContent(content).
                setShowPositiveButton(true).
                setShowNegativeButton(true).
                setShowCancelIcon(showCancelIcon).
                setPositiveButtonText(positiveText).
                setNegativeButtonText(negativeText).
                setOnAlertClickListener2(listener).build();
        return dialog;
    }

    public static Dialog getTwoButtonAlert(Context context, Object title, Object content, Object positiveText, Object negativeText, boolean showCancelIcon, OnAlertClickListener2 listener , boolean newStyle) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context, DEFAULT_THEME , newStyle);
        CustomDialog dialog = builder.setTitle(title).setContent(content).
                setShowPositiveButton(true).
                setShowNegativeButton(true).
                setShowCancelIcon(showCancelIcon).
                setPositiveButtonText(positiveText).
                setNegativeButtonText(negativeText).
                setOnAlertClickListener2(listener).build();
        return dialog;
    }



    public static Dialog showAlert(Context context, View view) {
        Dialog alert = getAlert(context, view);
        alert.show();
        return alert;
    }


    public static Dialog showOnButtonAlert(Context context, Object title, Object content, Object buttonText , boolean showCancelIcon, OnAlertClickListener1 listener) {
        Dialog alert = getOneButtonAlert(context, title, content, buttonText, showCancelIcon,listener);
        alert.show();
        return alert;
    }

    public static Dialog showTwoButtonAlert(Context context, Object title, Object content, Object positiveText, Object negativeText, boolean showCancelIcon, OnAlertClickListener2 listener) {
        Dialog alert = getTwoButtonAlert(context, title, content, positiveText, negativeText, showCancelIcon,listener);
        alert.show();
        return alert;
    }

}
