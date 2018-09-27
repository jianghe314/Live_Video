package com.szreach.ybolotv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.hearttouch.htrefreshrecyclerview.HTLoadMoreListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshRecyclerView;
import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.adapter.VideoListAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.bean.VideoList;
import com.szreach.ybolotv.bean.VideoTitle;
import com.szreach.ybolotv.bean.VideoTitle2;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.presenter.VideoFragmentPresenter;
import com.szreach.ybolotv.utils.ShowToast;
import com.szreach.ybolotv.utils.mLog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZX on 2018/9/18
 */
public class VideoFragment extends BaseFragment implements MVPView ,View.OnClickListener{

    private SearchView searchView;
    private TabLayout tab_layout;
    private HTRefreshRecyclerView refreshRecyclerView;
    private LinearLayout title2_layout;
    private TextView videoSumHint;
    private TextView videoSort;

    private final String Tab_Url=  Interface.getIpAddress(MyApplication.getApplication()) + Interface.URL_PREFIX_VIDEO + Interface.URL_POST_VIDEO_GROUP_LIST;
    private final String videoUrl=Interface.getIpAddress(MyApplication.getApplication())+Interface.URL_PREFIX_VIDEO+Interface.URL_POST_VIDEO_LIST_NOGROUP;

    private VideoFragmentPresenter videoFragmentPresenter;
    private UserInfo userInfo;
    private List<VideoTitle> videoData = new ArrayList<>();
    private VideoListAdapter adapter;
    private List<VideoList> videoListData=new ArrayList<>();
    private int pageNumber=1;
    private int pageSize=6;
    private int totalPage=0;
    private int tabPostion=0;
    private int videoSum=0;
    private String videoFlag="video_time";

    @Override
    protected int setContentView() {
        return R.layout.video_fragment_layout;
    }

    @Override
    protected void initView(View view) {
        searchView=view.findViewById(R.id.video_fragment_searchView);
        tab_layout=view.findViewById(R.id.video_fragment_tab);
        title2_layout=view.findViewById(R.id.video_fragment_addView);
        videoSumHint=view.findViewById(R.id.video_fragment_videoSumHint);
        videoSort=view.findViewById(R.id.video_frgment_videoSort);
        refreshRecyclerView=view.findViewById(R.id.video_fragment_content);
        videoSort.setOnClickListener(this);
        videoSort.setTag(true);        //视频排序默认true按最新发布排序
        videoSort.setText("最新发布");

        userInfo=MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);

        videoFragmentPresenter=new VideoFragmentPresenter();
        videoFragmentPresenter.attachView(this);

        refreshRecyclerView.startAutoRefresh();
        refreshRecyclerView.setEnableScrollOnRefresh(true);
        adapter=new VideoListAdapter(videoListData,getActivity());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        refreshRecyclerView.setLayoutManager(gridLayoutManager);
        refreshRecyclerView.setAdapter(adapter);
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title2_layout.removeAllViews();
                setChildTitle(tab.getPosition());
                //获取选中视频标签栏的数据
                tabPostion=tab.getPosition();
                pageNumber=1;
                params.clear();
                values.clear();

                params.add("coId");
                params.add("pageNumber");
                params.add("pageSize");
                params.add("groupId");
                params.add("sort");         //视频分类标准
                params.add("videoCName");

                values.add(userInfo.getCoId());
                values.add(pageNumber);
                values.add(6);
                values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                values.add(videoFlag);
                values.add("");
                videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        refreshRecyclerView.setEnableScrollOnRefresh(true);
        refreshRecyclerView.setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                pageNumber=1;

                params.clear();
                values.clear();

                params.add("coId");
                params.add("pageNumber");
                params.add("pageSize");
                params.add("groupId");
                params.add("sort");         //视频分类标准
                params.add("videoCName");

                values.add(userInfo.getCoId());
                values.add(pageNumber);
                values.add(6);
                values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                values.add(videoFlag);
                values.add("");
                videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
                refreshRecyclerView.setRefreshCompleted(true);
            }
        });
        refreshRecyclerView.setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //上拉加载更多
                if(pageNumber<totalPage){
                    pageNumber=pageNumber+1;

                    params.clear();
                    values.clear();

                    params.add("coId");
                    params.add("pageNumber");
                    params.add("pageSize");
                    params.add("groupId");
                    params.add("sort");         //视频分类标准
                    params.add("videoCName");

                    values.add(userInfo.getCoId());
                    values.add(pageNumber);
                    values.add(6);
                    values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                    values.add(videoFlag);
                    values.add("");
                    videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,false);
                }else {
                    ShowToast.setToastShort("没有更多了");
                    refreshRecyclerView.setLoadMoreViewShow(false);
                    refreshRecyclerView.setRefreshCompleted(false);
                }
                refreshRecyclerView.setRefreshCompleted(true);
            }
        });
        //搜索框的监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            String queryText;
            //当点击搜索时触发
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query.length()>0){
                    pageNumber=1;

                    params.clear();
                    values.clear();

                    params.add("coId");
                    params.add("pageNumber");
                    params.add("pageSize");
                    params.add("groupId");
                    params.add("sort");         //视频分类标准
                    params.add("videoCName");

                    values.add(userInfo.getCoId());
                    values.add(pageNumber);
                    values.add(6);
                    values.add("");
                    values.add(videoFlag);
                    values.add(query);
                    videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
                }
                return true;
            }

            //当输入框文字发生改变是触发
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //关闭搜索时监听
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                pageNumber=1;

                params.clear();
                values.clear();

                params.add("coId");
                params.add("pageNumber");
                params.add("pageSize");
                params.add("groupId");
                params.add("sort");         //视频分类标准
                params.add("videoCName");

                values.add(userInfo.getCoId());
                values.add(pageNumber);
                values.add(6);
                values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                values.add(videoFlag);
                values.add("");
                videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
                return false;
            }
        });

    }

    @Override
    protected void loadData() {
        params.clear();
        values.clear();
        params.add("coId");
        values.add(userInfo.getCoId());
        videoFragmentPresenter.getTabTitle(1,Tab_Url,params,values,videoData);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showData(Object data) {
        switch (Integer.valueOf(data.toString())){
            case 1:
                //显示标题栏
                addTitle();
                break;
        }
    }

    @Override
    public void onRefresh(Object data) {
        if(!"".equals(data)){
            int[] mdata= (int[]) data;
            totalPage=mdata[0];
            videoSum=mdata[1];
        }
        //添加视频数据
        adapter.notifyDataSetChanged();
        //如果某视频标签下没有视频
        if(videoListData.size()==0){
            refreshRecyclerView.setLoadMoreViewShow(false);
            refreshRecyclerView.setRefreshCompleted(false);
            ShowToast.setToastShort("暂时没有相关视频");
        }
        videoSumHint.setText(Html.fromHtml("共<font color='#3c67ca'>" + videoSum +"</font>个视频"));
        refreshRecyclerView.setRefreshCompleted(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //视频排序
            case R.id.video_frgment_videoSort:
                if((boolean)videoSort.getTag()){
                    videoSort.setText("最多浏览");
                    videoSort.setTag(false);
                    videoFlag="video_vod";

                    params.clear();
                    values.clear();

                    params.add("coId");
                    params.add("pageNumber");
                    params.add("pageSize");
                    params.add("groupId");
                    params.add("sort");         //视频分类标准
                    params.add("videoCName");

                    values.add(userInfo.getCoId());
                    values.add(pageNumber);
                    values.add(6);
                    values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                    values.add(videoFlag);
                    values.add("");
                    videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
                }else {
                    videoSort.setText("最新发布");
                    videoSort.setTag(true);
                    videoFlag="video_time";

                    params.clear();
                    values.clear();

                    params.add("coId");
                    params.add("pageNumber");
                    params.add("pageSize");
                    params.add("groupId");
                    params.add("sort");         //视频分类标准
                    params.add("videoCName");

                    values.add(userInfo.getCoId());
                    values.add(pageNumber);
                    values.add(6);
                    values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                    values.add(videoFlag);
                    values.add("");
                    videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
                }
                break;
        }
    }

    private void addTitle() {
        //刷新TabLayout数据
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_layout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tab_layout.setSelectedTabIndicatorHeight(5);
        tab_layout.addTab(tab_layout.newTab().setText("全部"), 0, true);
        for (int i = 0; i < videoData.size(); i++) {
            tab_layout.addTab(tab_layout.newTab().setText(videoData.get(i).getGroupName()), false);
        }
    }

    private void setChildTitle(int position){
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(position==0){
            TextView all=new TextView(getActivity());
            all.setText("全部");
            all.setTextColor(getResources().getColor(R.color.live_textColor));
            all.setLayoutParams(lp);
            title2_layout.addView(all);
        }else {
            VideoTitle videoTitle=videoData.get(position-1);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin=40;
            final TextView tv1=new TextView(getActivity());
            final TextView tv2=new TextView(getActivity());
            for (final VideoTitle2 childTitle:videoTitle.getTwoVideoGroupList()) {
                if(!childTitle.getGroupName().equals("")&&null!=childTitle.getGroupName()){
                    tv1.setText("全部");
                    tv1.setTextColor(getResources().getColor(R.color.live_textColor));
                    tv1.setLayoutParams(lp);
                    tv2.setText(childTitle.getGroupName());
                    tv2.setTextColor(getResources().getColor(R.color.color_text_gray));
                    tv2.setLayoutParams(lp);
                    tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pageNumber=1;
                            tv1.setTextColor(getResources().getColor(R.color.live_textColor));
                            tv2.setTextColor(getResources().getColor(R.color.color_text_gray));

                            params.clear();
                            values.clear();

                            params.add("coId");
                            params.add("pageNumber");
                            params.add("pageSize");
                            params.add("groupId");
                            params.add("sort");         //视频分类标准
                            params.add("videoCName");

                            values.add(userInfo.getCoId());
                            values.add(pageNumber);
                            values.add(6);
                            values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                            values.add(videoFlag);
                            values.add("");
                            videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
                        }
                    });
                    tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pageNumber=1;
                            tv2.setTextColor(getResources().getColor(R.color.live_textColor));
                            tv1.setTextColor(getResources().getColor(R.color.color_text_gray));

                            params.clear();
                            values.clear();

                            params.add("coId");
                            params.add("pageNumber");
                            params.add("pageSize");
                            params.add("groupId");
                            params.add("sort");         //视频分类标准
                            params.add("videoCName");

                            values.add(userInfo.getCoId());
                            values.add(pageNumber);
                            values.add(6);
                            values.add(tabPostion==0?"":videoData.get(tabPostion-1).getGroupId());
                            values.add(videoFlag);
                            values.add("");
                            videoFragmentPresenter.getTabData(videoUrl,1,params,values,videoListData,true);
                        }
                    });
                    title2_layout.addView(tv1);
                    title2_layout.addView(tv2,layoutParams);
                }
            }
        }


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
        videoFragmentPresenter.detachView();
    }
}
