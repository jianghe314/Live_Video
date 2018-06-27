package com.szreach.ybolotv.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.fragment.VideoIntroFragment;
import com.szreach.ybolotv.fragment.VideoRemarkFragment;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.player.YboloPlayer;
import com.szreach.ybolotv.utils.mLog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ybolo.szreach.com.live_vod.R;


public class VideoDetailActivity extends BaseActivity {


    @BindView(R.id.video_detail_back)
    ImageView back;
    @BindView(R.id.video_share)
    ImageView share;
    @BindView(R.id.ybolo_player_video)
    YboloPlayer video_player;
    @BindView(R.id.video_detail_intro)
    TextView intro_tv;
    @BindView(R.id.video_fragment_intro_container)
    RelativeLayout introContainer;
    @BindView(R.id.video_detail_evalu)
    TextView remark_tv;
    @BindView(R.id.video_fragment_remark_container)
    RelativeLayout remarkContainer;
    @BindView(R.id.video_fragment_frame_layout)
    android.widget.FrameLayout FrameLayout;

    private String videoId;
    private long coId;
    private String videoPath="";

    private UserInfo userInfo;
    private VideoIntroFragment introFragment;
    private VideoRemarkFragment remarkFragment;

    @Override
    protected void HandlerMsg(int msg) {
        super.HandlerMsg(msg);
        switch (msg) {
            case 22:
                mLog.e("视频地址","-->"+videoPath);
                video_player.setDataSource(videoPath);
                break;
            case 88:
                video_player.setDataSource("");
                break;
            case 99:
                //网络报错，处理
                video_player.setDataSource("");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        videoId = getIntent().getStringExtra("videoId");
        coId = getIntent().getLongExtra("coId", 0000);
        userInfo = daoSession.getUserInfoDao().loadAll().get(0);
        initData();
        getVideoPath();
    }

    private void initData() {
        video_player.setBufferSize(30);
        video_player.setMaxBufferTimeSize(10f);
        video_player.setTimeOut(10, 10);
        switchToFragment(R.id.video_fragment_intro_container);
        setTextColor(intro_tv);
    }


    private void getVideoPath() {
        params.clear();
        objects.clear();
        params.add("userId");
        params.add("coId");
        params.add("videoId");
        objects.add(userInfo.getUserId());
        objects.add(coId);
        objects.add(videoId);
        String url = Interface.getIpAddress(getApplicationContext()) + Interface.URL_PREFIX_VIDEO_PLAY + Interface.URL_POST_VIDEO_PLAY;
        mLog.e("url", "-->" + url);
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
        request.setDefineRequestBodyForJson(addParams(params, objects));
        SendRequest(5, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                mLog.e("reponse", "video-->" + response.get().toString());
                try {
                    JSONObject object = new JSONObject(response.get().toString());
                    JSONObject object1 = object.getJSONObject("msgHeader");
                    if (object1.getBoolean("result")) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray videoObj=data.getJSONArray("videoObj");
                        JSONObject object2=videoObj.getJSONObject(0);
                        JSONArray channel=object2.getJSONArray("channel");
                        JSONObject object3=channel.getJSONObject(0);
                        JSONArray files=object3.getJSONArray("files");
                        JSONObject pathData=files.getJSONObject(0);
                        videoPath=pathData.getString("url");
                        handler.sendEmptyMessage(22);
                    } else {
                        Msg = object1.getString("errorInfo");
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

    @OnClick({R.id.video_detail_back, R.id.video_fragment_intro_container, R.id.video_fragment_remark_container})
    public void onViewClicked(View view) {
        switchToFragment(view.getId());
        switch (view.getId()){
            case R.id.video_detail_back:
                finish();
                break;
        }
    }

    private void switchToFragment(int which) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        switch (which) {
            case R.id.video_fragment_intro_container:
                clearColor();
                setTextColor(intro_tv);
                if (introFragment == null) {
                    introFragment = new VideoIntroFragment();
                    Bundle data=new Bundle();
                    data.putString("videoId",videoId);
                    data.putLong("coId",coId);
                    introFragment.setArguments(data);
                    fragmentTransaction.add(R.id.video_fragment_frame_layout, introFragment);
                } else {
                    fragmentTransaction.show(introFragment);
                }
                break;
            case R.id.video_fragment_remark_container:
                clearColor();
                setTextColor(remark_tv);
                if (remarkFragment == null) {
                    remarkFragment = new VideoRemarkFragment();
                    Bundle data=new Bundle();
                    data.putString("videoId",videoId);
                    data.putLong("coId",coId);
                    data.putString("userId",userInfo.getUserId());
                    remarkFragment.setArguments(data);
                    fragmentTransaction.add(R.id.video_fragment_frame_layout, remarkFragment);
                } else {
                    fragmentTransaction.show(remarkFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (introFragment != null) {
            transaction.hide(introFragment);
        }
        if (remarkFragment != null) {
            transaction.hide(remarkFragment);
        }
    }


    private void clearColor() {
        intro_tv.setTextColor(getResources().getColor(R.color.color_black_gray));
        remark_tv.setTextColor(getResources().getColor(R.color.color_black_gray));
    }

    private void setTextColor(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.live_textColor));
    }


    @Override
    protected void onPause() {
        if (video_player != null) {
            video_player.onPause();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        if (video_player != null) {
            video_player.onDestory();
        }
        super.onDestroy();
    }


}
