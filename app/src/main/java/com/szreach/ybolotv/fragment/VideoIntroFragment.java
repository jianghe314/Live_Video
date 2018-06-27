package com.szreach.ybolotv.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ybolo.szreach.com.live_vod.App;
import ybolo.szreach.com.live_vod.R;
import ybolo.szreach.com.live_vod.base.BaseFragment;
import ybolo.szreach.com.live_vod.bean.VideoInfo;
import ybolo.szreach.com.live_vod.mInterface.Interface;
import ybolo.szreach.com.live_vod.utils.mLog;

public class VideoIntroFragment extends BaseFragment {

    private TextView title,time,person,describe;
    private String videoId;
    private long coId;

    private VideoInfo videoInfo;

    @Override
    protected void HandMsg(int msg) {
        super.HandMsg(msg);
        switch (msg){
            case 22:
                title.setText(videoInfo.getVideoCname());
                time.setText(videoInfo.getVideoTime());
                person.setText(videoInfo.getVideoVod()+"");
                describe.setText(videoInfo.getVideoDesc());
                break;
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_video_intro_layout;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Bundle bundle=getArguments();
        videoId=bundle.getString("videoId");
        coId=bundle.getLong("coId");
        title=view.findViewById(R.id.video_detail_title);
        time=view.findViewById(R.id.video_detail_time);
        person=view.findViewById(R.id.video_detail_person);
        describe=view.findViewById(R.id.video_detail_describe);
    }

    @Override
    protected void initData() {
        params.clear();
        objects.clear();
        params.add("coId");
        params.add("vodId");
        objects.add(coId);
        objects.add(videoId);
        String url= Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_VIDEO+Interface.URL_POST_VIDEO_INFO;
        Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
        request.setDefineRequestBodyForJson(addParams(params,objects));
        SendRequest(11, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                mLog.e("reponse","-->"+response.get().toString());
                try {
                    JSONObject object=new JSONObject(response.get().toString());
                    JSONObject object1=object.getJSONObject("msgHeader");
                    if(object1.getBoolean("result")){
                        videoInfo=gson.fromJson(object.getJSONObject("data").toString(),VideoInfo.class);
                        handler.sendEmptyMessage(22);
                    }else {
                        Msg=object1.getString("errorInfo");
                        handler.sendEmptyMessage(88);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                handler.sendEmptyMessage(99);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
