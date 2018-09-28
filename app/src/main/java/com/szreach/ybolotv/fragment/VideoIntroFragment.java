package com.szreach.ybolotv.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.bean.VideoInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.presenter.VideoDetailIntroPresenter;

/**
 * Created by ZX on 2018/9/28
 */
public class VideoIntroFragment extends BaseFragment implements MVPView {

    private TextView title,time,person,describe;
    private String videoId;
    private UserInfo userInfo;
    private String url= Interface.getIpAddress(MyApplication.getApplication())+Interface.URL_PREFIX_VIDEO+Interface.URL_POST_VIDEO_INFO;

    private VideoDetailIntroPresenter introPresenter;


    @Override
    protected int setContentView() {
        return R.layout.fragment_video_intro_layout;
    }

    @Override
    protected void initView(View view) {
        videoId=getArguments().getString("videoId");
        title=view.findViewById(R.id.video_detail_title);
        time=view.findViewById(R.id.video_detail_time);
        person=view.findViewById(R.id.video_detail_person);
        describe=view.findViewById(R.id.video_detail_describe);
        userInfo=MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);
        introPresenter=new VideoDetailIntroPresenter();
        introPresenter.attachView(this);
    }

    @Override
    protected void stopLoad() {

    }

    @Override
    protected void loadData() {
        params.clear();
        values.clear();
        params.add("coId");
        params.add("vodId");
        values.add(userInfo.getCoId());
        values.add(videoId);
        introPresenter.getVideoIntro(url,params,values);
    }

    @Override
    public void showData(Object data) {
        VideoInfo videoInfo= (VideoInfo) data;
        title.setText(videoInfo.getVideoCname());
        time.setText(videoInfo.getVideoTime());
        person.setText(videoInfo.getVideoVod()+"");
        describe.setText(videoInfo.getVideoDesc());
    }

    @Override
    public Context getContent() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        introPresenter.detachView();
    }
}
