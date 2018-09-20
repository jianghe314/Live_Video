package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import java.util.List;

/**
 * Created by ZX on 2018/9/20
 */
public class LiveDetailIntroPresenter extends BasePresenter<MVPView> {

    //获取直播简介
    public void getLiveIntro(List<String> params, List<Object> data){
        for (int i = 0; i <params.size() ; i++) {
            mLog.e("Params","-->"+params.get(i)+":"+data.get(i));
        }
        if(!isViewAttach()){
            return;
        }
        String url= Interface.getIpAddress(MyApplication.getApplication()) + Interface.URL_PREFIX_LIVE + Interface.URL_POST_LIVE_INFO;
        mLog.e("URL","-->"+url);
        Model.postData(3, Interface.getIpAddress(MyApplication.getApplication()) + Interface.URL_PREFIX_LIVE + Interface.URL_POST_LIVE_INFO, params, data, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                mLog.e("LiveIntro","-->"+data);
            }

            @Override
            public void onRefresh(int waht, String data) {

            }

            @Override
            public void onError(String msg) {
                getView().hideLoading();
                getView().showError(msg);
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }
}
