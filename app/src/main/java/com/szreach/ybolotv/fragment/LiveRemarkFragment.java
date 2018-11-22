package com.szreach.ybolotv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netease.hearttouch.htrefreshrecyclerview.HTLoadMoreListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshRecyclerView;
import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.adapter.LiveRemarkAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.LiveRemark;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.presenter.LiveDetailRemarkPresenter;
import com.szreach.ybolotv.utils.ShowToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZX on 2018/9/20
 */
public class LiveRemarkFragment extends BaseFragment implements MVPView,View.OnClickListener {

    private TextView sum;
    private HTRefreshRecyclerView refreshRecyclerView;
    private EditText remarkContent;
    private Button sendBtn;
    private LiveDetailRemarkPresenter remarkPresenter;

    private String liveId;
    private UserInfo userInfo;
    private List<LiveRemark> liveRemarkData=new ArrayList<>();
    private LiveRemarkAdapter adapter;
    private int sumRecord=0;
    private final String getContent_Url= Interface.getIpAddress(MyApplication.getApplication()) + Interface.URL_PREFIX_LIVE_COMMENT + Interface.URL_POST_LIVE_COMMENT;
    private final String sendContent_Url=Interface.getIpAddress(MyApplication.getApplication())+Interface.URL_PREFIX_LIVE_COMMENT+Interface.URL_POST_LIVE_COMMENT_SUBMIT;

    @Override
    protected int setContentView() {
        return R.layout.fragment_live_remark_layout;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle=getArguments();
        liveId=bundle.getString("liveId");
        sum=view.findViewById(R.id.live_detail_remark_sum);
        refreshRecyclerView=view.findViewById(R.id.live_detail_remark_list);
        remarkContent=view.findViewById(R.id.live_detail_remark_content);
        sendBtn=view.findViewById(R.id.live_detail_remark_btn);
        sendBtn.setOnClickListener(this);
        remarkPresenter=new LiveDetailRemarkPresenter();
        remarkPresenter.attachView(this);

        adapter=new LiveRemarkAdapter(liveRemarkData);

        refreshRecyclerView.startAutoRefresh();
        refreshRecyclerView.setAdapter(adapter);
        refreshRecyclerView.setEnableScrollOnRefresh(true);
        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshRecyclerView.setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        refreshRecyclerView.setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //没有个分页总页数，现在不添加分页
                refreshRecyclerView.setLoadMoreViewShow(false);
                refreshRecyclerView.setRefreshCompleted(true);
            }
        });
    }


    @Override
    protected void loadData() {
        userInfo= MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);
        params_values.clear();
        params_values.put("max","");
        params_values.put("commId","");
        params_values.put("liveId",liveId);
        remarkPresenter.getRemarkData(1,getContent_Url,params_values,liveRemarkData);
    }

    @Override
    public void showData(Object data) {
        adapter.notifyDataSetChanged();
        sum.setText(Html.fromHtml("共<font color='#3c67ca'>"+liveRemarkData.size()+"</font>条评论"));
        refreshRecyclerView.setRefreshCompleted(true);
    }


    @Override
    public void showError(String msg) {
        super.showError(msg);
        sum.setText(Html.fromHtml("共<font color='#3c67ca'>"+liveRemarkData.size()+"</font>条评论"));
        refreshRecyclerView.setRefreshCompleted(true);
    }

    @Override
    protected void stopLoad() {

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.live_detail_remark_btn){
            String  content=remarkContent.getText().toString().trim();
            if(content.equals("")){
                ShowToast.setToastShort("评论内容不能为空哦^_^");
            }else {
                //发送评论信息
                params_values.clear();
                params_values.put("coId",userInfo.getCoId());
                params_values.put("liveId",liveId);
                params_values.put("commContent",content);
                params_values.put("userId",userInfo.getUserId());
                remarkPresenter.getRemarkData(2,sendContent_Url,params_values,null);

            }


        }
    }

    //评论成功刷新列表
    @Override
    public void onRefresh(Object data) {
        loadData();
        remarkContent.setText("");
    }

    @Override
    public Context getContent() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        remarkPresenter.detachView();
    }


}
