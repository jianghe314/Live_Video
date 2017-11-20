/**
 * Adams.Tsui 2017.10.25
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.utils.UIUtils;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class AutoVodPlayActivity extends Activity {
    private VideoView videoView;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.live_play_activity);

        progressDialog = UIUtils.createDialog(AutoVodPlayActivity.this);
        progressDialog.setCancelable(true);

        String vodPatch = getIntent().getStringExtra("vodPath");
        playLive(vodPatch);
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

    @Override
    protected void onPause() {
        super.onPause();
        if(videoView != null) {
            videoView.stopPlayback();
        }
    }
}
