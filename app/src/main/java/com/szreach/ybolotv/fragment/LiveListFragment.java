package com.szreach.ybolotv.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshRecyclerView;
import com.szreach.ybolotv.adapter.LiveListAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.bean.LiveList;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.utils.mLog;
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

import ybolo.szreach.com.live_vod.R;


public class LiveListFragment extends BaseFragment {

    private HTRefreshRecyclerView recyclerView;
    private List<LiveList> liveListData = new ArrayList<>();
    private LiveListAdapter liveListAdapter;
    private UserInfo userInfo;
    private NoticeListDataChange noticeListDataChange;

    @Override
    protected void HandMsg(int msg) {
        super.HandMsg(msg);
        switch (msg){
            case 22:
                liveListAdapter.notifyDataSetChanged();
                noticeListDataChange.dataChange(liveListData.size());
                recyclerView.setRefreshCompleted(false);
                break;
            case 88:
                liveListAdapter.notifyDataSetChanged();
                recyclerView.setRefreshCompleted(false);
                recyclerView.setLoadMoreViewShow(false);
                break;
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.live_list_fragment_layout;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        recyclerView=view.findViewById(R.id.live_list_recyclerView);
        liveListAdapter=new LiveListAdapter(liveListData,getActivity());
        userInfo=daoSession.getUserInfoDao().loadAll().get(0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.startAutoRefresh();
        recyclerView.setEnableScrollOnRefresh(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(liveListAdapter);
        noticeListDataChange= (NoticeListDataChange) getParentFragment();

        recyclerView.setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    protected void initData() {
       getData();
    }

    private void getData(){
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
                                    Msg=object1.getString("errorInfo");
                                    handler.sendEmptyMessage(88);
                                }
                            } else {
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
        }).start();
    }


    public interface NoticeListDataChange{
        void dataChange(int size);
    }
}
