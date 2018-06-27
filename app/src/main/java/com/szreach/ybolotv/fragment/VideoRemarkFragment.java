package com.szreach.ybolotv.fragment;

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
import com.szreach.ybolotv.App;
import com.szreach.ybolotv.adapter.VideoRemarkAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.bean.VideoRemark;
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

import ybolo.szreach.com.live_vod.R;


public class VideoRemarkFragment extends BaseFragment implements View.OnClickListener{

    private TextView remarkSum;
    private HTRefreshRecyclerView remarkList;
    private EditText remarkEdit;
    private Button sendBtn;

    private String videoId;
    private long coId;
    private String userId;
    private List<VideoRemark> remarkData=new ArrayList<>();
    private VideoRemarkAdapter adapter;
    private int totalRecord;
    private int totalPage;
    private int pageNumber=1;

    @Override
    protected void HandMsg(int msg) {
        super.HandMsg(msg);
        switch (msg){
            case 22:
                adapter.notifyDataSetChanged();
                remarkSum.setText(Html.fromHtml("共<font color='#3c67ca'>"+totalRecord+"</font>条评论"));
                remarkList.setRefreshCompleted(true);
                break;
            case 33:
                adapter.notifyDataSetChanged();
                remarkSum.setText(Html.fromHtml("共<font color='#3c67ca'>"+0+"</font>条评论"));
                mToast.setToastShort(App.getApplication(),"暂无相关评论");
                remarkList.setLoadMoreViewShow(false);
                remarkList.setRefreshCompleted(false);
                break;
            case 44:
                remarkEdit.setText("");
                getFirstData();
                break;
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_video_remark_layout;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Bundle bundle=getArguments();
        videoId=bundle.getString("videoId");
        coId=bundle.getLong("coId");
        userId=bundle.getString("userId");
        remarkSum=view.findViewById(R.id.video_detail_remark_sum);
        remarkList=view.findViewById(R.id.video_detail_remark_list);
        remarkEdit=view.findViewById(R.id.video_detail_remark_content);
        sendBtn=view.findViewById(R.id.video_detail_remark_btn);
        sendBtn.setOnClickListener(this);
        adapter=new VideoRemarkAdapter(remarkData);

        remarkList.startAutoRefresh();
        remarkList.setAdapter(adapter);
        remarkList.setEnableScrollOnRefresh(true);
        remarkList.setLayoutManager(new LinearLayoutManager(getActivity()));
        remarkList.setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstData();
            }
        });
        remarkList.setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(pageNumber<totalPage){
                    pageNumber=pageNumber+1;
                    params.add("commentPageSize");
                    params.add("commentPageNum");
                    params.add("videoId");
                    params.add("coId");
                    objects.add(6);
                    objects.add(pageNumber);
                    objects.add(videoId);
                    objects.add(coId);
                    String url= Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_VIDEO_COMMENT+Interface.URL_POST_VIDEO_COMMENT;
                    getData(12,url,RequestMethod.POST,params,objects,false);
                }else {
                    mToast.setToastShort(App.getApplication(),"没有更多了");
                    remarkList.setLoadMoreViewShow(false);
                    remarkList.setRefreshCompleted(false);
                }
            }
        });
        getFirstData();
    }



    private void getFirstData(){
        pageNumber=1;
        params.clear();
        objects.clear();
        params.add("commentPageSize");
        params.add("commentPageNum");
        params.add("videoId");
        params.add("coId");
        objects.add(6);
        objects.add(pageNumber);
        objects.add(videoId);
        objects.add(coId);
        String url= Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_VIDEO_COMMENT+Interface.URL_POST_VIDEO_COMMENT;
        getData(12,url,RequestMethod.POST,params,objects,true);
    }

    private void getData(int what, String url, RequestMethod ways, List<String> params, List<Object> data, final boolean isClear){
        Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, ways);
        request.setDefineRequestBodyForJson(addParams(params,data));
        SendRequest(what, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                mLog.e("reponse","remark-->"+response.get().toString());
                try {
                    JSONObject object=new JSONObject(response.get().toString());
                    JSONObject object1=object.getJSONObject("msgHeader");
                    switch (what){
                        case 12:
                            if(object1.getBoolean("result")){
                                JSONObject data=object.getJSONObject("data");
                                totalRecord=data.getInt("totalRecord");
                                totalPage=data.getInt("totalPage");
                                JSONArray array=data.getJSONArray("recordList");
                                if(array.length()>0){
                                    if(isClear){
                                        remarkData.clear();
                                    }
                                    for (int i = 0; i <array.length(); i++) {
                                        VideoRemark item=gson.fromJson(array.getJSONObject(i).toString(),VideoRemark.class);
                                        remarkData.add(item);
                                    }
                                    handler.sendEmptyMessage(22);
                                }else {
                                    handler.sendEmptyMessage(33);
                                }
                            }else {
                                Msg=object1.getString("errorInfo");
                                handler.sendEmptyMessage(88);
                            }
                            break;
                        case 13:
                            if(object1.getBoolean("result")){
                                //评论成功，刷新列表，清空编辑框
                                handler.sendEmptyMessage(44);
                            }else {
                                Msg=object1.getString("errorInfo");
                                handler.sendEmptyMessage(88);
                            }
                            break;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video_detail_remark_btn:
                if(!remarkEdit.getText().toString().trim().equals("")){
                    params.clear();
                    objects.clear();
                    params.add("coId");
                    params.add("videoId");
                    params.add("commContent");
                    objects.add(coId);
                    objects.add(videoId);
                    objects.add(remarkEdit.getText().toString().trim());
                    if(null==userId||userId.equals("")){
                        params.add("userId");
                        params.add("userName");
                        objects.add("-1");
                        objects.add("游客");
                    }else {
                        params.add("userId");
                        objects.add(userId);
                    }
                    String url=Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_VIDEO_COMMENT+Interface.URL_POST_VIDEO_COMMENT_SUBMIT;
                    getData(13,url,RequestMethod.POST,params,objects,false);

                }else {
                    mToast.setToastShort(App.getApplication(),"内容不能为空哦^_^");
                }
                break;
        }
    }
}
