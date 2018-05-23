/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.utils.Constant;
import com.szreach.ybolotv.utils.StoreObjectUtils;

public class PlatformActivity extends Activity implements View.OnClickListener,ViewTreeObserver.OnGlobalFocusChangeListener,View.OnKeyListener {

    private EditText yboloIp,meetIp,meetNum,meetName,meetPsd;
    private TextView yboloSave,meetSave;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.platform_activity);
        ViewTreeObserver viewTreeObserver=getWindow().getDecorView().getViewTreeObserver();
        viewTreeObserver.addOnGlobalFocusChangeListener(this);
        initView();
        initData();

    }

    private void initView() {
        yboloIp=findViewById(R.id.platform_address);
        meetIp=findViewById(R.id.meet_ip);
        meetNum=findViewById(R.id.meet_num);
        meetName=findViewById(R.id.meet_userName);
        meetPsd=findViewById(R.id.meet_userPsd);
        yboloSave=findViewById(R.id.platform_save);
        meetSave=findViewById(R.id.meet_save);
        yboloSave.setOnClickListener(this);
        meetSave.setOnClickListener(this);
        yboloSave.setOnKeyListener(this);
        meetSave.setOnKeyListener(this);

    }

    private void initData() {
        StoreObjectUtils storeObjectUtils = new StoreObjectUtils(PlatformActivity.this, StoreObjectUtils.SP_Plat);
        String platformAddr = storeObjectUtils.getString(StoreObjectUtils.DATA_Plat_Address);
        if(platformAddr != null && platformAddr.length() > 0) {
            yboloIp.setText(platformAddr);
        }
        if(!getMeetData().equals("")){
            String[] strings=getMeetData().split("#");
            if(strings.length>0){
                meetIp.setText(strings[0]);
                meetNum.setText(strings[1]);
                if(strings[2].equals(" ")){
                    meetName.setText("");
                }else {
                    meetName.setText(strings[2]);
                }
                if(strings[3].equals(" ")){
                    meetPsd.setText("");
                }else {
                    meetPsd.setText(strings[3]);
                }
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.platform_save:
                handleEvent();
                break;
            case R.id.meet_save:
                SaveMeetData();
                break;
        }
    }


    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        if(oldFocus!=null){
            oldFocus.setBackgroundResource(R.drawable.move_no_focus);
        }
        if(newFocus!=null){
            newFocus.setBackgroundResource(R.drawable.move_focus);
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()){
            case R.id.platform_save:
                if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_UP) {
                handleEvent();
            }
                break;
            case R.id.meet_save:
                if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_UP) {
                    SaveMeetData();
                }
                break;
        }
        return false;
    }

    private void SaveMeetData() {
        if(!meetIp.getText().toString().trim().equals("")){
            if(!meetNum.getText().toString().trim().equals("")){
               StringBuilder stringBuilder=new StringBuilder();
               stringBuilder.append(meetIp.getText().toString().trim());
               stringBuilder.append("#");
               stringBuilder.append(meetNum.getText().toString().trim());
               stringBuilder.append("#");
               if(meetName.getText().toString().trim().equals("")){
                   stringBuilder.append(" ");
                   stringBuilder.append("#");
               }else {
                   stringBuilder.append(meetName.getText().toString().trim());
                   stringBuilder.append("#");
               }
               if(meetPsd.getText().toString().trim().equals("")){
                   stringBuilder.append(" ");
               }else {
                   stringBuilder.append(meetPsd.getText().toString().trim());
               }
               StoreMeetData("meet_plat",stringBuilder.toString());
            }else {
                Toast.makeText(getApplicationContext(),"会议号不能为空",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"视频会议地址不能为空",Toast.LENGTH_SHORT).show();
        }
    }


    private void handleEvent() {
        String platAddressStr = yboloIp.getText().toString().trim();
        if(yboloIp.getText().toString().equals("")||!yboloIp.getText().toString().startsWith("http://")) {
            new AlertDialog.Builder(PlatformActivity.this).setTitle("警告")
                    .setMessage("请输入正确的网络地址~")
                    .setPositiveButton("确定", null)
                    .create().show();
        } else {
            StoreObjectUtils sou = new StoreObjectUtils(PlatformActivity.this, StoreObjectUtils.SP_Plat);
            sou.saveObject(StoreObjectUtils.DATA_Plat_Address, platAddressStr);
            Constant.DataServerAdress = platAddressStr;
            new AlertDialog.Builder(PlatformActivity.this).setTitle("提示")
                    .setMessage("保存成功")
                    .setPositiveButton("确定", null)
                    .create().show();
        }
    }


    private void StoreMeetData(String TAG,String data){
        SharedPreferences sharedPreferences=getSharedPreferences("meet_plat",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("meet_plat",data);
        editor.apply();
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
    }

    private String getMeetData(){
        SharedPreferences sharedPreferences=getSharedPreferences("meet_plat",MODE_PRIVATE);
        String data=sharedPreferences.getString("meet_plat","");
        return data;
    }



}
