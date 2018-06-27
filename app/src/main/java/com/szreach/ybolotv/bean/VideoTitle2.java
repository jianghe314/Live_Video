package com.szreach.ybolotv.bean;

/**
 * 二级标题
 */
public class VideoTitle2 {
    private String coId;
    private String groupId;
    private String groupPid;
    private String orgId;
    private String groupName;
    private String groupType;
    private String groupPath;
    private String groupRank;

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupPid() {
        return groupPid;
    }

    public void setGroupPid(String groupPid) {
        this.groupPid = groupPid;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public String getGroupRank() {
        return groupRank;
    }

    public void setGroupRank(String groupRank) {
        this.groupRank = groupRank;
    }
}
