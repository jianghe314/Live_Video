package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.VideoInfo;
import com.szreach.ybolotv.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZX on 2018/9/28
 */
public class VideoDetailIntroPresenter extends BasePresenter<MVPView> {

    public void getVideoIntro(String url, List<String> params,List<Object> values){
        if(!isViewAttach()){
            return;
        }
        Model.postData(9, url, params, values, new CallBack<String>() {
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
                        VideoInfo videoInfo=gson.fromJson(object.getJSONObject("data").toString(),VideoInfo.class);
                        getView().showData(videoInfo);
                    }else {
                        getView().showError(object1.getString("errorInfo"));
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
