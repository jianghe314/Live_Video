package com.szreach.ybolotv.bean;

import java.util.ArrayList;

/**
 * 一级标题
 */
public class VideoTitle {
    private String groupRank;
    private String groupPid;
    private String groupName;
    private String groupType;
    private String groupId;
    private String groupPath;
    private String orgId;
    private ArrayList<VideoTitle2> twoVideoGroupList;

    public String getGroupRank() {
        return groupRank;
    }

    public void setGroupRank(String groupRank) {
        this.groupRank = groupRank;
    }

    public String getGroupPid() {
        return groupPid;
    }

    public void setGroupPid(String groupPid) {
        this.groupPid = groupPid;
    }

    public String getGroupName() {
        if(null==groupName){
            return "";
        }
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ArrayList<VideoTitle2> getTwoVideoGroupList() {
        return twoVideoGroupList;
    }

    public void setTwoVideoGroupList(ArrayList<VideoTitle2> twoVideoGroupList) {
        this.twoVideoGroupList = twoVideoGroupList;
    }
}
