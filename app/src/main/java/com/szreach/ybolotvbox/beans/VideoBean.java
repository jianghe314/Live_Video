package com.szreach.ybolotvbox.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class VideoBean implements Serializable, Parcelable {

    private long coId;                     // 帐套号
    private String videoId;                 // 课件ID
    private String videoName;                 // 视频名
    private String videoTime;                 // 创建时间,格式为：yyyy-MM-dd HH:mm:ss
    private String videoLong;                 // 时长，格式为：HH:mm:ss
    private String videoImgs;             // 缩略图
    private String videoDesc;             // 描述
    private String meetNumber;

    private String sysVideoProp1;
    private String sysVideoProp2;
    private String sysVideoProp3;
    private String sysVideoProp4;
    private String sysVideoProp5;
    private String sysVideoProp6;

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

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getVideoLong() {
        return videoLong;
    }

    public String getMeetNumber() {
        return meetNumber;
    }

    public void setMeetNumber(String meetNumber) {
        this.meetNumber = meetNumber;
    }

    public void setVideoLong(String videoLong) {
        this.videoLong = videoLong;
    }

    public String getVideoImgs() {
        return videoImgs;
    }

    public void setVideoImgs(String videoImgs) {
        this.videoImgs = videoImgs;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getSysVideoProp1() {
        return sysVideoProp1;
    }

    public void setSysVideoProp1(String sysVideoProp1) {
        this.sysVideoProp1 = sysVideoProp1;
    }

    public String getSysVideoProp2() {
        return sysVideoProp2;
    }

    public void setSysVideoProp2(String sysVideoProp2) {
        this.sysVideoProp2 = sysVideoProp2;
    }

    public String getSysVideoProp3() {
        return sysVideoProp3;
    }

    public void setSysVideoProp3(String sysVideoProp3) {
        this.sysVideoProp3 = sysVideoProp3;
    }

    public String getSysVideoProp4() {
        return sysVideoProp4;
    }

    public void setSysVideoProp4(String sysVideoProp4) {
        this.sysVideoProp4 = sysVideoProp4;
    }

    public String getSysVideoProp5() {
        return sysVideoProp5;
    }

    public void setSysVideoProp5(String sysVideoProp5) {
        this.sysVideoProp5 = sysVideoProp5;
    }

    public String getSysVideoProp6() {
        return sysVideoProp6;
    }

    public void setSysVideoProp6(String sysVideoProp6) {
        this.sysVideoProp6 = sysVideoProp6;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public VideoBean[] newArray(int i) {
            return new VideoBean[0];
        }
    };
}
