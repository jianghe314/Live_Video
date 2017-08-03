/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.LiveBean;
import com.szreach.ybolotvbox.utils.UIUtils;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class LivePlayActivity extends Activity {
    private VideoView videoView;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.live_play_activity);

        progressDialog = UIUtils.createDialog(LivePlayActivity.this);
        progressDialog.setCancelable(true);

        Intent intent = this.getIntent();
        LiveBean live = (LiveBean) intent.getSerializableExtra("live");

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
        videoView.setVideoPath(live.getLn01());
//        videoView.setVideoPath("rtmp://live.hkstv.hk.lxdns.com/live/hks");
        progressDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.stopPlayback();
    }
}
