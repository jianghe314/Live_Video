package com.szreach.ybolotv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.LiveInfo;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.presenter.LiveDetailIntroPresenter;
import com.szreach.ybolotv.presenter.LiveDetailPresenter;
import com.szreach.ybolotv.utils.mLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZX on 2018/9/20
 */
public class LiveIntroFragment extends BaseFragment implements MVPView{

    private TextView title,time,person,describe;
    private ImageView person_iv;
    private String videoId;
    private UserInfo userInfo;
    private List<LiveInfo> liveInfo=new ArrayList<>();
    private LiveDetailIntroPresenter introPresenter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_live_intro_layout;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle=getArguments();
        videoId=bundle.getString("liveId");
        title=view.findViewById(R.id.live_detail_title);
        time=view.findViewById(R.id.live_detail_time);
        person=view.findViewById(R.id.live_detail_person);
        describe=view.findViewById(R.id.live_detail_describe);
        person_iv=view.findViewById(R.id.live_detail_person_iv);
    }


    @Override
    protected void loadData() {
        userInfo= MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);
        introPresenter=new LiveDetailIntroPresenter();
        introPresenter.attachView(this);
        params.clear();
        values.clear();
        params.add("coId");
        params.add("liveId");
        values.add(userInfo.getCoId());
        values.add(videoId);
        introPresenter.getLiveIntro(params,values,liveInfo);
    }


    @Override
    public void showData(Object data) {
        title.setText(liveInfo.get(0).getLiveName());
        time.setText(liveInfo.get(0).getLiveStart()+"-"+liveInfo.get(0).getLiveEnd());
        person.setText(liveInfo.get(0).getOnlineCount()+"");
        describe.setText(liveInfo.get(0).getLiveName()+","+liveInfo.get(0).getChannelName());
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
        introPresenter.detachView();
    }
}
