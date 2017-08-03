package com.szreach.ybolotvbox.beans;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class NewsBean implements Serializable {
    private String coId;
    private String newsId;
    private String newsTitle;
    private String newImg;
    private String newsDatetime;
    private String newsUrl;

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewImg() {
        return newImg;
    }

    public void setNewImg(String newImg) {
        this.newImg = newImg;
    }

    public String getNewsDatetime() {
        return newsDatetime;
    }

    public void setNewsDatetime(String newsDatetime) {
        this.newsDatetime = newsDatetime;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
