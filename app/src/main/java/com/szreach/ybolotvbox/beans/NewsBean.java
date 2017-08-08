package com.szreach.ybolotvbox.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class NewsBean implements Serializable, Parcelable {

    private long coId;
    private String nnId;              // 新闻ID
    private String userId;           // 发布者用户ID
    private String userName;         // 发布者用户名称
    private String createTime;      // 创建时间
    private int viewCount;          // 浏览量
    private String title;            // 新闻标题
    private String summary;         // 新闻摘要
    private String thumbnail;       // 封面图片

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getNnId() {
        return nnId;
    }

    public void setNnId(String nnId) {
        this.nnId = nnId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static Creator<NewsBean> CREATOR = new Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public NewsBean[] newArray(int i) {
            return new NewsBean[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
