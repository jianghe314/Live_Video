package com.szreach.ybolotv.utils;

import android.content.Context;
import android.widget.Toast;

public class mToast  {
    private static Toast toast;
    public static void setToastShort(Context context, String msg){
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
