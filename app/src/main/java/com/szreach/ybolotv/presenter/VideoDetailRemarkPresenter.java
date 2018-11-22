package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.VideoRemark;
import com.szreach.ybolotv.model.Model;
import com.szreach.ybolotv.utils.mLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ZX on 2018/9/28
 */
public class VideoDetailRemarkPresenter extends BasePresenter<MVPView> {

    //获取评论数据
    public void getRemarkData(String url, Map<String,Object> params_values, final List<VideoRemark> remarkData, final boolean isClear){
        if(!isViewAttach()){
            return;
        }
        Model.postData(9, url, params_values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                mLog.e("RemarkData","-->"+data);
                try {
                    JSONObject object=new JSONObject(data);
                    JSONObject object1=object.getJSONObject("msgHeader");
                    if(object1.getBoolean("result")){
                        JSONObject mdata=object.getJSONObject("data");
                        int[] sum_data=new int[2];
                        sum_data[0]=mdata.getInt("totalRecord");
                        sum_data[1]=mdata.getInt("totalPage");
                        JSONArray array=mdata.getJSONArray("recordList");
                        if(array.length()>0){
                            if(isClear){
                                remarkData.clear();
                            }
                            for (int i = 0; i <array.length(); i++) {
                                VideoRemark item=gson.fromJson(array.getJSONObject(i).toString(),VideoRemark.class);
                                remarkData.add(item);
                            }
                            getView().showData(sum_data);
                        }else {
                            //没有评论数据
                            getView().showData(sum_data);
                            getView().showError("暂无相关评论！");
                        }
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

    //发送评论数据
    public void sendRemarkData(String url,Map<String,Object> params_values){
        if(!isViewAttach()){
            return;
        }
        Model.postData(10, url, params_values, new CallBack<String>() {
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
                        //评论成功，刷新列表，清空编辑框
                        getView().onRefresh("");
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
                getView().showError(msg     );
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }
}
