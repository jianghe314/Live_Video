package com.szreach.ybolotv.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.szreach.ybolotv.App;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.bean.LiveInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.utils.mLog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ybolo.szreach.com.live_vod.R;


public class LiveIntroFragment extends BaseFragment {

    private TextView title,time,person,describe;
    private ImageView person_iv;
    private String videoId;
    private long coId;

    private LiveInfo liveInfo;

    @Override
    protected void HandMsg(int msg) {
        super.HandMsg(msg);
        switch (msg){
            case 22:
                title.setText(liveInfo.getLiveName());
                time.setText(liveInfo.getLiveStart()+"-"+liveInfo.getLiveEnd());
                person.setText(liveInfo.getOnlineCount()+"");
                describe.setText(liveInfo.getLiveName()+","+liveInfo.getChannelName());

                break;
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_live_intro_layout;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Bundle bundle=getArguments();
        videoId=bundle.getString("liveId");
        coId=bundle.getLong("coId");
        title=view.findViewById(R.id.live_detail_title);
        time=view.findViewById(R.id.live_detail_time);
        person=view.findViewById(R.id.live_detail_person);
        describe=view.findViewById(R.id.live_detail_describe);
        person_iv=view.findViewById(R.id.live_detail_person_iv);
    }

    @Override
    protected void initData() {
        params.clear();
        objects.clear();
        params.add("coId");
        params.add("liveId");
        objects.add(coId);
        objects.add(videoId);
        String url= Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_LIVE+Interface.URL_POST_LIVE_INFO;
        Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
        request.setDefineRequestBodyForJson(addParams(params,objects));
        SendRequest(14, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                mLog.e("reponse","liveIntro-->"+response.get().toString());
                try {
                    JSONObject object=new JSONObject(response.get().toString());
                    JSONObject object1=object.getJSONObject("msgHeader");
                    if(object1.getBoolean("result")){
                        liveInfo=gson.fromJson(object.getJSONObject("data").toString(),LiveInfo.class);
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
