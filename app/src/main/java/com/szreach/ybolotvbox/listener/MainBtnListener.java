package com.szreach.ybolotvbox.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.activities.LiveListActivity;
import com.szreach.ybolotvbox.activities.NewsListActivity;
import com.szreach.ybolotvbox.activities.VodHisListActivity;
import com.szreach.ybolotvbox.activities.VodListActivity;
import com.szreach.ybolotvbox.utils.Constant;

import java.util.Map;


/**
 * Created by Adams.Tsui on 2017/7/25.
 */

public class MainBtnListener implements View.OnKeyListener {
    private Activity act;
    private Intent intent;
    Map<Integer, LinearLayout> btnMap;

    public MainBtnListener(Activity act, Map<Integer, LinearLayout> btnMap) {
        this.act = act;
        this.btnMap = btnMap;
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == Constant.OK_BTN_KEYCODE && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            if (view == this.btnMap.get(R.id.live)) {
                // 直播
                intent = new Intent(act, LiveListActivity.class);
            } else if (view == this.btnMap.get(R.id.vod)) {
                // 视频
                intent = new Intent(act, VodListActivity.class);
            } else if (view == this.btnMap.get(R.id.news)) {
                // 新闻
                intent = new Intent(act, NewsListActivity.class);

            } else if (view == this.btnMap.get(R.id.history)) {
                // 浏览历史
                intent = new Intent(act, VodHisListActivity.class);
            } else if (view == this.btnMap.get(R.id.settings)) {
                // 系统设置

            } else if (view == this.btnMap.get(R.id.network)) {
                // 网络设置

            } else if (view == this.btnMap.get(R.id.upgrade)) {
                // 系统升级
            }
            if (intent != null) {
                act.startActivity(intent);
            }
        }
        return false;
    }
}
