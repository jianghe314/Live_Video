package com.szreach.ybolotv.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.activities.LiveListActivity;
import com.szreach.ybolotv.activities.NewsListActivity;
import com.szreach.ybolotv.activities.PlatformActivity;
import com.szreach.ybolotv.activities.UpgradeActivity;
import com.szreach.ybolotv.activities.VodHisListActivity;
import com.szreach.ybolotv.activities.VodListActivity;
import com.szreach.ybolotv.utils.StoreObjectUtils;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Adams.Tsui on 2017/7/25.
 */

public class MainBtnListener implements View.OnKeyListener, View.OnClickListener {
    private Activity act;
    private Intent intent;
    Map<Integer, LinearLayout> btnMap;
    private  EditText edit;

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
                act.startActivity(intent);
                break;

            case R.id.vod:
                // 视频
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, VodListActivity.class);
                act.startActivity(intent);
                break;

            case R.id.news:
                // 新闻
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, NewsListActivity.class);
                act.startActivity(intent);
                break;

            case R.id.history:
                // 浏览历史
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, VodHisListActivity.class);
                act.startActivity(intent);
                break;

            case R.id.settings:
                // 系统设置
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName("com.android.hisiliconsetting", "com.android.hisiliconsetting.MainActivity"));
                act.startActivity(intent);
                break;

            case R.id.network:
                // 平台地址设置
                intent = new Intent(act, PlatformActivity.class);
                act.startActivity(intent);
                break;

            case R.id.upgrade:
                // 系统升级
                if(!checkPlatformAddressExists()) {
                    createAlert().show();
                    return;
                }
                intent = new Intent(act, UpgradeActivity.class);
                act.startActivity(intent);
                break;
            case R.id.camera:
                //视频会议
                if(!getMeetData().equals("")){
                    String[] strings=getMeetData().split("#");
                    if(strings.length>0){
                        intent=new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri uri=Uri.parse("conf://net.viazijing.cloud/join?host="+ strings[0]+"&mrnum="+strings[1]+"&mrpin="+(strings[3].equals(" ")?"":strings[3]));
                        intent.setData(uri);
                        act.startActivity(intent);
                    }
                }else {
                    Toast.makeText(act.getApplicationContext(), "请先到平台网络设置视频会议地址", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        /*
        if (intent != null) {
            act.startActivity(intent);
        }
        */
    }


    private String getMeetData(){
        SharedPreferences sharedPreferences=act.getSharedPreferences("meet_plat",MODE_PRIVATE);
        String data=sharedPreferences.getString("meet_plat","");
        return data;
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
