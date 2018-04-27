package com.szreach.ybolotv.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.szreach.ybolotv.R;

public class CustomMediaController extends RelativeLayout implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    private int textSize;
    private int controlSize;
    private ImageView control;
    private TextView loadTime;
    private SeekBar seekBar;
    private TextView totleTime;
    private StartOrStopListener startOrStopListener;
    private SetOnSeekBarListener setOnSeekBarListener;

    public CustomMediaController(Context context) {
        this(context,null);
    }

    public CustomMediaController(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.CustomMediaController);
        textSize=array.getDimensionPixelSize(R.styleable.CustomMediaController_media_controller_textSize,10);
        controlSize=array.getDimensionPixelSize(R.styleable.CustomMediaController_media_controller_controlSize,15);
        array.recycle();
        initView(context);

    }

    private void initView(Context context) {
        this.setBackgroundColor(Color.parseColor("#8a4e4d4d"));
        this.setPadding(8,8,8,8);
        control=new ImageView(context);
        control.setTag(false);
        control.setOnClickListener(this);
        control.setImageResource(R.drawable.ic_pause_black_24dp);
        control.setId(View.generateViewId());
        control.setFocusable(true);
        RelativeLayout.LayoutParams rl1= new RelativeLayout.LayoutParams(controlSize, controlSize);
        rl1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rl1.addRule(RelativeLayout.CENTER_VERTICAL);
        control.setLayoutParams(new ViewGroup.LayoutParams(controlSize, controlSize));
        addView(control,rl1);

        //
        totleTime=new TextView(context);
        totleTime.setId(View.generateViewId());
        //
        loadTime=new TextView(context);
        loadTime.setTextSize(textSize);
        loadTime.setTextColor(Color.parseColor("#ffffff"));
        //loadTime.setPadding(10,8,10,8);
        loadTime.setText("00:00:00");
        loadTime.setId(View.generateViewId());
        RelativeLayout.LayoutParams rl2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);;;
        rl2.addRule(RelativeLayout.RIGHT_OF,control.getId());
        rl2.setMargins(20,10,10,10);
        rl2.addRule(RelativeLayout.CENTER_VERTICAL);
        loadTime.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(loadTime,rl2);
        //
        seekBar=new SeekBar(context);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setFocusable(true);
        RelativeLayout.LayoutParams rl3= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);;
        rl3.addRule(RelativeLayout.RIGHT_OF,loadTime.getId());
        rl3.addRule(RelativeLayout.LEFT_OF,totleTime.getId());
        rl3.addRule(RelativeLayout.CENTER_VERTICAL);
        rl3.setMargins(5,10,5,10);
        seekBar.setLayoutParams(rl3);
        seekBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(seekBar,rl3);
        //

        totleTime.setTextSize(textSize);
        totleTime.setTextColor(Color.parseColor("#ffffff"));
        totleTime.setText("00:00:00");
        RelativeLayout.LayoutParams rl4= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);;
        rl4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl4.addRule(RelativeLayout.CENTER_VERTICAL);
        rl4.setMargins(0,5,10,5);
        totleTime.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(totleTime,rl4);

    }

    //暂停或开始点击事件
    @Override
    public void onClick(View v) {
        if((boolean)control.getTag()){
            control.setImageResource(R.drawable.ic_pause_black_24dp);
            control.setTag(false);
            if(startOrStopListener!=null){
                startOrStopListener.startOrstopListener(control);
            }
        }else {
            control.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            control.setTag(true);
            if(startOrStopListener!=null){
                startOrStopListener.startOrstopListener(control);
            }
        }


    }

    //seekbar拖动事件监听
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(setOnSeekBarListener!=null){
            setOnSeekBarListener.onSeekBarListener(seekBar,progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    public interface StartOrStopListener{
        void startOrstopListener(ImageView control);
    }

    public interface SetOnSeekBarListener{
        void onSeekBarListener(SeekBar seekBar, int progress);
    }


    //向外提供播放或暂停
    public void setPlayOrStop(StartOrStopListener startOrStopListener){
        this.startOrStopListener=startOrStopListener;
    }

    //设置视频总时间
    public void setTotalTime(long time){
        totleTime.setText(ComputerTime(time));
    }
    //设置已播放视频的时间
    public void setHasLoadTime(long time){
        loadTime.setText(ComputerTime(time));
    }

    //更新seekbar的位置
    public void setProgress(int progress){
        seekBar.setProgress(progress);
    }

    //seekbar监听
    public void setSeekBarProgress(SetOnSeekBarListener seekBarListener){
        this.setOnSeekBarListener=seekBarListener;
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





}

