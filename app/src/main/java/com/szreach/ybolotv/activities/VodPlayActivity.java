/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.utils.UIUtils;

import java.util.HashMap;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VodPlayActivity extends Activity {
    private LinearLayout vodVideoCon;
    private VideoView videoView;
    private ProgressDialog progressDialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle data = msg.getData();
        String videoPath = (String) data.getCharSequence("videoPath");
        playVideo(videoPath);
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.vod_play_activity);

        progressDialog = UIUtils.createDialog(VodPlayActivity.this);
        progressDialog.setCancelable(true);

        final MediaController controller = new MediaController(this);
        videoView = this.findViewById(R.id.vod_play_video);
        videoView.setMediaController(controller);

        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (percent >= 98) {
                progressDialog.dismiss();
            }
            progressDialog.setMessage("加载中..." + percent + "%");
            }
        });

        // 播放完毕处理
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.seekTo(0);

                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        videoView.pause();
                    }
                }, 800);
            }
        });

        // 确定键和方向键处理
        vodVideoCon = findViewById(R.id.vod_video_con);
        vodVideoCon.requestFocus();
        vodVideoCon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    if(!controller.isShowing()) {
                        controller.show();
                    } else {
                        if(videoView.isPlaying()) {
                            videoView.pause();
                        } else {
                            videoView.start();
                        }
                    }
                }

                if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    videoView.seekTo(videoView.getCurrentPosition() - 5000);
                }

                if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    videoView.seekTo(videoView.getCurrentPosition() + 5000);
                }
                return false;
            }
        });

        new DataThread(this.getIntent(), this.mHandler).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }

    private void playVideo(String videoPath) {
        videoView.setVideoPath(videoPath);
        progressDialog.show();
    }

    private class DataThread extends Thread {
        private Intent intent;
        private Handler handler;

        public DataThread(Intent intent, Handler mHandler) {
            this.intent = intent;
            this.handler = mHandler;
        }

        @Override
        public void run() {
            Bundle bundle = intent.getExtras();
            long coId = (long) bundle.getSerializable("coId");
            String videoId = (String) bundle.getSerializable("videoId");

            HashMap<String, String> videoPaths = DataService.getInstance().getVideoPlayPath(coId, videoId);
            if (videoPaths != null) {
                String videoPath = videoPaths.get("httpAddrL00");
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putCharSequence("videoPath", videoPath);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }
    }
}
