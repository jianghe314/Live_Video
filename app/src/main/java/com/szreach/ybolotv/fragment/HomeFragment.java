package com.szreach.ybolotv.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ybolo.szreach.com.live_vod.R;
import ybolo.szreach.com.live_vod.adapter.LiveListAdapter;
import ybolo.szreach.com.live_vod.adapter.VideoListAdapter;
import ybolo.szreach.com.live_vod.base.BaseFragment;
import ybolo.szreach.com.live_vod.bean.LiveList;
import ybolo.szreach.com.live_vod.bean.UserInfo;
import ybolo.szreach.com.live_vod.bean.VideoList;
import ybolo.szreach.com.live_vod.mInterface.Interface;
import ybolo.szreach.com.live_vod.mui.mBanner;
import ybolo.szreach.com.live_vod.utils.mLog;

public class HomeFragment extends BaseFragment {


    @BindView(R.id.home_fragment_live_getMore)
    TextView LiveGetMore;
    @BindView(R.id.home_fragment_video_getMore)
    TextView VideoGetMore;
    Unbinder unbinder;

    private UserInfo userInfo;
    private List<LiveList> liveListData = new ArrayList<>();
    private LiveListAdapter liveadapter;
    private List<VideoList> videoListData = new ArrayList<>();
    private VideoListAdapter videoAdapter;
    private FragmentCallBack fragmentCallBack;
    private mBanner banner;

    private List<Integer> bannerList=new ArrayList<>();


    @Override
    protected void HandMsg(int msg) {
        super.HandMsg(msg);
        switch (msg) {
            case 22:
                liveadapter.notifyDataSetChanged();
                break;
            case 33:
                videoAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        fragmentCallBack = (FragmentCallBack) getActivity();
        userInfo = daoSession.getUserInfoDao().loadAll().get(0);
        RecyclerView liveRecyclerView = view.findViewById(R.id.home_fragment_live_recycler);
        RecyclerView videoRecyclerView = view.findViewById(R.id.home_fragment_video_recycler);
        banner= view.findViewById(R.id.home_fragment_banner);
        liveadapter = new LiveListAdapter(liveListData, getActivity());
        videoAdapter = new VideoListAdapter(videoListData, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        liveRecyclerView.setLayoutManager(linearLayoutManager);
        liveRecyclerView.setAdapter(liveadapter);
        bannerList.add(R.drawable.banner_error);
        bannerList.add(R.drawable.banner_error);
        bannerList.add(R.drawable.banner_error);

        banner.setBannerIntergerData(bannerList);
        banner.startSmoothAuto();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        videoRecyclerView.setLayoutManager(gridLayoutManager);
        videoRecyclerView.setAdapter(videoAdapter);


        mLog.e("userInfo", "-->coId" + userInfo.getCoId());
    }

    @Override
    protected int setContentView() {
        return R.layout.homefragment_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取直播列表
                params.clear();
                objects.clear();
                params.add("coId");
                objects.add(userInfo.getCoId());
                Request<JSONObject> request = NoHttp.createJsonObjectRequest(Interface.LiveList(), RequestMethod.POST);
                request.setDefineRequestBodyForJson(addParams(params, objects));
                SendRequest(2, request, new OnResponseListener<JSONObject>() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response<JSONObject> response) {
                        mLog.e("reponse", "-->" + response.get().toString());
                        try {
                            JSONObject object = new JSONObject(response.get().toString());
                            JSONObject object1 = object.getJSONObject("msgHeader");
                            if (object1.getBoolean("result")) {
                                JSONObject data = object.getJSONObject("data");
                                JSONArray array = data.getJSONArray("livelist");
                                if (array.length() > 0) {
                                    liveListData.clear();
                                    for (int i = 0; i < array.length(); i++) {
                                        LiveList liveList = gson.fromJson(array.getJSONObject(i).toString(), LiveList.class);
                                        liveListData.add(liveList);
                                    }
                                    handler.sendEmptyMessage(22);
                                } else {
                                    Msg = object1.getString("errorInfo");
                                    handler.sendEmptyMessage(88);
                                }
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

                //获取视频列表信息
                params.clear();
                objects.clear();
                params.add("coId");
                params.add("pageNumber");
                params.add("pageSize");
                params.add("groupId");
                params.add("sort");
                objects.add(userInfo.getCoId());
                objects.add(1);
                objects.add(6);
                objects.add("");
                objects.add("video_time");
                //视频列表排序 1:按时间排序  0：按点播数排序  默认时间排序
                mLog.e("VideoUrl", "-->" + Interface.VideoList());
                Request<JSONObject> request1 = NoHttp.createJsonObjectRequest(Interface.VideoList(), RequestMethod.POST);
                request1.setDefineRequestBodyForJson(addParams(params, objects));
                SendRequest(3, request1, new OnResponseListener<JSONObject>() {
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
                                JSONArray array = data.getJSONArray("list");
                                if (array.length() > 0) {
                                    videoListData.clear();
                                    for (int i = 0; i < array.length(); i++) {
                                        VideoList item = gson.fromJson(array.getJSONObject(i).toString(), VideoList.class);
                                        videoListData.add(item);
                                    }
                                    handler.sendEmptyMessage(33);
                                } else {
                                    Msg = "服务器异常，请稍后再试";
                                    handler.sendEmptyMessage(88);
                                }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.home_fragment_live_getMore, R.id.home_fragment_video_getMore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_fragment_live_getMore:
                fragmentCallBack.setFragmentCallBack(R.id.tab_menu2);
                break;
            case R.id.home_fragment_video_getMore:
                fragmentCallBack.setFragmentCallBack(R.id.tab_menu3);
                break;
        }
    }


    public interface FragmentCallBack {
        void setFragmentCallBack(int fragmentId);
    }
}
