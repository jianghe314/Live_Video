package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZX on 2018/9/20
 */
public class LiveDetailPresenter extends BasePresenter<MVPView> {

    public void getLivePath(List<String> params, List<Object> values){

        if(!isViewAttach()){
            return;
        }
        Model.postData(2, Interface.getIpAddress(MyApplication.getApplication()) + Interface.URL_PREFIX_LIVE + Interface.URL_POST_LIVE_PLAY, params, values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject head=object.getJSONObject("msgHeader");
                    if(head.getBoolean("result")){
                        JSONObject jsonData=object.getJSONObject("data");
                        getView().showData(jsonData.getString("playLive"));
                    }else {
                        String msg=head.getString("errorInfo");
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
