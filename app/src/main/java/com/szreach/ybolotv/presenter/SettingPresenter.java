package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZX on 2018/9/29
 */
public class SettingPresenter extends BasePresenter<MVPView> {

    //上传修改的密码
    public void sendPsd(String url, List<String> params,List<Object> values){
        if(!isViewAttach()){
            return;
        }
        Model.postData(10, url, params, values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int what, String data) {
                mLog.e("PSD","-->"+data);
                try {
                    JSONObject object=new JSONObject(data);
                    JSONObject object1=object.getJSONObject("msgHeader");
                    if(object1.getBoolean("result")){
                        getView().showError("修改成功");
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
