package com.szreach.ybolotv.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.szreach.ybolotv.App;
import com.szreach.ybolotv.db.DaoSession;
import com.szreach.ybolotv.mui.ErrorDialog;
import com.szreach.ybolotv.mui.WaitDialog;
import com.szreach.ybolotv.utils.NoHttpUtils;
import com.szreach.ybolotv.utils.mLog;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class BaseActivity extends AppCompatActivity {

    private Object cancelObject=new Object();
    protected Gson gson=new Gson();
    private JSONObject jsonObject=new JSONObject();
    protected List<String> params=new ArrayList<>();
    protected List<Object> objects=new ArrayList<>();
    protected ErrorDialog errorDialog;
    protected WaitDialog waitDialog;
    protected App myApp;
    protected DaoSession daoSession;
    protected String Msg="";
    protected Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            HandlerMsg(msg.what);
        }
    };

    protected void HandlerMsg(int msg){
        switch (msg){
            case 00:
                waitDialog.show();
                waitDialog.setWatiContent("加载中");
                break;
            case 11:
                waitDialog.dismiss();
                break;
            case 88:
                //错误提示
                waitDialog.dismiss();
                errorDialog.show();
                errorDialog.setTextMsg(Msg);
                break;
            case 99:
                //网络故障
                waitDialog.dismiss();
                errorDialog.show();
                errorDialog.setTextMsg("网络连接不上，请检查网络连接");
                break;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp= (App) getApplication();
        daoSession=myApp.getDaoSession();
        errorDialog=new ErrorDialog(this);
        waitDialog=new WaitDialog(this);

    }


    protected  <T> void SendRequest(int what, Request<T> request, OnResponseListener<T> listener){
        request.setCancelSign(cancelObject);
        NoHttpUtils.getInstence().add(what,request,listener);
    }


    protected JSONObject addParams(List<String> params, List<Object> values){
        if(params.size()>0){
            for (int i = 0; i < params.size(); i++) {
                try {
                    jsonObject.put(params.get(i),values.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        mLog.e("参数","-->"+jsonObject.toString());
        return jsonObject;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(event.getAction()== MotionEvent.ACTION_DOWN){
            if(this.getCurrentFocus()!=null){
                if(this.getCurrentFocus().getWindowToken()!=null){
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onBackPressed() {
        NoHttpUtils.getInstence().cancelBySign(cancelObject);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        NoHttpUtils.getInstence().cancelBySign(cancelObject);
        super.onDestroy();
    }
}
