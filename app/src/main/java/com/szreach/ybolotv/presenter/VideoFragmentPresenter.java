package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.CallBack;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.VideoList;
import com.szreach.ybolotv.bean.VideoTitle;
import com.szreach.ybolotv.model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ZX on 2018/9/26
 */
public class VideoFragmentPresenter extends BasePresenter<MVPView> {

    //获取Tab标签
    public void getTabTitle(final int mwhat, String url, Map<String,Object> params_values, final List<VideoTitle> TitleData){

        if(!isViewAttach()){
            return;
        }
        Model.postData(6, url, params_values, new CallBack<String>() {
            @Override
            public void onLoading() {
                getView().showLoading();
            }

            @Override
            public void onSuccess(int waht, String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject object1 = object.getJSONObject("msgHeader");
                    switch (mwhat){
                        case 1:
                            //获取标签栏
                            if (object1.getBoolean("result")) {
                                JSONArray array = object.getJSONArray("data");
                                if (array.length() > 0) {
                                    TitleData.clear();
                                    for (int i = 0; i < array.length(); i++) {
                                        VideoTitle item = gson.fromJson(array.getJSONObject(i).toString(), VideoTitle.class);
                                        TitleData.add(item);
                                    }
                                    getView().showData(mwhat);
                                }
                            } else {
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

    //获取标题栏数据
    public void getTabData(String url, int mwhat, Map<String,Object> params_values, final List<VideoList> videoListData, final boolean isClear){

        if(!isViewAttach()){
            return;
        }
        Model.postData(7, url, params_values, new CallBack<String>() {
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
                        JSONObject video_data=object.getJSONObject("data");
                        int totalPage=video_data.getInt("totalPage");
                        int videoSum=video_data.getInt("totalRecord");
                        int[] page_sum={totalPage,videoSum};
                        JSONArray array=video_data.getJSONArray("list");
                        if(array.length()>0){
                            if(isClear){
                                videoListData.clear();
                            }
                            for (int i = 0; i <array.length(); i++) {
                                VideoList item=gson.fromJson(array.getJSONObject(i).toString(),VideoList.class);
                                videoListData.add(item);
                            }
                            getView().onRefresh(page_sum);
                        }else {
                            videoListData.clear();
                            getView().onRefresh("");
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
}
