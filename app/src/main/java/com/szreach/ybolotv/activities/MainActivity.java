/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.NewsBean;
import com.szreach.ybolotv.beans.SysCoBean;
import com.szreach.ybolotv.listener.MainBtnListener;
import com.szreach.ybolotv.service.AutoPlayLiveService;
import com.szreach.ybolotv.utils.Constant;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.utils.StoreObjectUtils;
import com.szreach.ybolotv.views.MainLinearLayout;
import com.szreach.ybolotv.views.MoveFrameLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.szreach.ybolotv.R.id.news;

public class MainActivity extends Activity {
    MainLinearLayout mLinearLayout;
    MoveFrameLayout mMainMoveFrame;
    ImageView homePageLogo;
    TextView homePageTitle;
    TextView newsTitle;
    TextView newsSummary;
    View mOldFocus;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main_activity);

        mLinearLayout = findViewById(R.id.home_page);
        mMainMoveFrame = findViewById(R.id.main_move_frame);
        homePageLogo = findViewById(R.id.app_home_page_logo);
        homePageTitle = findViewById(R.id.app_home_page_title);
        newsTitle = findViewById(R.id.main_news_title);
        newsSummary = findViewById(R.id.main_news_summary);

        initAppParams();
        initMoveFrame();
        initMainBtnEvent();
        initPageData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initWebSocket();
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
        mainBtnMap.put(news, (LinearLayout) this.findViewById(news));
        mainBtnMap.put(R.id.history, (LinearLayout) this.findViewById(R.id.history));
        mainBtnMap.put(R.id.settings, (LinearLayout) this.findViewById(R.id.settings));
        mainBtnMap.put(R.id.network, (LinearLayout) this.findViewById(R.id.network));
        mainBtnMap.put(R.id.upgrade, (LinearLayout) this.findViewById(R.id.upgrade));

        Iterator<Map.Entry<Integer, LinearLayout>> it = mainBtnMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().setOnKeyListener(new MainBtnListener(this, mainBtnMap));
        }
    }

    private void initPageData() {
        new DataThread(this.handlerNews, this.handlerCoInfo).start();
    }

    private void initWebSocket() {
        if(Constant.OpenAutoPlayLiveService) {
            Intent intent = new Intent(this, AutoPlayLiveService.class);
            startService(intent);
        }
    }

    /**
     * 处理新闻数据
     */
    private Handler handlerNews = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            NewsBean news = bundle.getParcelable("news");
            newsTitle.setText(news.getTitle());
            newsSummary.setText(news.getSummary());
        }
    };

    /**
     * 处理企业信息
     */
    private Handler handlerCoInfo = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String coName = bundle.getString("coName");
            Bitmap bitmap = bundle.getParcelable("coLogoMap");
            homePageTitle.setText(coName);
            homePageLogo.setImageBitmap(bitmap);
        }
    };

    private class DataThread extends Thread {
        private Handler handlerNews;
        private Handler handlerCoInfo;

        public DataThread(Handler handlerNews, Handler handlerCoInfo) {
            this.handlerNews = handlerNews;
            this.handlerCoInfo = handlerCoInfo;
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
                    handlerNews.sendMessage(msg);
                }

                SysCoBean sysCoBean = DataService.getInstance().getSysCoBean();
                if(sysCoBean != null) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();

                    try {
                        bundle.putString("coName", sysCoBean.getCoName());
                        URL url = new URL(Constant.DataServerAdress + "/Rec/coImg/" + sysCoBean.getCoLogo());
                        Bitmap bitMap = BitmapFactory.decodeStream(url.openStream());
                        bundle.putParcelable("coLogoMap", bitMap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    msg.setData(bundle);
                    handlerCoInfo.sendMessage(msg);
                }
            }
        }
    }

}
