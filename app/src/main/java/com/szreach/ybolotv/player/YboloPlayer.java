package com.szreach.ybolotv.player;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ybolo.szreach.com.live_vod.R;

/**
 * 还要添加window类，设置当播放视频时屏幕常亮，播放完毕清除常亮标志
 */
public class YboloPlayer extends RelativeLayout implements View.OnClickListener,SeekBar.OnSeekBarChangeListener,View.OnTouchListener{

    //背景颜色
    private int backColor;
    //提示文字颜色
    private int hintColor;
    //视频时间提示颜色
    private int timeColor;
    //seekBar的颜色
    private int seekBarColor;
    //媒体工具栏的颜色
    private int toolBgColor;
    //正在加载时的提示文字大小，像素为单位
    private int hintTextSize;
    //视频时间文字提示大小，像素为单位
    private int timeTextSize;
    //播放按钮的大小
    private int controlSize;
    //是否点播
    private boolean isLive;


    //加载时的提示文字
    private TextView hintText;
    //视频已播放时间
    private TextView loadTime;
    //视频总时间
    private TextView totalTime;
    //暂停播放按钮
    private ImageView controlIv;
    //滑动seekbar
    private SeekBar seekBar;
    //媒体控制工具栏的容器
    private RelativeLayout control_layout;
    //播放器
    private KSYTextureView ksyTextureView;


    private long duartion;
    private Context context;
    private Timer timer;
    private ProgressBar progressBar;
    //当滑动时，不隐藏媒体栏
    private boolean isSeeking=false;


    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 00:
                    //刷新已播视频时间和更新seekBar
                    loadTime.setText(ComputerTime(ksyTextureView.getCurrentPosition()));
                    if(duartion>0&&ksyTextureView.getCurrentPosition()>0){
                        float aa=(float) ksyTextureView.getCurrentPosition();
                        float bb=(float) duartion;
                        float cc=aa/bb*100f;
                        seekBar.setProgress((int) cc);
                    }
                    break;
                case 11:
                    //隐藏媒体栏
                    if(!isSeeking){
                        if((boolean)control_layout.getTag()){
                            control_layout.setVisibility(INVISIBLE);
                            /*
                            Animator animation_out= AnimatorInflater.loadAnimator(context,R.animator.translate_out);
                            animation_out.setTarget(control_layout);
                            animation_out.start();
                            */
                            control_layout.setTag(false);
                            ksyTextureView.setTag(true);
                        }
                    }
                    break;
            }
        }
    };


    public YboloPlayer(Context context) {
        this(context,null);
    }

    public YboloPlayer(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public YboloPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.YboloPlayer);
        backColor=array.getColor(R.styleable.YboloPlayer_yboloPlayer_backColor, Color.parseColor("#585757"));
        hintColor=array.getColor(R.styleable.YboloPlayer_yboloPlayer_hintColor, Color.parseColor("#ffffff"));
        timeColor=array.getColor(R.styleable.YboloPlayer_yboloPlayer_timeColor, Color.parseColor("#ffffff"));
        seekBarColor=array.getColor(R.styleable.YboloPlayer_yboloPlayer_seekBarColor, Color.parseColor("#FF4081"));
        toolBgColor=array.getColor(R.styleable.YboloPlayer_yboloPlayer_toolColor, Color.parseColor("#8a4e4d4d"));
        hintTextSize=array.getDimensionPixelSize(R.styleable.YboloPlayer_yboloPlayer_hintSize,5);
        timeTextSize=array.getDimensionPixelSize(R.styleable.YboloPlayer_yboloPlayer_timeSize,8);
        controlSize=array.getDimensionPixelSize(R.styleable.YboloPlayer_yboloPlayer_btnSize,30);
        isLive=array.getBoolean(R.styleable.YboloPlayer_yboloPlayer_isLive,false);       //默认点播，显示媒体工具栏
        array.recycle();
        initView(context);
        initData();

    }

    private void initView(Context context) {
        this.setBackgroundColor(backColor);

        ksyTextureView=new KSYTextureView(context);
        ksyTextureView.setOnTouchListener(this);
        ksyTextureView.setTag(false);
        ksyTextureView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(ksyTextureView);

        progressBar=new ProgressBar(context);
        progressBar.setVisibility(VISIBLE);
        LayoutParams rl0=new LayoutParams(80, 80);
        rl0.addRule(CENTER_IN_PARENT);
        this.addView(progressBar,rl0);


        hintText=new TextView(context);
        hintText.setVisibility(GONE);
        hintText.setFocusable(true);
        hintText.setTextColor(hintColor);
        hintText.setTextSize(hintTextSize);
        //hintText.setText("努力加载中...");
        hintText.setOnClickListener(this);
        hintText.setId(View.generateViewId());
        LayoutParams rl1=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl1.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(hintText,rl1);


        control_layout=new RelativeLayout(context);
        control_layout.setBackgroundColor(toolBgColor);
        //默认开始显示媒体栏
        control_layout.setTag(true);
        LayoutParams rl2=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);  //默认高度120
        rl2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.addView(control_layout,rl2);

        //添加媒体控件
        controlIv=new ImageView(context);
        controlIv.setFocusable(true);
        controlIv.setId(View.generateViewId());
        controlIv.setOnClickListener(this);
        controlIv.setImageResource(R.drawable.ic_pause_black_24dp);
        LayoutParams rl3=new LayoutParams(controlSize,controlSize);
        rl3.addRule(CENTER_VERTICAL);
        rl3.addRule(ALIGN_PARENT_START);
        rl3.setMargins(10,8,8,10);
        control_layout.addView(controlIv,rl3);

        loadTime=new TextView(context);
        loadTime.setId(View.generateViewId());
        loadTime.setText("00:00:00");
        loadTime.setTextSize(timeTextSize);
        loadTime.setTextColor(timeColor);
        LayoutParams rl4=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl4.addRule(CENTER_VERTICAL);
        rl4.addRule(RIGHT_OF,controlIv.getId());    //添加到controlIv的右边
        rl4.setMargins(5,8,5,8);
        control_layout.addView(loadTime,rl4);

        totalTime=new TextView(context);
        totalTime.setText("00:00:00");
        totalTime.setTextSize(timeTextSize);
        totalTime.setTextColor(timeColor);
        totalTime.setId(View.generateViewId());
        LayoutParams rl5=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl5.addRule(CENTER_VERTICAL);
        rl5.addRule(ALIGN_PARENT_END);
        rl5.setMargins(5,8,10,8);
        control_layout.addView(totalTime,rl5);

        //seekBar的样式通过资源文件指定
        seekBar=new SeekBar(context);
        seekBar.setFocusable(true);
        seekBar.setOnSeekBarChangeListener(this);
        LayoutParams rl6=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl6.addRule(CENTER_VERTICAL);
        rl6.addRule(RIGHT_OF,loadTime.getId());    //添加到controlIv的右边
        rl6.addRule(LEFT_OF,totalTime.getId());
        control_layout.addView(seekBar,rl6);

        if(isLive){
            control_layout.setVisibility(GONE);
        }

    }

    private void initData() {
        //获取视频加载相关信息，此方法没有回调
        ksyTextureView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what){
                        //开始缓存数据
                    case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        progressBar.setVisibility(VISIBLE);
                        break;
                        //数据缓存完毕
                    case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        progressBar.setVisibility(GONE);
                        break;
                }
                return false;
            }
        });

        ksyTextureView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                duartion=ksyTextureView.getDuration();
                totalTime.setText(ComputerTime(duartion));
                hintText.setVisibility(View.GONE);
                progressBar.setVisibility(GONE);
                seekBar.setProgress(0);
                //此模式，充模式，视频宽高比例与手机频幕宽高比例不一致时，使用此模式视频播放会有黑边
                ksyTextureView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_NOSCALE_TO_FIT);
                ksyTextureView.start();
                //当视频播放之后5秒隐藏媒体栏
            }
        });
        //播放完成调用
        ksyTextureView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                hintText.setVisibility(VISIBLE);
                hintText.setText("播放完毕");
                ksyTextureView.release();
                seekBar.setProgress(100);
            }
        });
        ksyTextureView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                hintText.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                switch (what){
                    case KSYMediaPlayer.MEDIA_ERROR_IO:
                        hintText.setText("链接超时，点击重试");
                        //Toast.makeText(getApplicationContext(),"链接超时，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_UNKNOWN:
                        hintText.setText("未知的播放器错误，点击重试");
                        //Toast.makeText(getApplicationContext(),"未知的播放器错误，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        hintText.setText("多媒体服务器出错，点击重试");
                        //Toast.makeText(getApplicationContext(),"多媒体服务器出错，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_INVALID_DATA:
                        hintText.setText("无效的媒体数据");
                        //Toast.makeText(getApplicationContext(),"无效的媒体数据，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_TIMED_OUT:
                        hintText.setText("操作超时，点击重试");
                        //Toast.makeText(getApplicationContext(),"操作超时，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_DNS_PARSE_FAILED:
                        hintText.setText("DNS解析失败，请检查网络");
                        //Toast.makeText(getApplicationContext(),"DNS解析失败，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case KSYMediaPlayer.MEDIA_ERROR_CONNECT_SERVER_FAILED:
                        hintText.setText("连接服务器失败，请检查网络");
                        //Toast.makeText(getApplicationContext(),"连接服务器失败，请重试",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        timer=new Timer();
        handler.sendEmptyMessageDelayed(11,1000*8);
        setShowTime();

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==hintText.getId()){
            progressBar.setVisibility(VISIBLE);
            hintText.setVisibility(GONE);
            Toast.makeText(context.getApplicationContext(),"重试中", Toast.LENGTH_SHORT).show();
            //重新加载视频,这里有个bug,视频重新载入不了
            ksyTextureView.reload(ksyTextureView.getDataSource(),true);
        }else if(v.getId()==controlIv.getId()){
            if(ksyTextureView!=null){
                if(ksyTextureView.isPlaying()){
                    ksyTextureView.pause();
                    controlIv.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else {
                    ksyTextureView.start();
                    controlIv.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!isLive){
            if((boolean)ksyTextureView.getTag()){
                control_layout.setVisibility(View.INVISIBLE);
                /*
                Animator animation_out= AnimatorInflater.loadAnimator(context,R.animator.translate_out);
                animation_out.setTarget(control_layout);
                animation_out.start();*/

                control_layout.setTag(false);
                ksyTextureView.setTag(false);
            }else {
                control_layout.setVisibility(View.VISIBLE);
                /*
                Animator animation_int= AnimatorInflater.loadAnimator(context,R.animator.translate_in);
                animation_int.setTarget(control_layout);
                animation_int.start();*/

                control_layout.setTag(true);
                ksyTextureView.setTag(true);
                //5秒钟后控制栏隐藏，当连续点击时，取消上一次的点击，只保留最后一次的点击
                handler.removeMessages(11);
                handler.sendEmptyMessageDelayed(11,5000);

            }
        }
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        double mprogress=progress/100d;
        double seekPostion=(double) duartion*mprogress;
        //Log.e("mprogress","-->"+mprogress);
        //Log.e("seekPostion","-->"+(long)seekPostion);
        ksyTextureView.seekTo((long)seekPostion,true);
        loadTime.setText(ComputerTime(ksyTextureView.getCurrentPosition()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeeking=true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isSeeking=false;
        handler.sendEmptyMessageDelayed(11,5000);
    }

    //实在视频数据源
    public void setDataSource(String sourceUrl){
        try {
            ksyTextureView.setDataSource(sourceUrl);
            ksyTextureView.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //实在视频缓存最长时间
    public void setMaxBufferTimeSize(float timeSize){
        //设置播放器缓存最长时间，默认2秒
        ksyTextureView.setBufferTimeMax(timeSize);
    }

    public void setTimeOut(int prepareTimeOut,int readTimeOut){
        //设置视频读取和准备超时时间
        ksyTextureView.setTimeout(prepareTimeOut,readTimeOut);
    }

    public void setBufferSize(int size){
        //设置缓存大小，单位M，默认15M
        ksyTextureView.setBufferSize(size);
    }


    private void setShowTime(){
        //更新已播视频时间
        if(null!=timer){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(00);
                }
            },0,1000);
        }
    }



    private String ComputerTime(long time){
        int seconds= (int) (time/1000);
        int temp=0;
        StringBuffer sb=new StringBuffer();
        temp = seconds/3600;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600/60;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600%60;
        sb.append((temp<10)?"0"+temp:""+temp);
        return sb.toString();
    }


    public void onPause(){
        if(ksyTextureView!=null){
            ksyTextureView.pause();
        }
    }

    public void onDestory(){
        if(ksyTextureView!=null){
            ksyTextureView.stop();
            ksyTextureView.release();
        }
    }


}
