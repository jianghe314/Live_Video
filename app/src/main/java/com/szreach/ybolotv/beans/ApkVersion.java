package com.szreach.ybolotv.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Adams.Tsui on 2017/8/11 0011.
 */

public class ApkVersion implements Serializable, Parcelable {
    private int versionCode;
    private String versionName;
    private String apkPath;
    private String apkSaveName;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getApkSaveName() {
        return apkSaveName;
    }

    public void setApkSaveName(String apkSaveName) {
        this.apkSaveName = apkSaveName;
    }

    public static Creator<ApkVersion> CREATOR = new Creator<ApkVersion>() {
        @Override
        public ApkVersion createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public ApkVersion[] newArray(int i) {
            return new ApkVersion[0];
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
