package com.szreach.ybolotv.fragment;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.netease.hearttouch.htrefreshrecyclerview.HTLoadMoreListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshListener;
import com.netease.hearttouch.htrefreshrecyclerview.HTRefreshRecyclerView;
import com.szreach.ybolotv.App;
import com.szreach.ybolotv.adapter.VideoListAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.bean.VideoList;
import com.szreach.ybolotv.bean.VideoTitle;
import com.szreach.ybolotv.bean.VideoTitle2;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.utils.mLog;
import com.szreach.ybolotv.utils.mToast;
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

import io.reactivex.disposables.Disposable;
import ybolo.szreach.com.live_vod.R;


public class VideoFragment extends BaseFragment implements View.OnClickListener{


    private SearchView searchView;
    private TabLayout tab_layout;
    private HTRefreshRecyclerView refreshRecyclerView;
    private LinearLayout title2_layout;
    private TextView videoSumHint;
    private TextView videoSort;
    private ImageView arrow;


    private UserInfo userInfo;
    private List<VideoTitle> videoData = new ArrayList<>();
    private int pageNumber=1;
    private int pageSize=6;
    private int totalPage=0;
    private List<VideoList> videoListData=new ArrayList<>();
    private int videoSum=0;
    private VideoListAdapter adapter;
    private Disposable disposable;
    private int tabPostion=0;
    private String videoFlag="video_time";
    private String videoUrl= Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_VIDEO+Interface.URL_POST_VIDEO_LIST_NOGROUP;

    @Override
    protected void HandMsg(int msg) {
        super.HandMsg(msg);
        switch (msg) {
            case 22:
                addTitle();
                break;
            case 33:
                //添加视频数据
                adapter.notifyDataSetChanged();
                //如果某视频标签下没有视频
                if(videoListData.size()==0){
                    refreshRecyclerView.setLoadMoreViewShow(false);
                    refreshRecyclerView.setRefreshCompleted(false);
                    mToast.setToastShort(App.getApplication(),"暂时没有相关视频");
                }
                videoSumHint.setText(Html.fromHtml("共<font color='#3c67ca'>" + videoSum +"</font>个视频"));
                refreshRecyclerView.setRefreshCompleted(true);
                break;
            case 44:
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

    @Override
    protected int setContentView() {
        return R.layout.videofragment_layout;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        userInfo = daoSession.getUserInfoDao().loadAll().get(0);
        searchView=view.findViewById(R.id.video_fragment_searchView);
        tab_layout=view.findViewById(R.id.video_fragment_tab);
        title2_layout=view.findViewById(R.id.video_fragment_addView);
        videoSumHint=view.findViewById(R.id.video_fragment_videoSumHint);
        videoSort=view.findViewById(R.id.video_frgment_videoSort);
        videoSort.setOnClickListener(this);
        videoSort.setTag(true);        //视频排序默认true按最新发布排序
        videoSort.setText("最新发布");
        arrow=view.findViewById(R.id.video_fragment_arrow);
        refreshRecyclerView=view.findViewById(R.id.video_fragment_content);
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
                getTabData(pageNumber,tabPostion==0?"":videoData.get(tabPostion-1).getGroupId(),videoFlag,"",true);
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
                getTabData(pageNumber,tabPostion==0?"":videoData.get(tabPostion-1).getGroupId(),videoFlag,"",true);
                refreshRecyclerView.setRefreshCompleted(true);
            }
        });
        refreshRecyclerView.setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //上拉加载更多
                if(pageNumber<totalPage){
                    pageNumber=pageNumber+1;
                    getTabData(pageNumber,tabPostion==0?"":videoData.get(tabPostion-1).getGroupId(),videoFlag,"",false);
                }else {
                    mToast.setToastShort(App.getApplication(),"没有更多了");
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
                    getTabData(pageNumber,"",videoFlag,query,true);
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
                getTabData(pageNumber,tabPostion==0?"":videoData.get(tabPostion-1).getGroupId(),videoFlag,"",true);
                return false;
            }
        });
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
                            getTabData(pageNumber,tabPostion==0?"":videoData.get(tabPostion-1).getGroupId(),videoFlag,"",true);
                        }
                    });
                    tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pageNumber=1;
                            tv2.setTextColor(getResources().getColor(R.color.live_textColor));
                            tv1.setTextColor(getResources().getColor(R.color.color_text_gray));
                            getTabData(pageNumber,childTitle.getGroupId(),videoFlag,"",true);
                        }
                    });
                    title2_layout.addView(tv1);
                    title2_layout.addView(tv2,layoutParams);
                }
            }
        }


    }

    //postion==0?"":videoData.get(postion-1).getGroupId()
    private void getTabData(int page_number, String groupId, String video_flage, String searchContent, boolean isClear) {
        params.clear();
        objects.clear();
        params.add("coId");
        params.add("pageNumber");
        params.add("pageSize");
        params.add("groupId");
        params.add("sort");         //视频分类标准
        params.add("videoCName");
        objects.add(userInfo.getCoId());
        objects.add(page_number);
        objects.add(pageSize);
        objects.add(groupId);
        objects.add(video_flage);
        objects.add(searchContent);
        getData(videoUrl,RequestMethod.POST,10,params,objects,isClear);
    }

    @Override
    protected void initData() {
        //先从网络获取tablayout的标签
        new Thread(new Runnable() {
            @Override
            public void run() {
                params.clear();
                objects.clear();
                params.add("coId");
                objects.add(userInfo.getCoId());
                String url = Interface.getIpAddress(App.getApplication()) + Interface.URL_PREFIX_VIDEO + Interface.URL_POST_VIDEO_GROUP_LIST;
                getData(url,RequestMethod.POST,6,params,objects,true);
            }
        }).start();
    }

    //获取视频数据
    private void getData(String url, RequestMethod WAYS, int what, List<String> params, List<Object> data, final boolean isClear){
        Request<JSONObject> request=NoHttp.createJsonObjectRequest(url,WAYS);
        request.setDefineRequestBodyForJson(addParams(params,data));
        SendRequest(what, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                switch (what){
                    case 6:
                        try {
                            mLog.e("reponse", "videolist-->" + response.get().toString());
                            JSONObject object = new JSONObject(response.get().toString());
                            JSONObject object1 = object.getJSONObject("msgHeader");
                            if (object1.getBoolean("result")) {
                                JSONArray array = object.getJSONArray("data");
                                if (array.length() > 0) {
                                    videoData.clear();
                                    for (int i = 0; i < array.length(); i++) {
                                        VideoTitle item = gson.fromJson(array.getJSONObject(i).toString(), VideoTitle.class);
                                        videoData.add(item);
                                    }
                                    handler.sendEmptyMessage(22);
                                }
                            } else {
                                Msg = object1.getString("errorInfo");
                                handler.sendEmptyMessage(88);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 10:
                        mLog.e("reponse", "videoData-->" + response.get().toString());
                        try {
                            JSONObject object=new JSONObject(response.get().toString());
                            JSONObject object1=object.getJSONObject("msgHeader");
                            if(object1.getBoolean("result")){
                                JSONObject data=object.getJSONObject("data");
                                totalPage=data.getInt("totalPage");
                                videoSum=data.getInt("totalRecord");
                                JSONArray array=data.getJSONArray("list");
                                if(array.length()>0){
                                    if(isClear){
                                        videoListData.clear();
                                    }
                                    for (int i = 0; i <array.length(); i++) {
                                        VideoList item=gson.fromJson(array.getJSONObject(i).toString(),VideoList.class);
                                        videoListData.add(item);
                                    }
                                    handler.sendEmptyMessage(33);
                                }else {
                                    videoListData.clear();
                                    handler.sendEmptyMessage(33);
                                }
                            }else {
                                Msg=object1.getString("errorInfo");
                                handler.sendEmptyMessage(88);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //视频排序
            case R.id.video_frgment_videoSort:
                if((boolean)videoSort.getTag()){
                    videoSort.setText("最多浏览");
                    videoSort.setTag(false);
                    videoFlag="video_vod";
                    getTabData(pageNumber,tabPostion==0?"":videoData.get(tabPostion-1).getGroupId(),videoFlag,"",true);
                }else {
                    videoSort.setText("最新发布");
                    videoSort.setTag(true);
                    videoFlag="video_time";
                    getTabData(pageNumber,tabPostion==0?"":videoData.get(tabPostion-1).getGroupId(),videoFlag,"",true);
                }
                break;
        }
    }

    @Override
    public void onDetach() {
        if(null!=disposable){
            disposable.dispose();
        }
        super.onDetach();
    }

}
