package com.szreach.ybolotvbox.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by Adams Tsui on 2017/7/24 0024.
 */

public class DeviceUtils extends Activity {

    public static DeviceInfo getDeviceInfo(WindowManager manager) {
        DeviceInfo deviceInfo = new DeviceInfo();

        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        deviceInfo.setScreenWidth(metrics.widthPixels);
        deviceInfo.setScreenHeight(metrics.heightPixels);
        deviceInfo.setDensityDpi(metrics.densityDpi);

        return deviceInfo;
    }

    public static String printSysInfo(WindowManager manager) {
        DisplayMetrics metric = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        int width = metric.widthPixels;
        // 屏幕高度（像素）
        int height = metric.heightPixels;
        // 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
        // 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL + ",\nSDK版本:"
                + android.os.Build.VERSION.SDK + ",\n系统版本:"
                + android.os.Build.VERSION.RELEASE + "\n屏幕宽度（像素）: " + width + "\n屏幕高度（像素）: " + height + "\n屏幕密度比例:  " + density + "\n屏幕密度DPI: " + densityDpi;

        return info;
    }

}