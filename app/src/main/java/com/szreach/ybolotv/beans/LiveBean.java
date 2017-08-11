package com.szreach.ybolotv.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class LiveBean implements Serializable, Parcelable {
    private long coId;                      // 企业号
    private String channelId;               // 直播频道ID
    private String liveId;                  // 直播主键ID
    private String liveName;                // 直播名称
    private String liveStart;               // 直播开始时间，格式为：yyyy-MM-dd HH:mm
    private String liveEnd;                 // 直播结束时间，格式为：yyyy-MM-dd HH:mm
    private String headImg;                 // 图片信息
    private int liveFlag;                  // 直播状态 0 预约 1进行 2结束
    private String liveFlagStr;            // 直播状态显示字符串
    private String liveDateTimeStr;        // 直播日期时间显示字符串

    private int onLineNum; // 在线人数
    private String rtmpAddress; // rtmp地址
    private String hlsAddress; // hls地址
    private String liveRemark; // 直播描述

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLiveStart() {
        return liveStart;
    }

    public void setLiveStart(String liveStart) {
        this.liveStart = liveStart;
    }

    public String getLiveEnd() {
        return liveEnd;
    }

    public void setLiveEnd(String liveEnd) {
        this.liveEnd = liveEnd;
    }


    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }


    public int getLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(int liveFlag) {
        this.liveFlag = liveFlag;
    }

    public String getLiveFlagStr() {
        String retStr = "";
        switch (this.liveFlag) {
            case 1:
                retStr = "直播中";
                break;
        }
        return retStr;
    }

    public void setLiveFlagStr(String liveFlagStr) {
        this.liveFlagStr = liveFlagStr;
    }

    public String getLiveDateTimeStr() {
        return this.liveStart + "-" + this.liveEnd.substring(11);
    }

    public void setLiveDateTimeStr(String liveDateTimeStr) {
        this.liveDateTimeStr = liveDateTimeStr;
    }

    public int getOnLineNum() {
        return onLineNum;
    }

    public void setOnLineNum(int onLineNum) {
        this.onLineNum = onLineNum;
    }

    public String getRtmpAddress() {
        return rtmpAddress;
    }

    public void setRtmpAddress(String rtmpAddress) {
        this.rtmpAddress = rtmpAddress;
    }

    public String getHlsAddress() {
        return hlsAddress;
    }

    public void setHlsAddress(String hlsAddress) {
        this.hlsAddress = hlsAddress;
    }

    public String getLiveRemark() {
        return liveRemark;
    }

    public void setLiveRemark(String liveRemark) {
        this.liveRemark = liveRemark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static Creator<LiveBean> CREATOR = new Creator<LiveBean>() {
        @Override
        public LiveBean createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public LiveBean[] newArray(int i) {
            return new LiveBean[0];
        }
    };
}
