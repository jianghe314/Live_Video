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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.LiveBean;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.utils.UIUtils;
import com.szreach.ybolotv.views.LiveItemView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LiveListActivity extends Activity {
    private LinearLayout liveListItems;
    private LiveItemView selectedView;
    private ProgressDialog progressDialog;

    private KSYTextureView ksyTextureView;
    private long duartion;
    private boolean isSeeking=false;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 33:
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
                    break;
                case 44:
                    Bundle mbundle = msg.getData();
                    LiveBean liveBean = mbundle.getParcelable("live");

                    ((TextView) findViewById(R.id.live_list_right_title)).setText(liveBean.getLiveName());
                    ((TextView) findViewById(R.id.live_list_right_time)).setText(liveBean.getLiveDateTimeStr());
                    ((TextView) findViewById(R.id.live_list_right_count)).setText(String.valueOf(liveBean.getOnLineNum()));
                    ((ImageView) findViewById(R.id.live_list_count_icon)).setVisibility(View.VISIBLE);

                    //videoView.setVideoPath(liveBean.getRtmpAddress());
                    try {
                        ksyTextureView.setDataSource(liveBean.getRtmpAddress());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ksyTextureView.prepareAsync();
                    progressDialog.show();
                    progressDialog.setMessage("加载中...");
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.live_list_activity);
        liveListItems = (LinearLayout) this.findViewById(R.id.live_list_items);
        ksyTextureView=findViewById(R.id.live_list_right_video);

        progressDialog = UIUtils.createDialog(LiveListActivity.this);
        progressDialog.setCancelable(true);

        ksyTextureView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                duartion=ksyTextureView.getDuration();
                ksyTextureView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                ksyTextureView.start();
                progressDialog.dismiss();

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

                        new Thread() {
                            @Override
                            public void run() {
                                long coId = live.getCoId();
                                String liveId = live.getLiveId();
                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("live", DataService.getInstance().getLive(coId, liveId));
                                msg.setData(bundle);
                                msg.what=44;
                                handler.sendMessage(msg);
                            }
                        }.start();
                    }
                }
            }
        });

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

        new DataThread().start();
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
    protected void onRestart() {
        super.onRestart();
        progressDialog.show();
    }





    private class DataThread extends Thread {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("liveList", DataService.getInstance().getLiveList());
            msg.setData(bundle);
            msg.what=33;
            handler.sendMessage(msg);
        }
    }

    public KSYTextureView getVideoView() {
        return ksyTextureView;
    }

    public void setVideoView(KSYTextureView videoView) {
        this.ksyTextureView = videoView;
    }

    public LiveItemView getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(LiveItemView selectedView) {
        this.selectedView = selectedView;
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
