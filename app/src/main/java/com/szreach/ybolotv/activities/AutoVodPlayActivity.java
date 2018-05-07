/**
 * Adams.Tsui 2017.10.25
 */

package com.szreach.ybolotv.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.views.CustomMediaController;

import java.io.IOException;

public class AutoVodPlayActivity extends AppCompatActivity {

    private KSYTextureView ksyTextureView;
    private TextView hint_tv;
    private CustomMediaController mediaController;
    private String vodPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vod_play_activity);
        vodPath = getIntent().getStringExtra("vodPath");
        initView();
        initData();
    }

    private void initView() {
        ksyTextureView=findViewById(R.id.vod_play_ksplay);
        hint_tv=findViewById(R.id.vod_play_hint);
        mediaController=findViewById(R.id.vod_play_mc);
        mediaController.setVisibility(View.INVISIBLE);

    }

    private void initData() {
        ksyTextureView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                hint_tv.setVisibility(View.GONE);
                ksyTextureView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                ksyTextureView.start();

            }
        });
        //播放完成调用
        ksyTextureView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                ksyTextureView.release();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
        ksyTextureView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                switch (what){
                    case KSYMediaPlayer.MEDIA_ERROR_IO:
                        Toast.makeText(getApplicationContext(),"链接超时，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_UNKNOWN:
                        Toast.makeText(getApplicationContext(),"未知的播放器错误，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        Toast.makeText(getApplicationContext(),"多媒体服务器出错，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_INVALID_DATA:
                        Toast.makeText(getApplicationContext(),"无效的媒体数据，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_TIMED_OUT:
                        Toast.makeText(getApplicationContext(),"操作超时，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_DNS_PARSE_FAILED:
                        Toast.makeText(getApplicationContext(),"DNS解析失败，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_CONNECT_SERVER_FAILED:
                        Toast.makeText(getApplicationContext(),"连接服务器失败，请重试",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        ksyTextureView.setBufferTimeMax(10.0f);
        ksyTextureView.setTimeout(10,30);
        try {
            ksyTextureView.setDataSource(vodPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ksyTextureView.prepareAsync();
        //更新已播放视频的时间
    }


    @Override
    protected void onPause() {
        if(ksyTextureView!=null){
            ksyTextureView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(ksyTextureView!=null){
            ksyTextureView.stop();
            ksyTextureView.release();
        }
        super.onDestroy();
    }
}
