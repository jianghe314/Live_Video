package com.szreach.ybolotv.fragment;

import android.content.Context;
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
import com.szreach.ybolotv.adapter.VideoRemarkAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.bean.VideoRemark;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.presenter.VideoDetailRemarkPresenter;
import com.szreach.ybolotv.utils.ShowToast;
import com.yanzhenjie.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZX on 2018/9/28
 */
public class VideoRemarkFragment extends BaseFragment implements MVPView,View.OnClickListener{

    private String videoId;
    private TextView remarkSum;
    private HTRefreshRecyclerView remarkList;
    private EditText remarkEdit;
    private Button sendBtn;


    private List<VideoRemark> remarkData=new ArrayList<>();
    private VideoRemarkAdapter adapter;
    private int totalRecord;
    private int totalPage;
    private int pageNumber=1;
    private UserInfo userInfo;

    private VideoDetailRemarkPresenter remarkPresenter;
    private String getRemarkData= Interface.getIpAddress(MyApplication.getApplication())+Interface.URL_PREFIX_VIDEO_COMMENT+Interface.URL_POST_VIDEO_COMMENT;
    private String sendReamrkData=Interface.getIpAddress(MyApplication.getApplication())+Interface.URL_PREFIX_VIDEO_COMMENT+Interface.URL_POST_VIDEO_COMMENT_SUBMIT;

    @Override
    protected int setContentView() {
        return R.layout.fragment_video_remark_layout;
    }

    @Override
    protected void initView(View view) {
        videoId=getArguments().getString("videoId");
        remarkSum=view.findViewById(R.id.video_detail_remark_sum);
        remarkList=view.findViewById(R.id.video_detail_remark_list);
        remarkEdit=view.findViewById(R.id.video_detail_remark_content);
        sendBtn=view.findViewById(R.id.video_detail_remark_btn);
        sendBtn.setOnClickListener(this);
        adapter=new VideoRemarkAdapter(remarkData);
        userInfo=MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);

        remarkList.startAutoRefresh();
        remarkList.setAdapter(adapter);
        remarkList.setEnableScrollOnRefresh(true);
        remarkList.setLayoutManager(new LinearLayoutManager(getActivity()));
        remarkList.setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        remarkList.setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(pageNumber<totalPage){
                    pageNumber=pageNumber+1;
                    params_values.clear();
                    params_values.put("commentPageSize",6);
                    params_values.put("commentPageNum",pageNumber);
                    params_values.put("videoId",videoId);
                    params_values.put("coId",userInfo.getCoId());
                    remarkPresenter.getRemarkData(getRemarkData, params_values,remarkData,false);
                }else {
                    ShowToast.setToastShort("没有更多了");
                    remarkList.setLoadMoreViewShow(false);
                    remarkList.setRefreshCompleted(false);
                }
            }
        });

        remarkPresenter=new VideoDetailRemarkPresenter();
        remarkPresenter.attachView(this);
    }

    @Override
    protected void stopLoad() {

    }

    @Override
    protected void loadData() {
        pageNumber=1;

        params_values.clear();
        params_values.put("commentPageSize",6);
        params_values.put("commentPageNum",pageNumber);
        params_values.put("videoId",videoId);
        params_values.put("coId",userInfo.getCoId());
        remarkPresenter.getRemarkData(getRemarkData,params_values,remarkData,true);
    }

    @Override
    public void showData(Object data) {
        int[] mdata= (int[]) data;
        totalRecord=mdata[0];
        totalPage=mdata[1];
        adapter.notifyDataSetChanged();
        remarkSum.setText(Html.fromHtml("共<font color='#3c67ca'>"+totalRecord+"</font>条评论"));
        remarkList.setRefreshCompleted(true);
        remarkList.setLoadMoreViewShow(false);
        remarkList.setRefreshCompleted(false);
    }

    @Override
    public void onClick(View v) {
        String remarkContent=remarkEdit.getText().toString().trim();
        switch (v.getId()){
            case R.id.video_detail_remark_btn:
                if(remarkContent.equals("")){
                    ShowToast.setToastShort("内容不能为空哦^_^");
                }else {
                    params_values.clear();
                    params_values.put("coId",userInfo.getCoId());
                    params_values.put("videoId",videoId);
                    params_values.put("commContent",remarkContent);
                    if(null==userInfo.getUserId()||userInfo.getUserId().equals("")){
                        params_values.put("userId","-1");
                        params_values.put("userName","游客");
                    }else {
                        params_values.put("userId",userInfo.getUserId());
                    }
                    remarkPresenter.sendRemarkData(sendReamrkData,params_values);
                }
                break;
        }
    }


    @Override
    public void onRefresh(Object data) {
        remarkEdit.setText("");
        loadData();
    }

    @Override
    public Context getContent() {
        return null;
    }


}
