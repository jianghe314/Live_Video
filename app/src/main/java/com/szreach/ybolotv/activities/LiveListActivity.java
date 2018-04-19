/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.LiveBean;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.utils.UIUtils;
import com.szreach.ybolotv.views.LiveItemView;

import java.io.IOException;
import java.util.ArrayList;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.widget.VideoView;

public class LiveListActivity extends Activity {
    private LinearLayout liveListItems;
    private LiveItemView selectedView;
    private ProgressDialog progressDialog;
    private VideoView videoView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.live_list_activity);
        liveListItems = (LinearLayout) this.findViewById(R.id.live_list_items);

        progressDialog = UIUtils.createDialog(LiveListActivity.this);
        progressDialog.setCancelable(true);
        videoView=findViewById(R.id.live_list_right_video);

        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if (percent >= 98) {
                    progressDialog.dismiss();
                }
                progressDialog.setMessage("加载中..." + percent + "%");
            }
        });

        liveListItems.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (oldFocus != null && oldFocus instanceof LiveItemView) {
                    LiveItemView oldFocusLayout = (LiveItemView) oldFocus;
                    if (oldFocusLayout != selectedView) {
                        oldFocusLayout.setBackgroundColor(0x00ffffff);
                        oldFocusLayout.getLiveflagView().setTextColor(0xff0084fd);
                    }
                }
                if (newFocus != null && newFocus instanceof LiveItemView) {
                    LiveItemView newFocusLayout = (LiveItemView) newFocus;
                    if (newFocusLayout != selectedView) {
//                        newFocusLayout.setBackgroundColor(0xff203040); // 灰色
                        newFocusLayout.setBackgroundColor(0xff0084fd);
                        newFocusLayout.getLiveflagView().setTextColor(0xffffffff);
                        final LiveBean live = newFocusLayout.getLiveInfo();

                        final Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                Bundle bundle = msg.getData();
                                LiveBean liveBean = bundle.getParcelable("live");

                                ((TextView) findViewById(R.id.live_list_right_title)).setText(liveBean.getLiveName());
                                ((TextView) findViewById(R.id.live_list_right_time)).setText(liveBean.getLiveDateTimeStr());
                                ((TextView) findViewById(R.id.live_list_right_count)).setText(String.valueOf(liveBean.getOnLineNum()));
                                ((ImageView) findViewById(R.id.live_list_count_icon)).setVisibility(View.VISIBLE);

                                videoView.setVideoPath(liveBean.getRtmpAddress());
                                progressDialog.show();
                                progressDialog.setMessage("加载中...0%");
                            }
                        };

                        new Thread() {
                            @Override
                            public void run() {
                                long coId = live.getCoId();
                                String liveId = live.getLiveId();
                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("live", DataService.getInstance().getLive(coId, liveId));
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        }.start();
                    }
                }
            }
        });

        new DataThread().start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressDialog.show();
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            ArrayList<LiveBean> liveList = bundle.getParcelableArrayList("liveList");
            ((TextView)LiveListActivity.this.findViewById(R.id.live_list_count)).setText("共计" + liveList.size() + "场直播");
            for (int i = 0; i < liveList.size(); i++) {
                LiveBean liveBean = liveList.get(i);
                LiveItemView item = new LiveItemView(LiveListActivity.this, liveBean);
                liveListItems.addView(item);
                if(i == 0) {
                    item.requestFocus();
                }
            }
        }
    };

    private class DataThread extends Thread {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("liveList", DataService.getInstance().getLiveList());
            msg.setData(bundle);
            handler.sendMessage(msg);
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
