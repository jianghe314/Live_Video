package com.szreach.ybolotv.utils;

import android.view.View;

/**
 * Created by Adams.Tsui on 2017/07/27.
 */

public class ScaleTool {


    private View view;
    private int width;
    private int height;

    public ScaleTool(View view) {
        this.view = view;
    }

    public int getWidth() {
        return view.getLayoutParams().width;
    }

    public void setWidth(int width) {
        this.width = width;
        view.getLayoutParams().width = width;
        view.requestLayout();  //重新刷新位置
    }

    public int getHeight() {
        return view.getLayoutParams().height;

    }

    public void setHeight(int height) {
        this.height = height;
        view.getLayoutParams().height = height;
        view.requestLayout();
    }

}
