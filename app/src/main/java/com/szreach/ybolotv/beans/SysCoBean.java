package com.szreach.ybolotv.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/8/10 0010.
 */

public class SysCoBean implements Serializable, Parcelable {

    private long coId;
    private String coName;
    private String coLogo;

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getCoLogo() {
        return coLogo;
    }

    public void setCoLogo(String coLogo) {
        this.coLogo = coLogo;
    }

    public static Creator<SysCoBean> CREATOR = new Creator<SysCoBean>() {
        @Override
        public SysCoBean createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public SysCoBean[] newArray(int i) {
            return new SysCoBean[0];
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
