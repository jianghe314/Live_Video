/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.ApkVersion;
import com.szreach.ybolotv.utils.Constant;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.utils.UpgradeUtils;
import com.szreach.ybolotv.views.MoveFrameLayout;

public class UpgradeActivity extends Activity {
    private LinearLayout mLinearLayout;
    private LinearLayout upgradeCheck;
    private LinearLayout upgradeFile;
    private MoveFrameLayout mMainMoveFrame;
    private View mOldFocus;
    private TextView upgradeCurrVersion;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.upgrade_activity);

        mLinearLayout = findViewById(R.id.upgrade_page);
        mMainMoveFrame = findViewById(R.id.upgrade_move_frame);
        upgradeFile = findViewById(R.id.upgrade_file);
        upgradeCheck = findViewById(R.id.upgrade_check);
        upgradeCurrVersion = findViewById(R.id.upgrade_curr_version);

        try {
            String versionName = UpgradeActivity.this.getPackageManager().getPackageInfo(UpgradeActivity.this.getPackageName(), 0).versionName;
            upgradeCurrVersion.setText("当前版本：" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        initMoveFrame();

        upgradeFile.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    // 打开文件管理器
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName("com.hitv.explorer", "com.hitv.explorer.activity.MainExplorerActivity");
                    intent.setComponent(cn);
                    UpgradeActivity.this.startActivity(intent);

                    // 高级设置
                    /*Intent intent = new Intent("android.settings.SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UpgradeActivity.this.startActivity(intent);*/

                    // 以太网
                    /*Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName("com.android.hisiliconsetting", "com.android.hisiliconsetting.net.EthernetActivity");
                    intent.setComponent(cn);
                    UpgradeActivity.this.startActivity(intent);*/

                    // 无线网
                    /*Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName("com.android.hisiliconsetting", "com.android.hisiliconsetting.net.WifiActivity");
                    intent.setComponent(cn);
                    UpgradeActivity.this.startActivity(intent);*/

                }
                return false;
            }
        });

        upgradeCheck.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                      new UpgradeThread(UpgradeActivity.this.upgradeHandler).start();
                }
                return false;
            }
        });
    }

    private Handler upgradeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            final ApkVersion apkVersion = bundle.getParcelable("apkVersion");
            try {
                int versionCode = UpgradeActivity.this.getPackageManager().getPackageInfo(UpgradeActivity.this.getPackageName(), 0).versionCode;
                if(apkVersion != null && apkVersion.getVersionCode() > versionCode) {
                    new AlertDialog.Builder(UpgradeActivity.this)
                    .setTitle("应用更新")
                    .setMessage("检测到新版本，立即更新吗？")
                    .setPositiveButton("更   新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new UpgradeUtils(UpgradeActivity.this, apkVersion).upgradeVersion();
                        }
                    })
                    .setNegativeButton("稍后更新", null)
                    .create().show();
                } else {
                    new AlertDialog.Builder(UpgradeActivity.this)
                    .setTitle("应用更新")
                    .setMessage("当前应用是最新版本~")
                    .setPositiveButton("确   定", null)
                    .create().show();
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    };

    private class UpgradeThread extends Thread {
        private Handler upgradeHandler;

        public UpgradeThread(Handler upgradeHandler) {
            this.upgradeHandler = upgradeHandler;
        }

        @Override
        public void run() {
            ApkVersion apkVersion = DataService.getInstance().getApkVersionInfo();
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("apkVersion", apkVersion);
            msg.setData(bundle);
            this.upgradeHandler.sendMessage(msg);
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

}
