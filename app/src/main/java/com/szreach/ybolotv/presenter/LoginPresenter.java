package com.szreach.ybolotv.presenter;


import android.util.Log;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.db.UserInfoDao;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ZX on 2018/9/18
 */
public class LoginPresenter extends BasePresenter<MVPView> {

    public void Login(Map<String,Object> params_values){
        if(!isViewAttach()){
            //没有持有View直接返回
            return;
        }
        Model.postData(0, Interface.Login(), params_values, new CallBack<String>() {
            @Override
            public void onLoading() {
                //显示耗时
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    JSONObject head=jsonObject.getJSONObject("msgHeader");
                    if(head.getBoolean("result")){
                        //接收数据
                        UserInfo userInfo=gson.fromJson(jsonObject.getJSONObject("data").toString(),UserInfo.class);
                        UserInfoDao userInfoDao=MyApplication.getDaoSession().getUserInfoDao();
                        userInfoDao.insertOrReplace(userInfo);
                        getView().showData("");
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
