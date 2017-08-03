package com.szreach.ybolotvbox.beans;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class VideoBean implements Serializable {
    private long coId;
    private String videoId;
    private String vidoeoImg;
    private String videoName;
    private String videoPath;

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVidoeoImg() {
        return vidoeoImg;
    }

    public void setVidoeoImg(String vidoeoImg) {
        this.vidoeoImg = vidoeoImg;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
