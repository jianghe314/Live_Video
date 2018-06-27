package com.szreach.ybolotv.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.fragment.LiveIntroFragment;
import com.szreach.ybolotv.fragment.LiveRemarkFragment;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.player.YboloPlayer;
import com.szreach.ybolotv.utils.mLog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ybolo.szreach.com.live_vod.R;


public class LiveDetailActivity extends BaseActivity {


    @BindView(R.id.live_detail_back)
    ImageView back;
    @BindView(R.id.live_share)
    ImageView share;
    @BindView(R.id.ybolo_player_live)
    YboloPlayer live_player;
    @BindView(R.id.live_detail_intro)
    TextView intro_tv;
    @BindView(R.id.live_detail_intro_container)
    RelativeLayout introContainer;
    @BindView(R.id.live_detail_evalu)
    TextView remark_tv;
    @BindView(R.id.live_detail_remark_container)
    RelativeLayout remarkContainer;
    @BindView(R.id.live_detail_frame_layout)
    FrameLayout frame_layout;

    private String liveId, livePath = "";
    private long coId;
    private UserInfo userInfo;
    private LiveIntroFragment introFragment;
    private LiveRemarkFragment remarkFragment;

    @Override
    protected void HandlerMsg(int msg) {
        super.HandlerMsg(msg);
        switch (msg) {
            case 22:
                live_player.setDataSource(livePath);
                break;
            case 88:
                live_player.setDataSource("");
                break;
            case 99:
                live_player.setDataSource("");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        liveId = getIntent().getStringExtra("liveId");
        coId = getIntent().getLongExtra("coId", 0000);
        userInfo = daoSession.getUserInfoDao().loadAll().get(0);
        initView();
        initData();
        getLiveData();
    }

    private void initView() {

    }

    private void initData() {
        live_player.setBufferSize(30);
        live_player.setMaxBufferTimeSize(10f);
        live_player.setTimeOut(10, 10);
        switchToFragment(R.id.live_detail_intro_container);
        setTextColor(intro_tv);
    }

    @OnClick({R.id.live_detail_back, R.id.live_detail_intro_container, R.id.live_detail_remark_container})
    public void onViewClicked(View view) {
        switchToFragment(view.getId());
        switch (view.getId()) {
            case R.id.live_detail_back:
                finish();
                break;
        }

    }

    private void switchToFragment(int viewId){
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        hidAllFragment(fragmentTransaction);
        switch (viewId){
            case R.id.live_detail_intro_container:
                clearColor();
                setTextColor(intro_tv);
                if(null==introFragment){
                    introFragment=new LiveIntroFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("liveId",liveId);
                    bundle.putLong("coId",coId);
                    introFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.live_detail_frame_layout, introFragment);
                }else {
                    fragmentTransaction.show(introFragment);
                }
                break;
            case R.id.live_detail_remark_container:
                clearColor();
                setTextColor(remark_tv);
                if(null==remarkFragment){
                    remarkFragment=new LiveRemarkFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("liveId",liveId);
                    bundle.putLong("coId",coId);
                    bundle.putString("userId",userInfo.getUserId());
                    remarkFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.live_detail_frame_layout,remarkFragment);
                }else {
                    fragmentTransaction.show(remarkFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hidAllFragment(FragmentTransaction transaction) {
        if(introFragment!=null){
            transaction.hide(introFragment);
        }
       if(remarkFragment!=null){
            transaction.hide(remarkFragment);
       }

    }

    //获取直播路径
    private void getLiveData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                params.clear();
                objects.clear();
                params.add("userId");
                params.add("coId");
                params.add("userName");
                params.add("liveId");
                objects.add(userInfo.getUserId());
                objects.add(coId);
                objects.add(userInfo.getUserName());
                objects.add(liveId);
                String url = Interface.getIpAddress(getApplicationContext()) + Interface.URL_PREFIX_LIVE + Interface.URL_POST_LIVE_PLAY;
                mLog.e("url", "live-->" + url);
                Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
                request.setDefineRequestBodyForJson(addParams(params, objects));
                SendRequest(4, request, new OnResponseListener<JSONObject>() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response<JSONObject> response) {
                        mLog.e("reponse", "live-->" + response.get().toString());
                        try {
                            JSONObject object = new JSONObject(response.get().toString());
                            JSONObject object1 = object.getJSONObject("msgHeader");
                            if (object1.getBoolean("result")) {
                                JSONObject data = object.getJSONObject("data");
                                livePath = data.getString("playLive");
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
        }).start();

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
        if (live_player != null) {
            live_player.onPause();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        if (live_player != null) {
            live_player.onDestory();
        }
        super.onDestroy();
    }



}
