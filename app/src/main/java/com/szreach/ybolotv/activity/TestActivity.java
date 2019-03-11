package com.szreach.ybolotv.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.player.YboloTvPlayer;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements SurfaceHolder.Callback {


    @BindView(R.id.ybolo_player)
    YboloTvPlayer yboloPlayer;
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private String str="分支测试";
    private String Address="udp://192.168.1.1:2256";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        yboloPlayer.setBufferSize(30);
        yboloPlayer.setMaxBufferTimeSize(10f);
        yboloPlayer.setTimeOut(10, 10);
        mediaPlayer=new MediaPlayer();
        SurfaceHolder surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(MyApplication.getApplication(), Uri.parse(Address));
        } catch (IOException e) {
            e.printStackTrace();
        }
        yboloPlayer.setDataSources("udp://192.168.1.1:2256");
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        yboloPlayer.OnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yboloPlayer.OnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yboloPlayer.OnDestroy();
    }



}
