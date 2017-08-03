package com.szreach.ybolotvbox.beans;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class LiveBean implements Serializable {
    private long coId;                      // 企业号
    private String channelId;               // 直播频道ID
    private String liveId;                  // 直播主键ID
    private String liveName;                // 直播名称
    private String liveStart;               // 直播开始时间，格式为：yyyy-MM-dd HH:mm
    private String liveEnd;                 // 直播结束时间，格式为：yyyy-MM-dd HH:mm
    private int onLine;                     // 在线人数
    private String headImg;                 // 图片信息
    private String ln01;                    // 流地址
    private int liveFlag;                  // 直播状态
    private String liveFlagStr;            // 直播状态显示字符串
    private String liveDateTimeStr;        // 直播日期时间显示字符串

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

    public int getOnLine() {
        return onLine;
    }

    public void setOnLine(int onLine) {
        this.onLine = onLine;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getLn01() {
        return ln01;
    }

    public int getLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(int liveFlag) {
        this.liveFlag = liveFlag;
    }

    public void setLn01(String ln01) {

        this.ln01 = ln01;
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
}
