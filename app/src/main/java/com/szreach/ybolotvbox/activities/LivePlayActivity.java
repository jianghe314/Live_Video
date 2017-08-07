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
import android.util.Log;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.LiveBean;
import com.szreach.ybolotvbox.utils.DataService;
import com.szreach.ybolotvbox.utils.UIUtils;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class LivePlayActivity extends Activity {
    private VideoView videoView;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String livePath = (String) bundle.getSerializable("livePath");
            playLive(livePath);
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.live_play_activity);

        progressDialog = UIUtils.createDialog(LivePlayActivity.this);
        progressDialog.setCancelable(true);
        new DataThread(this.getIntent(), this.handler).start();
    }

    private void playLive(String livePath) {
        videoView = (VideoView)this.findViewById(R.id.live_play_video);
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if(percent >= 98) {
                    progressDialog.dismiss();
                }
                progressDialog.setMessage("加载中..." + percent + "%");
            }
        });

        videoView.setVideoPath(livePath);
        progressDialog.show();
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
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(videoView != null) {
            videoView.stopPlayback();
        }
    }
}
