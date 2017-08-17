/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.flexbox.FlexboxLayout;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.NewsBean;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.views.MoveFrameLayout;
import com.szreach.ybolotv.views.NewsConItemView;
import com.szreach.ybolotv.views.NewsItemView;

import java.util.ArrayList;

public class NewsListActivity extends Activity {
    private MoveFrameLayout mMainMoveFrame;
    private FlexboxLayout newsListCenterLayout;
    private View currentNewsItemView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.news_list_activity);

        mMainMoveFrame = (MoveFrameLayout) findViewById(R.id.news_list_page_move);
        mMainMoveFrame.setUpRectResource(R.drawable.conner_news);
        mMainMoveFrame.setTranDurAnimTime(200);

        newsListCenterLayout = (FlexboxLayout) findViewById(R.id.news_list_center_list);
        newsListCenterLayout.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if(newFocus instanceof NewsItemView) {
                    mMainMoveFrame.setDrawUpRectEnabled(true);
                    float scale = 1.015f;
                    mMainMoveFrame.setFocusView(newFocus, currentNewsItemView, scale);
                    mMainMoveFrame.bringToFront();
                    currentNewsItemView = newFocus;
                }
            }
        });

        new DataThread().start();
    }

    private void initData(ArrayList<NewsBean> newsBeanList) {
        if(newsBeanList != null && newsBeanList.size() > 0) {
            for(int i = 0; i < newsBeanList.size(); i++) {
                NewsBean newsBean = newsBeanList.get(i);
                NewsItemView newsItemView = new NewsItemView(this, newsBean);
                NewsConItemView newsConItemView = new NewsConItemView(this, newsItemView, newsBean);
                newsListCenterLayout.addView(newsConItemView);

                // 初始化按键事件
                newsItemView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            NewsItemView target = (NewsItemView) view;
                            Intent intent = new Intent(target.getAct(), NewsPlayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("coId", target.getNews().getCoId());
                            bundle.putSerializable("nnId", target.getNews().getNnId());
                            intent.putExtras(bundle);
                            target.getAct().startActivity(intent);
                        }
                        return false;
                    }
                });
            }

            // 视图全部添加后，才让第一个元素获取焦点
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    View view = newsListCenterLayout.getChildAt(0);
                    if(view != null) {
                        view.requestFocus();
                    }
                }
            }, 300);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            ArrayList<NewsBean> newsList = bundle.getParcelableArrayList("newsList");
            initData(newsList);
        }
    };

    private class DataThread extends Thread {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("newsList", DataService.getInstance().getNewsList());
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }
}
