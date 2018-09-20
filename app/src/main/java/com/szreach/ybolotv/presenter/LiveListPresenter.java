package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.LiveList;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZX on 2018/9/19
 */
public class LiveListPresenter extends BasePresenter<MVPView> {

    public void getLiveData(List<String> params, List<Object> values, final List<LiveList> objectData){

        if(!isViewAttach()){
            return;
        }
        Model.postData(1, Interface.LiveList(), params, values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                try {
                    JSONObject object=new JSONObject(data);
                    JSONObject head=object.getJSONObject("msgHeader");
                    if(head.getBoolean("result")){
                        JSONObject json_data = object.getJSONObject("data");
                        JSONArray array = json_data.getJSONArray("livelist");
                        if (array.length() > 0) {
                            objectData.clear();
                            for (int i = 0; i < array.length(); i++) {
                                LiveList liveList = gson.fromJson(array.getJSONObject(i).toString(), LiveList.class);
                                objectData.add(liveList);
                            }
                            getView().showData(objectData);

                        } else {
                            String msg=head.getString("errorInfo");
                            getView().showError(msg);
                        }
                    }else{
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
