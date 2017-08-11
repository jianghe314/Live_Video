package com.szreach.ybolotv.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotv.beans.VideoBean;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class VideoItemView extends LinearLayout {
    private VideoBean video;
    private VideoImgItemView videoImg;
    private TextView videoName;

    public VideoItemView(Context context, VideoBean video, VideoImgItemView videoImg) {
        super(context);

        this.video = video;
        this.videoImg = videoImg;

        if(video != null) {
            // 由于设置margins没效果，采用变通的方法解决问题
            LayoutParams lp = new LinearLayout.LayoutParams(208, 200);
            this.setLayoutParams(lp);
            this.setOrientation(LinearLayout.VERTICAL);

            videoName = new TextView(context);
            LayoutParams nameLp = new LayoutParams(174, 100);
            nameLp.setMargins(0, 7, 0, 0);
            videoName.setLayoutParams(nameLp);
            videoName.setTextColor(0xffc0c0c0);
            videoName.setTextSize(21f);
            videoName.setText(video.getVideoName());

            this.addView(videoImg);
            this.addView(videoName);
        }
    }

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public VideoImgItemView getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(VideoImgItemView videoImg) {
        this.videoImg = videoImg;
    }

    public TextView getVideoName() {
        return videoName;
    }

    public void setVideoName(TextView videoName) {
        this.videoName = videoName;
    }
}
