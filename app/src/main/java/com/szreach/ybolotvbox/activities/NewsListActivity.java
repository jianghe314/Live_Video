/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.flexbox.FlexboxLayout;
import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.NewsBean;
import com.szreach.ybolotvbox.utils.Constant;
import com.szreach.ybolotvbox.views.MoveFrameLayout;
import com.szreach.ybolotvbox.views.NewsConItemView;
import com.szreach.ybolotvbox.views.NewsItemView;

import java.util.ArrayList;
import java.util.List;

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

        loadData();

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
    }

    private void loadData() {
        List<NewsBean> newsBeanList = new ArrayList<NewsBean>();

        int count = 0;
        for(int i = 0; i < 20; i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.setNewsId("xxxxxxxx");
            newsBean.setNewsDatetime("2017-07-31");
            newsBean.setNewsTitle("中国邮政科技秀闪亮渝洽会秀闪亮会秀闪亮会秀闪亮渝洽会" + (i + 1));
            newsBeanList.add(newsBean);
            if(count == 0) {
                newsBean.setNewsUrl("http://www.szreach.com/news/mtbd/320.html");
                count++;
            } else if(count == 1) {
                newsBean.setNewsUrl("http://www.szreach.com/news/gsxw/270.html");
                count++;
            } else {
                newsBean.setNewsUrl("http://www.szreach.com/news/mtbd/240.html");
                count = 0;
            }
        }

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
                        if(keyCode == Constant.OK_BTN_KEYCODE && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            NewsItemView target = (NewsItemView) view;
                            Intent intent = new Intent(target.getAct(), NewsPlayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("news", target.getNews());
                            intent.putExtras(bundle);
                            target.getAct().startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        }
    }

}
