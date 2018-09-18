package com.szreach.ybolotv.utils;

import android.content.Context;
import android.widget.Toast;

import com.szreach.ybolotv.MyApplication;

/**
 * Created by ZX on 2018/9/18
 */
public class ShowToast {
    private static Toast toast;
    public static void setToastShort(String msg){
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getApplication(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
