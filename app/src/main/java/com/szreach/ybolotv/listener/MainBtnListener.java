package com.szreach.ybolotv.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.activities.LiveListActivity;
import com.szreach.ybolotv.activities.NewsListActivity;
import com.szreach.ybolotv.activities.PlatformActivity;
import com.szreach.ybolotv.activities.UpgradeActivity;
import com.szreach.ybolotv.activities.VodHisListActivity;
import com.szreach.ybolotv.activities.VodListActivity;
import com.szreach.ybolotv.utils.StoreObjectUtils;

import java.util.Map;


/**
 * Created by Adams.Tsui on 2017/7/25.
 */

public class MainBtnListener implements View.OnKeyListener, View.OnClickListener {
    private Activity act;
    private Intent intent;
    Map<Integer, LinearLayout> btnMap;

    public MainBtnListener(Activity act, Map<Integer, LinearLayout> btnMap) {
        this.act = act;
        this.btnMap = btnMap;
    }

    @Override
    public void onClick(View v) {
        handleEvent(v);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            handleEvent(view);
        }
        return false;
    }

    private void handleEvent(View view) {
        switch (view.getId()) {
            case R.id.live:
                // 直播
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, LiveListActivity.class);
                break;

            case R.id.vod:
                // 视频
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, VodListActivity.class);
                break;

            case R.id.news:
                // 新闻
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, NewsListActivity.class);
                break;

            case R.id.history:
                // 浏览历史
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, VodHisListActivity.class);
                break;

            case R.id.settings:
                // 系统设置
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName("com.android.hisiliconsetting", "com.android.hisiliconsetting.MainActivity"));
                break;

            case R.id.network:
                // 平台地址设置
                intent = new Intent(act, PlatformActivity.class);
                break;

            case R.id.upgrade:
                // 系统升级
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, UpgradeActivity.class);
                break;

            default:
                break;
        }

        if (intent != null) {
            act.startActivity(intent);
        }
    }

    private boolean checkPlatformAddressExists() {
        StoreObjectUtils storeObjectUtils = new StoreObjectUtils(act, StoreObjectUtils.SP_Plat);
        String platformAddr = storeObjectUtils.getString(StoreObjectUtils.DATA_Plat_Address);
        if(platformAddr != null && platformAddr.length() > 0) {
            return true;
        }
        return false;
    }

    private AlertDialog createAlert() {
        return new AlertDialog.Builder(act).setTitle("警告").setMessage("请先进行平台网络设置~").setPositiveButton("确定", null).create();
    }

}
