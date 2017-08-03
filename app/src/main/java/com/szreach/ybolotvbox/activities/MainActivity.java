/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.listener.MainBtnListener;
import com.szreach.ybolotvbox.views.MainLinearLayout;
import com.szreach.ybolotvbox.views.MoveFrameLayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends Activity {
    MainLinearLayout mLinearLayout;
    MoveFrameLayout mMainMoveFrame;
    View mOldFocus;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main_activity);

        mLinearLayout = (MainLinearLayout) findViewById(R.id.home_page);
        mMainMoveFrame = (MoveFrameLayout) findViewById(R.id.main_move_frame);
        initMoveFrame();
        initMainBtnEvent();
    }

    private void initMoveFrame() {
        mMainMoveFrame.setUpRectResource(R.drawable.conner_main);
        mMainMoveFrame.setTranDurAnimTime(400);
        mLinearLayout.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {

                if (newFocus != null) {
                    mMainMoveFrame.setDrawUpRectEnabled(true);
                    float scale = 1.015f;
                    mMainMoveFrame.setFocusView(newFocus, mOldFocus, scale);
                    mMainMoveFrame.bringToFront();
                    mOldFocus = newFocus;
                }
            }
        });
    }

    private void initMainBtnEvent() {
        Map<Integer, LinearLayout> mainBtnMap = new HashMap<Integer, LinearLayout>();

        mainBtnMap.put(R.id.live, (LinearLayout) this.findViewById(R.id.live));
        mainBtnMap.put(R.id.vod, (LinearLayout) this.findViewById(R.id.vod));
        mainBtnMap.put(R.id.news, (LinearLayout) this.findViewById(R.id.news));
        mainBtnMap.put(R.id.history, (LinearLayout) this.findViewById(R.id.history));
        mainBtnMap.put(R.id.settings, (LinearLayout) this.findViewById(R.id.settings));
        mainBtnMap.put(R.id.network, (LinearLayout) this.findViewById(R.id.network));
        mainBtnMap.put(R.id.upgrade, (LinearLayout) this.findViewById(R.id.upgrade));

        Iterator<Map.Entry<Integer, LinearLayout>> it = mainBtnMap.entrySet().iterator();
        while(it.hasNext()) {
            it.next().getValue().setOnKeyListener(new MainBtnListener(this, mainBtnMap));
        }
    }

}
