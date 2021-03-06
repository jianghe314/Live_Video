package com.szreach.ybolotv.model;

import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.utils.NoHttpUtils;
import com.szreach.ybolotv.utils.mLog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

/**
 * Created by ZX on 2018/9/18
 */
//负责网络请求，做了网络请求工具隔离，然后要换请求框架的话，直接改写这里即可
public class Model {

    /**
     * GET请求
     * @param what
     * @param url
     */
    public static void getData(int what, String url, Map<String,Object> params_values, final CallBack<String> callBack){
        Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
        NoHttpUtils.getInstence().add(what, request, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                callBack.onLoading();
            }

            @Override
            public void onSucceed(int what, Response response) {
                mLog.e("reponse","-->"+response.get().toString());
                callBack.onSuccess(what,response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {
                callBack.onError("网络连接异常，请检查网络");
            }

            @Override
            public void onFinish(int what) {
                callBack.onComplete();
            }
        });
    }

    /**
     * POST请求
     * @param what
     * @param url
     */
    public static void postData(int what, String url, Map<String,Object> params_values, final CallBack<String> callBack){
        Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
        request.setDefineRequestBodyForJson(addParams(params_values));
        NoHttpUtils.getInstence().add(what, request, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                callBack.onLoading();
            }

            @Override
            public void onSucceed(int what, Response response) {
                mLog.e("reponse","POST-->"+response.get().toString());
                callBack.onSuccess(what,response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {
                callBack.onError("网络连接异常，请检查网络");
            }

            @Override
            public void onFinish(int what) {
                callBack.onComplete();
            }
        });
    }

    /**
     * 取消请求
     * @param what
     */
    public static void cancelRequest(int what){
        NoHttpUtils.getInstence().cancelBySign(what);
    }


    private static JSONObject addParams(Map<String,Object> params_values){
        JSONObject jsonObject=new JSONObject();
        if(params_values.size()>0){
            for (String key:params_values.keySet()) {
                try {
                    jsonObject.put(key,params_values.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        mLog.e("参数","-->"+jsonObject.toString());
        return jsonObject;
    }

}
