package com.csxy.box.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csxy.box.R;
import com.csxy.box.app.MyApplication;


public class ToastUtils {

    public static Toast toast = null;
    public static Toast imageTextToast = null;

    public static void showShortToast(Object info) {
        String showContent;
        Context context = MyApplication.getInstance();
        if (info instanceof String) {
            showContent = (String) info;
        } else {
            showContent = context.getString((Integer) info);
        }
        showToastShort(showContent);
    }

    public static void showLongToast(Object info) {
        String showContent;
        Context context = MyApplication.getInstance();
        if (info instanceof String) {
            showContent = (String) info;
        } else {
            showContent = context.getString((Integer) info);
        }
        showToastLong(showContent);
    }

    public static void showShortImageToast(Object info, int imageResId) {
        String showContent;
        Context context = MyApplication.getInstance();
        if (info instanceof String) {
            showContent = (String) info;
        } else {
            showContent = context.getString((Integer) info);
        }
        showToastImageText(showContent, imageResId, Toast.LENGTH_SHORT);
    }

    public static void showLongImageToast(Object info, int imageResId) {
        String showContent;
        Context context = MyApplication.getInstance();
        if (info instanceof String) {
            showContent = (String) info;
        } else {
            showContent = context.getString((Integer) info);
        }
        showToastImageText(showContent, imageResId, Toast.LENGTH_LONG);
    }


    private static void showToastShort(String infoString) {
        Context context = MyApplication.getInstance();
        if (context == null) {
            return;
        }
        showToastText(infoString, Toast.LENGTH_SHORT);
    }

    private static void showToastLong(String infoString) {
        Context context = MyApplication.getInstance();
        if (context == null) {
            return;
        }
        showToastText(infoString, Toast.LENGTH_LONG);
    }

    public static void cancel() {
        if (toast == null) {
            return;
        }
        toast.cancel();
    }

    private static Toast getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context.getApplicationContext());
            LayoutInflater inflate = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.toast_main, null);
            toast.setView(v);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }

    private static Toast getImageTextToast(Context context) {
        if (imageTextToast == null) {
            imageTextToast = new Toast(context.getApplicationContext());
            imageTextToast.setGravity(Gravity.CENTER, 0, 0);
            LayoutInflater inflate = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.toast_image_text, null);
            imageTextToast.setView(v);
        }
        imageTextToast.setDuration(Toast.LENGTH_SHORT);
        return imageTextToast;
    }

    private static void showToastText(String infoStr, int TOAST_LENGTH) {
        Context context = MyApplication.getInstance();
        Toast toast = getToast(context);
        TextView textView = (TextView) toast.getView().findViewById(R.id.tv_toast);
        if (textView != null) {
            textView.setText(infoStr);
            textView.setTypeface(Typeface.SANS_SERIF);
        }
        toast.setDuration(TOAST_LENGTH);
        toast.show();
    }

    private static void showToastImageText(String infoStr, int imageResId, int TOAST_LENGTH) {
        Context context = MyApplication.getInstance();
        Toast toast = getImageTextToast(context);
        TextView textView = (TextView) toast.getView().findViewById(R.id.tv_toast);
        ImageView imageView = (ImageView) toast.getView().findViewById(R.id.img_toast);
        if (textView != null) {
            textView.setText(infoStr);
            textView.setTypeface(Typeface.SANS_SERIF);
        }
        if (imageView != null) {
            imageView.setImageResource(imageResId);
        }
        toast.setDuration(TOAST_LENGTH);
        toast.show();
    }


    public static void showCustomBgToast(Context context, String content, int drawableResId) {
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toastView.setBackgroundResource(drawableResId);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showTwoLineToast(Context context, String content1, String content2) {
        View toastView = View.inflate(context, R.layout.toast_twoline, null);
        ((TextView) toastView.findViewById(R.id.line1)).setText(content1);
        ((TextView) toastView.findViewById(R.id.line2)).setText(content2);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
