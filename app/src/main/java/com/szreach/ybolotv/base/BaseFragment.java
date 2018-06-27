package com.szreach.ybolotv.base;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ybolo.szreach.com.live_vod.App;
import ybolo.szreach.com.live_vod.db.DaoSession;
import ybolo.szreach.com.live_vod.mui.ErrorDialog;
import ybolo.szreach.com.live_vod.mui.WaitDialog;
import ybolo.szreach.com.live_vod.utils.NoHttpUtils;
import ybolo.szreach.com.live_vod.utils.mLog;

public abstract class BaseFragment extends Fragment {

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
            HandMsg(msg.what);
        }
    };

    protected void HandMsg(int msg){
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= LayoutInflater.from(getActivity()).inflate(setContentView(),container,false);
        initView(view);
        initData();
        return view;
    }


    protected abstract int setContentView();

    protected void initView(View view) {
        myApp= (App) App.getApplication();
        daoSession=myApp.getDaoSession();
        errorDialog=new ErrorDialog(getActivity());
        waitDialog=new WaitDialog(getActivity());
    }

    protected void initData() {
    }


    protected  <T> void SendRequest(int what, Request<T> request, OnResponseListener<T> listener){
        request.setCancelSign(cancelObject);
        NoHttpUtils.getInstence().add(what,request,listener);
    }


    protected  synchronized JSONObject addParams(List<String> params, List<Object> values){
        if(params.size()>0&&values.size()>0){
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
    public void onDetach() {
        NoHttpUtils.getInstence().cancelBySign(cancelObject);
        super.onDetach();
    }
}
