package com.szreach.ybolotv.presenter;

import android.view.Display;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.model.Model;

import java.util.List;

/**
 * Created by ZX on 2018/9/18
 */
public class LoginPresenter extends BasePresenter<MVPView> {

    public void Login(List<String> params,List<Object> values){
        if(!isViewAttach()){
            //没有持有View直接返回
            return;
        }
        Model.postData(0, Interface.Login(), params, values, new CallBack<String>() {
            @Override
            public void onLoading() {
                //显示耗时
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {

            }

            @Override
            public void onRefresh(int waht, String data) {

            }

            @Override
            public void onFail(String msg) {

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
