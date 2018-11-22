package com.szreach.ybolotv.activity;

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
import com.szreach.ybolotv.fragment.VideoIntroFragment;
import com.szreach.ybolotv.fragment.VideoRemarkFragment;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.player.YboloTvPlayer;
import com.szreach.ybolotv.presenter.VideoDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoDetailActivity extends BaseActivity implements MVPView{


    @BindView(R.id.video_detail_back)
    ImageView videoDetailBack;
    @BindView(R.id.video_share)
    ImageView videoShare;
    @BindView(R.id.ybolo_player_video)
    YboloTvPlayer yboloPlayerVideo;
    @BindView(R.id.video_detail_intro)
    TextView videoDetailIntro;
    @BindView(R.id.video_fragment_intro_container)
    RelativeLayout videoFragmentIntroContainer;
    @BindView(R.id.video_detail_evalu)
    TextView videoDetailEvalu;
    @BindView(R.id.video_fragment_remark_container)
    RelativeLayout videoFragmentRemarkContainer;
    @BindView(R.id.video_viewpager_layout)
    ViewPager viewPager;

    private String videoId;
    private long coId;
    private UserInfo userInfo;
    private String video_url= Interface.getIpAddress(MyApplication.getApplication()) + Interface.URL_PREFIX_VIDEO_PLAY + Interface.URL_POST_VIDEO_PLAY;
    private List<BaseFragment> fragmentList=new ArrayList<>();

    private VideoDetailPresenter videoDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        videoId=getIntent().getStringExtra("videoId");
        coId=getIntent().getLongExtra("coId",0l);
        userInfo= MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);

        yboloPlayerVideo.setBufferSize(30);
        yboloPlayerVideo.setMaxBufferTimeSize(10f);
        yboloPlayerVideo.setTimeOut(10, 10);
        videoDetailPresenter=new VideoDetailPresenter();
        videoDetailPresenter.attachView(this);

        fragmentList.clear();
        VideoIntroFragment videoIntroFragment=new VideoIntroFragment();
        VideoRemarkFragment videoRemarkFragment=new VideoRemarkFragment();
        Bundle bundle=new Bundle();
        bundle.putString("videoId",videoId);
        videoIntroFragment.setArguments(bundle);
        videoRemarkFragment.setArguments(bundle);
        fragmentList.add(videoIntroFragment);
        fragmentList.add(videoRemarkFragment);
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        ClearAndSetColor(videoDetailIntro,videoDetailEvalu);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        ClearAndSetColor(videoDetailIntro,videoDetailEvalu);
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        ClearAndSetColor(videoDetailEvalu,videoDetailIntro);
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });
        parama_values.clear();
        parama_values.put("userId",userInfo.getUserId());
        parama_values.put("coId",coId);
        parama_values.put("videoId",videoId);
        videoDetailPresenter.getVideoUrl(video_url,parama_values);
    }


    @Override
    public void showData(Object data) {
        String url= PcdnManager.PCDNAddress(PcdnType.VOD,(String) data);
        yboloPlayerVideo.setDataSources(url);
    }

    @OnClick({R.id.video_detail_back, R.id.video_share, R.id.video_fragment_intro_container, R.id.video_fragment_remark_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.video_detail_back:
                finish();
                break;
            case R.id.video_share:

                break;
            case R.id.video_fragment_intro_container:
                viewPager.setCurrentItem(0);
                break;
            case R.id.video_fragment_remark_container:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        yboloPlayerVideo.setDataSources("");
    }

    private void ClearAndSetColor(TextView tv1, TextView tv2){
        tv1.setTextColor(getResources().getColor(R.color.live_textColor));
        tv2.setTextColor(getResources().getColor(R.color.color_black_gray));
    }

    @Override
    protected void onStop() {
        super.onStop();
        yboloPlayerVideo.OnStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        yboloPlayerVideo.OnPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yboloPlayerVideo.OnDestroy();
        videoDetailPresenter.detachView();
    }
}
