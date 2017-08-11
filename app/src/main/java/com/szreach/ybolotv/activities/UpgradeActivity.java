/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.ApkVersion;
import com.szreach.ybolotv.utils.Constant;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.utils.UpgradeUtils;

public class UpgradeActivity extends Activity {
    private TextView upgradeCheck;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.upgrade_activity);

        upgradeCheck = findViewById(R.id.upgrade_check);

        upgradeCheck.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == Constant.OK_BTN_KEYCODE && keyEvent.getAction() == KeyEvent.ACTION_UP) {
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
                int versionCode = UpgradeActivity.this.getPackageManager().getPackageInfo("com.szreach.ybolotv", 0).versionCode;
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

}
