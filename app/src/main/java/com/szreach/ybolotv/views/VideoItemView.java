package com.szreach.ybolotv.views;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szreach.ybolotv.R;
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
            LayoutParams lp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x75), getResources().getDimensionPixelOffset(R.dimen.y70));
            this.setLayoutParams(lp);
            this.setOrientation(LinearLayout.VERTICAL);

            videoName = new TextView(context);
            LayoutParams nameLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            nameLp.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.x3), 0, 0);
            videoName.setLayoutParams(nameLp);
            videoName.setTextColor(0xffc0c0c0);
            videoName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.x9));
            videoName.setText(video.getVideoName());
            Toast.makeText(context,videoImg.getVideo().getVideoImgs(),Toast.LENGTH_LONG).show();
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
