package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.LiveRemark;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZX on 2018/9/25
 */
public class LiveDetailRemarkPresenter extends BasePresenter<MVPView> {

    public void getRemarkData(final int mwhat, String url,List<String> params, List<Object> values, final List<LiveRemark> liveRemarkData){
        if(!isViewAttach()){
            return;
        }
        Model.postData(4, url, params, values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                mLog.e("Remark","-->"+data);
                try {
                    JSONObject object= new JSONObject(data);
                    JSONObject object1=object.getJSONObject("msgHeader");
                    switch (mwhat){
                        case 1:
                            try {
                                if(object1.getBoolean("result")){
                                    JSONObject remarkData=object.getJSONObject("data");
                                    int totleRecord=remarkData.getInt("dMax");
                                    JSONArray array=remarkData.getJSONArray("list");
                                    if(array.length()>0){
                                        liveRemarkData.clear();
                                        for (int i = 0; i <array.length() ; i++) {
                                            LiveRemark item=gson.fromJson(array.getJSONObject(i).toString(),LiveRemark.class);
                                            liveRemarkData.add(item);
                                        }
                                        getView().showData(totleRecord);
                                    }else {
                                        getView().showError("暂时没有评论数据");
                                    }
                                }else {
                                    String msg=object1.getString("errorInfo");
                                    getView().showError(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            if(object1.getBoolean("result")){
                                //刷新列表
                               getView().onRefresh("");
                            }else {
                                getView().showError(object1.getString("errorInfo"));
                            }
                            break;
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
