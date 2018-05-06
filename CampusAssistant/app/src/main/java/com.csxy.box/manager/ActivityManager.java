package com.csxy.box.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Stack;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class ActivityManager {
    private static ActivityManager instance;
    private static Stack<Activity> activityStack = new Stack<Activity>();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public <T extends Activity> T getActivity(Class clz) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(clz)) {
                return (T) activity;
            }
        }
        return null;

    }

    public int getStackSize() {
        return activityStack.size();
    }

    public void restartApp(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        finishAllActivity();
        System.exit(0);
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }
}
