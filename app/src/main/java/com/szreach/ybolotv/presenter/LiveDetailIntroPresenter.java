package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.LiveInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ZX on 2018/9/20
 */
public class LiveDetailIntroPresenter extends BasePresenter<MVPView> {

    //获取直播简介
    public void getLiveIntro(Map<String,Object> params_values, final List<LiveInfo> liveIntrData){
        if(!isViewAttach()){
            return;
        }
        Model.postData(3, Interface.getIpAddress(MyApplication.getApplication()) + Interface.URL_PREFIX_LIVE + Interface.URL_POST_LIVE_INFO, params_values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                try {
                    JSONObject object=new JSONObject(data);
                    JSONObject object1=object.getJSONObject("msgHeader");
                    if(object1.getBoolean("result")){
                        liveIntrData.clear();
                        LiveInfo liveInfo=gson.fromJson(object.getJSONObject("data").toString(),LiveInfo.class);
                        liveIntrData.add(liveInfo);
                        getView().showData(liveIntrData);
                    }else {
                        String msg=object1.getString("errorInfo");
                        getView().showError(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRefresh(int waht, String data) {

            }

            @Override
            public void onError(String msg) {
                getView().showError(msg);
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }
}
