/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.LiveBean;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.utils.UIUtils;
import com.szreach.ybolotv.views.CustomMediaController;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class LivePlayActivity extends AppCompatActivity implements CustomMediaController.StartOrStopListener,CustomMediaController.SetOnSeekBarListener,View.OnTouchListener{

    private KSYTextureView ksyTextureView;
    private CustomMediaController mediaController;
    private TextView hint_tv;
    private long duartion;
    private boolean isSeeking=false;

    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 00:
                    //更新时间
                    mediaController.setHasLoadTime(ksyTextureView.getCurrentPosition());
                    //更新seekbar

                    if(duartion>0&&ksyTextureView.getCurrentPosition()>0){
                        float aa=(float) ksyTextureView.getCurrentPosition();
                        float bb=(float) duartion;
                        float cc=aa/bb*100f;
                        mediaController.setProgress((int) cc);
                    }

                    break;
                case 11:
                    synchronized (CustomMediaController.class){
                        if((boolean)mediaController.getTag()){
                            mediaController.setVisibility(View.INVISIBLE);
                            mediaController.setTag(false);
                        }
                    }
                    break;
                case 33:
                    Bundle bundle = msg.getData();
                    String livePath = (String) bundle.getSerializable("livePath");
                    playLive(livePath);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_play_activity);
        initView();
        initData();
    }


    private void initView() {
        ksyTextureView=findViewById(R.id.live_play_video);
        mediaController=findViewById(R.id.live_play_mc);
        hint_tv=findViewById(R.id.live_play_hint);
        mediaController.setPlayOrStop(this);
        mediaController.setSeekBarProgress(this);
        ksyTextureView.setOnTouchListener(this);
        mediaController.setTag(true);
        new DataThread(this.getIntent(), this.handler).start();
    }

    private class DataThread extends Thread {
        private Intent intent;
        private Handler handler;

        private DataThread(Intent intent, Handler handler) {
            this.intent = intent;
            this.handler = handler;
        }

        @Override
        public void run() {
            Bundle data = intent.getExtras();
            long coId = (long) data.getSerializable("coId");
            String liveId = (String) data.getSerializable("liveId");

            Message msg = new Message();
            Bundle bundle = new Bundle();

            LiveBean liveBean = DataService.getInstance().getLive(coId, liveId);
            if(liveBean != null) {
                bundle.putSerializable("livePath", liveBean.getRtmpAddress());
                msg.setData(bundle);
                msg.what=33;
                handler.sendMessage(msg);
            }
        }
    }

    private void playLive(String livePath) {
        //http://video.ybolo.com/10001/16cd290f1c39415c9b8704e878412693/SD/resource/videos/1_1.mp4
        //rtmp://40.125.202.122/live/10001-002-L180420
        try {
            ksyTextureView.setDataSource(livePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ksyTextureView.prepareAsync();
    }


    private void initData() {
        ksyTextureView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                duartion=ksyTextureView.getDuration();
                mediaController.setTotalTime(duartion);
                hint_tv.setVisibility(View.GONE);
                mediaController.setProgress(0);
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

        //更新已播放视频的时间

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isSeeking){
                    handler.sendEmptyMessage(00);
                }
            }
        },0,1000);
        setMediaControllerVisible();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if((boolean)mediaController.getTag()){
            mediaController.setVisibility(View.INVISIBLE);
            mediaController.setTag(false);
        }else {
            mediaController.setVisibility(View.VISIBLE);
            mediaController.setTag(true);
            //5秒钟后控制栏隐藏
            setMediaControllerVisible();
        }
        return false;
    }

    @Override
    public void startOrstopListener(ImageView control) {
        if((boolean)control.getTag()){
            ksyTextureView.pause();
        }else{
            ksyTextureView.start();
        }
    }

    @Override
    public void onSeekBarListener(SeekBar seekBar, int progress) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double mprogress=progress/100d;
                double seekPostion=(double) duartion*mprogress;
                Log.e("mprogress","-->"+mprogress);
                Log.e("seekPostion","-->"+(long)seekPostion);
                ksyTextureView.seekTo((long)seekPostion,true);
                mediaController.setHasLoadTime(ksyTextureView.getCurrentPosition());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaController.setTag(false);
                isSeeking=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaController.setTag(true);
                setMediaControllerVisible();
                isSeeking=false;
            }
        });
    }


    //隐藏mc
    private void setMediaControllerVisible(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(11);
            }
        },8000);
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
