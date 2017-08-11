package com.szreach.ybolotv.utils;

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

    public static ProgressDialog createDownloadDialog(Context context) {
        ProgressDialog pDialog = ProgressDialog.show(context, "正在更新", "进度...0%");
        pDialog.dismiss();
        return pDialog;
    }
}
