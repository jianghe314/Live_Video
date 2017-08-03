/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.LiveBean;
import com.szreach.ybolotvbox.utils.UIUtils;
import com.szreach.ybolotvbox.views.LiveItemView;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class LiveListActivity extends Activity {
    private LinearLayout liveListItems;
    private LiveItemView selectedView;
    private VideoView videoView;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.live_list_activity);
        liveListItems = (LinearLayout) this.findViewById(R.id.live_list_items);

        progressDialog = UIUtils.createDialog(LiveListActivity.this);
        progressDialog.setCancelable(true);

        videoView = (VideoView) this.findViewById(R.id.live_list_right_video);
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if (percent >= 98) {
                    progressDialog.dismiss();
                }
                progressDialog.setMessage("加载中..." + percent + "%");
            }
        });

        this.addItem();

        liveListItems.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (oldFocus != null && oldFocus != videoView) {
                    LiveItemView oldFocusLayout = (LiveItemView) oldFocus;
                    if (oldFocusLayout != selectedView) {
                        oldFocusLayout.setBackgroundColor(0x00ffffff);
                        oldFocusLayout.getLiveflagView().setTextColor(0xff0084fd);
                    }
                }
                if (newFocus != null && newFocus != videoView) {
                    LiveItemView newFocusLayout = (LiveItemView) newFocus;
                    if (newFocusLayout != selectedView) {
//                        newFocusLayout.setBackgroundColor(0xff203040); // 灰色
                        newFocusLayout.setBackgroundColor(0xff0084fd);
                        newFocusLayout.getLiveflagView().setTextColor(0xffffffff);
                        LiveBean live = newFocusLayout.getLiveInfo();
                        ((TextView) findViewById(R.id.live_list_right_title)).setText(live.getLiveName());
                        ((TextView) findViewById(R.id.live_list_right_count)).setText(String.valueOf(live.getOnLine()));
                        ((TextView) findViewById(R.id.live_list_right_time)).setText(live.getLiveDateTimeStr());
                        videoView.setVideoPath(live.getLn01());
                        progressDialog.show();
                        progressDialog.setMessage("加载中...0%");
                    }
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressDialog.show();
    }

    private void addItem() {
        for (int i = 0; i < 20; i++) {
            LiveBean live = new LiveBean();
            if (i % 2 == 0) {
                live.setLiveName("白山市市长对邮政业支持跨境电商发展提出具体要求");
                live.setLiveStart("2017/05/06 09:01");
                live.setLiveEnd("2017/05/06 11:21");
                live.setOnLine(3658);

            } else {
                live.setLiveName("吉林省局检查组到辽源市开展检查");
                live.setLiveStart("2017/05/06 09:01");
                live.setLiveEnd("2017/05/06 21:01");
                live.setOnLine(3658);
            }
            if (i % 3 == 0) {
                live.setLiveFlag(1);
                // live.setLn01("rtmp://live.hkstv.hk.lxdns.com/live/hks");
                live.setLn01("rtmp://120.24.180.127/live/10031-002-L");
                if(i % 6 == 0) {
                    live.setLn01("rtmp://120.24.180.127/live/10031-001-L");
                }
            }

            LiveItemView item = new LiveItemView(this, live);
            liveListItems.addView(item);
        }
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }

    public LiveItemView getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(LiveItemView selectedView) {
        this.selectedView = selectedView;
    }
}
