package com.szreach.ybolotv.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.szreach.ybolotv.widgets.ErrorDialog;
import com.szreach.ybolotv.widgets.WaitDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    private WaitDialog waitDialog;
    private ErrorDialog errorDialog;
    protected Map<String,Object> parama_values=new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        waitDialog=new WaitDialog(this);
        errorDialog=new ErrorDialog(this);
    }

    @Override
    public void showLoading() {
        waitDialog.show();
        waitDialog.setWatiContent("加载中");
    }

    @Override
    public void hideLoading() {
        waitDialog.dismiss();
    }

    @Override
    public void showError(String msg) {
        waitDialog.dismiss();
        errorDialog.show();
        errorDialog.setTextMsg(msg);
    }


    @Override
    public void showData(Object data) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onRefresh(Object data) {

    }

    @Override
    public Context getContent() {
        return BaseActivity.this;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(this.getCurrentFocus()!=null){
                if(this.getCurrentFocus().getWindowToken()!=null){
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
