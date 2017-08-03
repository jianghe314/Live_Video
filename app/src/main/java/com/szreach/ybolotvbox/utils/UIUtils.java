package com.szreach.ybolotvbox.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Adams.Tsui on 2017/7/24 0024.
 */

public class UIUtils {

    public static ProgressDialog createDialog(Context context) {
        ProgressDialog pDialog = ProgressDialog.show(context, "", "加载中...0%");
        pDialog.dismiss();
        return pDialog;
    }
}
