package com.szreach.ybolotv.utils;

import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.szreach.ybolotv.App;



public class mLog {
    private static boolean isLog(){
        ApplicationInfo applicationInfo= App.getApplication().getApplicationInfo();
        return (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
    }
    public static void e(String tag, String msg){
        if(isLog()){
            Log.e(tag,msg);
        }
    }
}
