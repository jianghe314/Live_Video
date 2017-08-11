package com.szreach.ybolotv.utils;

/**
 * Created by Adams.Tsui on 2017/7/24 0024.
 */

public class DeviceInfo {
    private int screenWidth;
    private int screenHeight;
    private int densityDpi;

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setDensityDpi(int densityDpi) {
        this.densityDpi = densityDpi;
    }
}
