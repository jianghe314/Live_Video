package com.szreach.ybolotvbox.beans;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/29 0029.
 */

public class VodGroupBean implements Serializable {
    private String videoGroupId;
    private String videoGroupName;

    public String getVideoGroupId() {
        return videoGroupId;
    }

    public void setVideoGroupId(String videoGroupId) {
        this.videoGroupId = videoGroupId;
    }

    public String getVideoGroupName() {
        return videoGroupName;
    }

    public void setVideoGroupName(String videoGroupName) {
        this.videoGroupName = videoGroupName;
    }
}
