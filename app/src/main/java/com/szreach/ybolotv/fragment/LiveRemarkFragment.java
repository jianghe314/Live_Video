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
import com.szreach.ybolotv.adapter.LiveRemarkAdapter;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.bean.LiveRemark;
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


public class LiveRemarkFragment extends BaseFragment implements View.OnClickListener{

    private TextView remarkSum;
    private HTRefreshRecyclerView refreshRecyclerView;
    private EditText remarkEdit;
    private Button sendBtn;

    private String liveId;
    private long coId;
    private String userId;
    private LiveRemarkAdapter adapter;
    private int totalRecord=0;
    private int totalPage;
    private int pageNumber=1;
    private List<LiveRemark> liveRemarkData=new ArrayList<>();

    private String sendInfo= Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_LIVE_COMMENT+Interface.URL_POST_LIVE_COMMENT_SUBMIT;
    private String getInfo=Interface.getIpAddress(App.getApplication())+Interface.URL_PREFIX_LIVE_COMMENT+Interface.URL_POST_LIVE_COMMENT;

    @Override
    protected void HandMsg(int msg) {
        super.HandMsg(msg);
        switch (msg){
            case 22:
                adapter.notifyDataSetChanged();
                remarkSum.setText(Html.fromHtml("共<font color='#3c67ca'>"+totalRecord+"</font>条评论"));
                refreshRecyclerView.setRefreshCompleted(true);
                break;
            case 33:
                //刷新评论列表,清空编辑框
                remarkEdit.setText("");
                getFirstData();
                break;
            case 88:
                remarkSum.setText(Html.fromHtml("共<font color='#3c67ca'>"+totalRecord+"</font>条评论"));
                refreshRecyclerView.setRefreshCompleted(false);
                break;
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_live_remark_layout;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Bundle bundle=getArguments();
        liveId=bundle.getString("liveId");
        coId=bundle.getLong("coId");
        userId=bundle.getString("userId");
        remarkSum=view.findViewById(R.id.live_detail_remark_sum);
        refreshRecyclerView=view.findViewById(R.id.live_detail_remark_list);
        remarkEdit=view.findViewById(R.id.live_detail_remark_content);
        sendBtn=view.findViewById(R.id.live_detail_remark_btn);
        sendBtn.setOnClickListener(this);
        adapter=new LiveRemarkAdapter(liveRemarkData);
        refreshRecyclerView.setAdapter(adapter);

        refreshRecyclerView.startAutoRefresh();
        refreshRecyclerView.setAdapter(adapter);
        refreshRecyclerView.setEnableScrollOnRefresh(true);
        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshRecyclerView.setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstData();
            }
        });
        refreshRecyclerView.setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //没有个分页总页数，现在不添加分页
                refreshRecyclerView.setLoadMoreViewShow(false);
                refreshRecyclerView.setRefreshCompleted(false);
            }
        });
    }


    @Override
    protected void initData() {
        getFirstData();
    }


    private void getData(String url, int what, RequestMethod ways, List<String> params, List<Object> data, boolean isClear){
        mLog.e("liveUrl","-->"+url);
        Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, ways);
        request.setDefineRequestBodyForJson(addParams(params,data));
        SendRequest(what, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                mLog.e("reponse","liveRemark-->"+response.get().toString());
                try {
                    JSONObject object=new JSONObject(response.get().toString());
                    JSONObject object1=object.getJSONObject("msgHeader");
                    switch (what){
                        case 15:
                            if(object1.getBoolean("result")){
                                JSONObject data=object.getJSONObject("data");
                                totalRecord=data.getInt("dMax");
                                JSONArray array=data.getJSONArray("list");
                                if(array.length()>0){
                                    liveRemarkData.clear();
                                    for (int i = 0; i <array.length() ; i++) {
                                        LiveRemark item=gson.fromJson(array.getJSONObject(i).toString(),LiveRemark.class);
                                        liveRemarkData.add(item);
                                    }
                                    handler.sendEmptyMessage(22);
                                }else {
                                    Msg="暂时没有评论数据";
                                    handler.sendEmptyMessage(88);
                                }
                            }else {
                                Msg=object1.getString("errorInfo");
                                handler.sendEmptyMessage(88);
                            }
                            break;
                        case 16:
                            if(object1.getBoolean("result")){
                                handler.sendEmptyMessage(33);
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

    private void getFirstData(){
        params.clear();
        objects.clear();
        params.add("max");
        params.add("commId");
        params.add("liveId");
        objects.add("");
        objects.add("");
        objects.add(liveId);
        getData(getInfo,15,RequestMethod.POST,params,objects,false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.live_detail_remark_btn:
                if(!remarkEdit.getText().toString().trim().equals("")){
                    params.clear();
                    objects.clear();
                    params.add("coId");
                    params.add("liveId");
                    params.add("userId");
                    params.add("commContent");
                    objects.add(coId);
                    objects.add(liveId);
                    objects.add(userId);
                    objects.add(remarkEdit.getText().toString().trim());
                    getData(sendInfo,16,RequestMethod.POST,params,objects,false);
                }else {
                    mToast.setToastShort(App.getApplication(),"评论内容不能为空哦^_^");
                }
                break;
        }
    }
}
