package com.szreach.ybolotvbox.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import com.szreach.ybolotvbox.beans.VideoBean;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class VideoImgItemView extends AppCompatImageView {
    private VideoBean videoBean;

    public VideoImgItemView(Context context, VideoBean videoBean) {
        super(context);
        this.videoBean = videoBean;
        LinearLayout.LayoutParams imgLp = new LinearLayout.LayoutParams(174, 100);
        this.setLayoutParams(imgLp);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    public VideoBean getVideoBean() {
        return videoBean;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
    }
}
