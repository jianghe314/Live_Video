/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.VideoBean;
import com.szreach.ybolotvbox.utils.UIUtils;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VodPlayActivity extends Activity {
    private VideoView videoView;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.live_play_activity);

        progressDialog = UIUtils.createDialog(VodPlayActivity.this);
        progressDialog.setCancelable(true);

        Intent intent = this.getIntent();
        VideoBean video = (VideoBean) intent.getSerializableExtra("video");

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
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoPath("");
        progressDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.stopPlayback();
    }
}
