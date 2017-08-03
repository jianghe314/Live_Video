package com.szreach.ybolotvbox.utils;

import android.graphics.Canvas;
import android.view.View;

import com.szreach.ybolotvbox.views.MoveFrameLayout;

/**
 * Created by Adams.Tsui on 2017/07/27.
 */

public interface MoveAnimationHelper {

    void drawMoveView(Canvas canvas);

    void setFocusView(View currentView, View oldView, float scale);  //放大缩小函数

    void rectMoveAnimation(View currentView, float scaleX, float scaleY);// 边框移动函数

     MoveFrameLayout getMoveView(); //边框view

    void setMoveView(MoveFrameLayout moveView);

    void setTranDurAnimTime(int time);

    void setDrawUpRectEnabled(boolean isDrawUpRect);

}
