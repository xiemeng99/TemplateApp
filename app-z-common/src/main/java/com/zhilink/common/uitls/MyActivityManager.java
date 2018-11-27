package com.zhilink.common.uitls;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * 设置和获取当前显示activity
 *
 * @author xiemeng
 * @date 2018-10-24 20:06
 */

public class MyActivityManager {
    private static MyActivityManager sInstance = new MyActivityManager();
    private WeakReference<Activity> sCurrentActivityWeakRef;

    private MyActivityManager() {
    }

    public static MyActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }
}
