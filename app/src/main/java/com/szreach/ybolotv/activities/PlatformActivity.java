/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.utils.Constant;
import com.szreach.ybolotv.utils.StoreObjectUtils;

public class PlatformActivity extends Activity {
    private EditText platAddressText;
    private TextView platSaveBtn;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.platform_activity);

        platAddressText = findViewById(R.id.platform_address);
        this.initData();

        platSaveBtn = findViewById(R.id.platform_save);
        platSaveBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    view.setBackgroundColor(0xffff9900);
                } else {
                    view.setBackgroundColor(0xff00aaee);
                }
            }
        });

        platSaveBtn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == Constant.OK_BTN_KEYCODE && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    String platAddressStr = platAddressText.getText().toString();
                    if(platAddressStr != null && platAddressStr.length() > 0 && !platAddressStr.startsWith("http://") && !platAddressStr.startsWith("https://")) {
                        new AlertDialog.Builder(PlatformActivity.this).setTitle("警告")
                                .setMessage("请输入正确的网络地址~")
                                .setPositiveButton("确定", null)
                                .create().show();
                    } else {
                        StoreObjectUtils sou = new StoreObjectUtils(PlatformActivity.this, StoreObjectUtils.SP_Plat);
                        sou.saveObject(StoreObjectUtils.DATA_Plat_Address, platAddressStr);
                        Constant.DataServerAdress = platAddressStr;
                        new AlertDialog.Builder(PlatformActivity.this).setTitle("提示")
                                .setMessage("设置成功~")
                                .setPositiveButton("确定", null)
                                .create().show();
                    }
                }
                return false;
            }
        });
    }

    private void initData() {
        StoreObjectUtils storeObjectUtils = new StoreObjectUtils(PlatformActivity.this, StoreObjectUtils.SP_Plat);
        String platformAddr = storeObjectUtils.getString(StoreObjectUtils.DATA_Plat_Address);
        if(platformAddr != null && platformAddr.length() > 0) {
            platAddressText.setText(platformAddr);
        }
    }

}
