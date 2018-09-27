package com.szreach.ybolotv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.player.YboloTvPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoDetailActivity extends BaseActivity {


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
    @BindView(R.id.video_fragment_frame_layout)
    FrameLayout videoFragmentFrameLayout;

    private String videoId;
    private long coId;
    private UserInfo userInfo;

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

                break;
            case R.id.video_fragment_remark_container:

                break;
        }
    }
}
