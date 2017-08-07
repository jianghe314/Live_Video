/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.utils.DataService;
import com.szreach.ybolotvbox.utils.UIUtils;

import java.util.HashMap;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VodPlayActivity extends Activity {
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

        setContentView(R.layout.live_play_activity);

        progressDialog = UIUtils.createDialog(VodPlayActivity.this);
        progressDialog.setCancelable(true);
        new DataThread(this.getIntent(), this.mHandler).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(videoView != null) {
            videoView.stopPlayback();
        }
    }

    private void playVideo(String videoPath) {
        videoView = (VideoView) this.findViewById(R.id.live_play_video);
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if (percent >= 98) {
                    progressDialog.dismiss();
                }
                progressDialog.setMessage("加载中..." + percent + "%");
            }
        });
        videoView.setMediaController(new MediaController(this));
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
