package com.szreach.ybolotv.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/7/29 0029.
 */

public class VodGroupBean implements Serializable, Parcelable {
    private Parcelable.Creator CREATOR;

    private long coId;				// 帐套号
    private long groupId;			// 课件组主键ID
    private long groupPid;			// 父课件组主键ID
    private long orgId;				// 组织机构ID
    private String groupName;		    // 组名
    private String groupType;		    // 组类型
    private String groupPath;		    // 组路径
    private long groupRank;			// 排序

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getGroupPid() {
        return groupPid;
    }

    public void setGroupPid(long groupPid) {
        this.groupPid = groupPid;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getGroupName() {
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

    public long getGroupRank() {
        return groupRank;
    }

    public void setGroupRank(long groupRank) {
        this.groupRank = groupRank;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
