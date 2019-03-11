package com.szreach.ybolotv.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edge.pcdn.PcdnManager;
import com.edge.pcdn.PcdnType;
import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.adapter.FragmentAdapter;
import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.db.UserInfoDao;
import com.szreach.ybolotv.fragment.LiveIntroFragment;
import com.szreach.ybolotv.fragment.LiveRemarkFragment;
import com.szreach.ybolotv.player.YboloTvPlayer;
import com.szreach.ybolotv.presenter.LiveDetailPresenter;
import com.szreach.ybolotv.utils.mLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiveDetailActivity extends BaseActivity implements MVPView{
    //E527JU

    @BindView(R.id.live_detail_back)
    ImageView live_back;
    @BindView(R.id.live_share)
    ImageView live_share;
    @BindView(R.id.live_detail_title)
    RelativeLayout liveDetailTitle;
    @BindView(R.id.ybolo_player)
    YboloTvPlayer yboloPlayer;
    @BindView(R.id.live_detail_content)
    FrameLayout liveDetailContent;
    @BindView(R.id.live_detail_intro)
    TextView liveDetailIntro;
    @BindView(R.id.live_detail_intro_container)
    RelativeLayout introContainer;
    @BindView(R.id.live_detail_evalu)
    TextView liveDetailEvalu;
    @BindView(R.id.live_detail_remark_container)
    RelativeLayout remarkContainer;
    @BindView(R.id.live_detail_view_pager)
    ViewPager viewPager;
    @BindView(R.id.gl_surface_view)
    GLSurfaceView gl_surface_view;

    private String liveId;
    private LiveDetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
        ButterKnife.bind(this);

        liveId = getIntent().getStringExtra("liveId");
        yboloPlayer.setBufferSize(30);
        yboloPlayer.setMaxBufferTimeSize(10f);
        yboloPlayer.setTimeOut(10, 10);
        //添加视频简介和评价
        List<BaseFragment> fragmentList=new ArrayList<>();
        LiveIntroFragment introFragment=new LiveIntroFragment();
        LiveRemarkFragment remarkFragment=new LiveRemarkFragment();
        Bundle bundle=new Bundle();
        bundle.putString("liveId",liveId);
        introFragment.setArguments(bundle);
        remarkFragment.setArguments(bundle);
        fragmentList.add(introFragment);
        fragmentList.add(remarkFragment);
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(1);
        setColor(liveDetailEvalu,liveDetailIntro);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setColor(liveDetailEvalu,liveDetailIntro);
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        setColor(liveDetailIntro,liveDetailEvalu);
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });

        detailPresenter=new LiveDetailPresenter();
        detailPresenter.attachView(this);
        UserInfo userInfo=MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);
        parama_values.clear();
        parama_values.put("userId",userInfo.getUserId());
        parama_values.put("coId",userInfo.getCoId());
        parama_values.put("userName",userInfo.getUserName());
        parama_values.put("liveId",liveId);

        detailPresenter.getLivePath(parama_values);
    }

    @Override
    public void showData(Object data) {
        String url=PcdnManager.PCDNAddress(PcdnType.LIVE,(String) data);
        mLog.e("直播地址","-->"+url);
        yboloPlayer.setDataSources(url);
        //yboloPlayer.setDataSources("udp://172.16.32.234:8889");
    }

    @OnClick({R.id.live_detail_back, R.id.live_share, R.id.live_detail_intro_container, R.id.live_detail_remark_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.live_detail_back:
                finish();
                break;
            case R.id.live_share:

                break;
            case R.id.live_detail_intro_container:
                setColor(liveDetailEvalu,liveDetailIntro);
                viewPager.setCurrentItem(0);
                break;
            case R.id.live_detail_remark_container:
                setColor(liveDetailIntro,liveDetailEvalu);
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        yboloPlayer.setDataSources("");
    }

    private void setColor(TextView tv1, TextView tv2){
        tv1.setTextColor(getResources().getColor(R.color.color_black_gray));
        tv2.setTextColor(getResources().getColor(R.color.live_textColor));
    }

    @Override
    protected void onPause() {
        super.onPause();
        yboloPlayer.OnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yboloPlayer.OnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yboloPlayer.OnDestroy();
        detailPresenter.detachView();
    }
}
