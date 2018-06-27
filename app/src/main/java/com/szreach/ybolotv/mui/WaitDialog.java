package com.szreach.ybolotv.mui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import ybolo.szreach.com.live_vod.R;


/**
 * Created by zx on 2017/11/21.
 */

public class WaitDialog extends Dialog {
    private TextView tv;
    public WaitDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.wait_dialog_layout);
        initView();
    }

    private void initView() {
        tv= (TextView) findViewById(R.id.wait_dialog_tv);
    }

    public void setWatiContent(String msg){
        //tv的内容默认为正在加载
        tv.setText(msg);
    }
}
