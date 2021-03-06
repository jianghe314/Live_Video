package com.szreach.ybolotv.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshRecyclerView;
import com.netease.hearttouch.htrefreshrecyclerview.base.HTBaseRecyclerView;
import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.adapter.LiveListAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.LiveList;
import com.szreach.ybolotv.presenter.LiveListPresenter;
import com.szreach.ybolotv.utils.mLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ZX on 2018/9/18
 */
public class LiveFragment extends BaseFragment implements MVPView {

    private RelativeLayout titleRela;
    private HTRefreshRecyclerView recyclerView;
    private LiveListAdapter liveListAdapter;

    private LiveListPresenter liveListPresenter;
    private List<LiveList> data=new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.live_fragment_layout;
    }


    @Override
    protected void initView(View view) {
        titleRela=view.findViewById(R.id.live_fragment_title);
        recyclerView=view.findViewById(R.id.live_list_recyclerView);
        liveListAdapter=new LiveListAdapter(data,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.startAutoRefresh();
        recyclerView.setEnableScrollOnRefresh(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(liveListAdapter);
        recyclerView.setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh() {
                params_values.clear();
                params_values.put("coId",MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0).getCoId());
                liveListPresenter.getLiveData(params_values,data);
            }
        });
    }

    @Override
    public void showData(Object data) {
        liveListAdapter.notifyDataSetChanged();
        recyclerView.setRefreshCompleted(false);
        recyclerView.setLoadMoreViewShow(false);
    }

    @Override
    protected void loadData() {
        liveListPresenter=new LiveListPresenter();
        liveListPresenter.attachView(this);
        params_values.clear();
        params_values.put("coId",MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0).getCoId());
        liveListPresenter.getLiveData(params_values,data);
    }

    @Override
    protected void stopLoad() {

    }

    @Override
    public Context getContent() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        liveListPresenter.detachView();
    }
}
