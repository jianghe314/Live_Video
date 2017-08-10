/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.NewsBean;
import com.szreach.ybolotvbox.listener.MainBtnListener;
import com.szreach.ybolotvbox.utils.Constant;
import com.szreach.ybolotvbox.utils.DataService;
import com.szreach.ybolotvbox.utils.StoreObjectUtils;
import com.szreach.ybolotvbox.views.MainLinearLayout;
import com.szreach.ybolotvbox.views.MoveFrameLayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends Activity {
    MainLinearLayout mLinearLayout;
    MoveFrameLayout mMainMoveFrame;
    TextView newsTitle;
    TextView newsSummary;
    View mOldFocus;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main_activity);

        mLinearLayout = findViewById(R.id.home_page);
        mMainMoveFrame = findViewById(R.id.main_move_frame);
        newsTitle = findViewById(R.id.main_news_title);
        newsSummary = findViewById(R.id.main_news_summary);

        initAppParams();
        initMoveFrame();
        initMainBtnEvent();
        new DataThread(this.handler).start();
    }

    /**
     * 初始化应用数据
     */
    private void initAppParams() {
        StoreObjectUtils storeObjectUtils = new StoreObjectUtils(MainActivity.this, StoreObjectUtils.SP_Plat);
        String platformAddr = storeObjectUtils.getString(StoreObjectUtils.DATA_Plat_Address);
        if (platformAddr != null && platformAddr.length() > 0) {
            Constant.DataServerAdress = platformAddr;
        }
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

    /**
     * 初始化面板按钮事件
     */
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
        while (it.hasNext()) {
            it.next().getValue().setOnKeyListener(new MainBtnListener(this, mainBtnMap));
        }
    }

    /**
     * 异步网络请求数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            NewsBean news = bundle.getParcelable("news");
            newsTitle.setText(news.getTitle());
            newsSummary.setText(news.getSummary());
        }
    };

    private class DataThread extends Thread {
        private Handler handler;

        public DataThread(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            if (Constant.DataServerAdress != null && Constant.DataServerAdress.length() > 0) {
                NewsBean news = DataService.getInstance().getMainNews();
                if (news != null) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("news", news);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        }
    }

}
