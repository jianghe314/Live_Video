package com.szreach.ybolotv.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.VideoBean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class VideoImgItemView extends AppCompatImageView {
    private final VideoBean video;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            Bitmap bitmap = data.getParcelable("bitMap");
            setImageBitmap(bitmap);
        }
    };

    public VideoImgItemView(Context context, VideoBean videoBean) {
        super(context);
        this.video = videoBean;
        LinearLayout.LayoutParams imgLp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x65), getResources().getDimensionPixelOffset(R.dimen.x40));
        this.setLayoutParams(imgLp);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        new Thread() {
            @Override
            public void run() {
                try {
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    URL url = new URL(video.getVideoImgs());
                    Bitmap bitMap = BitmapFactory.decodeStream(url.openStream());
                    data.putParcelable("bitMap", bitMap);
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public VideoBean getVideo() {
        return video;
    }
}
