package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ZX on 2018/9/27
 */
public class VideoDetailPresenter extends BasePresenter<MVPView> {

    //获取点播视频路径
    public void getVideoUrl(String url, Map<String,Object> params_values){
        if(!isViewAttach()){
            return;
        }
        Model.postData(8, url, params_values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                mLog.e("VideoUrl","-->"+data);
                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject object1 = object.getJSONObject("msgHeader");
                    if (object1.getBoolean("result")) {
                        JSONObject mdata = object.getJSONObject("data");
                        JSONArray videoObj=mdata.getJSONArray("videoObj");
                        JSONObject object2=videoObj.getJSONObject(0);
                        JSONArray channel=object2.getJSONArray("channel");
                        JSONObject object3=channel.getJSONObject(0);
                        JSONArray files=object3.getJSONArray("files");
                        JSONObject pathData=files.getJSONObject(0);
                        String  videoPath=pathData.getString("url");
                        getView().showData(videoPath);
                    } else {
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
