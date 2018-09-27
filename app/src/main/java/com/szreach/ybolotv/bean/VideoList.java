package com.szreach.ybolotv.bean;

public class VideoList {
    private long coId; // 帐套号

    private String videoId; // 课件ID

    private String videoName; // 实际文件名

    private String videoCname; // 课件别名

    private String videoTime; // 创建时间,格式为：yyyy-MM-dd HH:mm:ss

    private String videoLong; // 时长，格式为：HH:mm:ss

    private String videoPublishId; // 视频发布者ID

    private String videoPublishName; // 视频发布者名称

    private String videoSpeak; // 主讲人

    private int videoVod; // 点播数

    private int videoComment; // 评论数

    private int videoGood; // 点赞数

    private int videoFavorite; // 收藏数

    private int videoDownload; // 下载数

    private int videoAuth; // 0:未审批，1 已审核

    private int videoLevel; // 1:公开观看,2:登录观看,3:加密观看,4:分组观看,5:暂不公开，6：IP观看

    private String vodPasswd; // 观看密码

    private int downloadAuthLevel; // 下载权限：0、不允许下载，1、允许下载

    private int videoStatus; // 状态，-1表示异常课件，0表示正在录制状态，1表示远程状态，2表示正常状态课件，3表示录制暂停状态，4表示上传中状态，6表示编辑处理状态，7表示转码处理状态
    // 8:视频未推送未审核 9推送完成未审核 10审核完成未推送

    private int videoGroupType; // 课件类型(0 录播视频、1 微课)

    private int videoAuthLevel; //

    private String videoAuthGroup; //

    private String videoLabel; // 标签

    private String videoRec; //

    private String videoImage; // 预览图

    private String videoMbType; // 收费类型

    private double videoPayMoney; // 收费额度

    private String liveId; // 直播id

    private String liveTemplatePc; //

    private String liveTemplateMb; //

    private int issue; // 是否同步平台：0、未发送,1、发送中,2、发送完成

    private int videoCaption; // 1、已上传字幕；0、未上传字幕

    private String orgId;

    private String orgName; // 针对九冶组织架构名称

    private String videoRoom; // 归属教室名称

    private String videoFtp; //

    /* 政企参数-- */
    private int videoStick; //

    private int albumVideoNum; //

    private String videoSampleDesc; //

    private String videoDesc;

    private int sortNum;
    /*--政企参数*/

    private String videoUse;

    private String videoUploadid;

    private String videoAttach;

    private String videoHt;

    private String meetNumber; // 会议号码

    private int pushStatus; // 推流状态

    private String sysVideoProp1; // 学科

    private String sysVideoProp2; // 版本

    private String sysVideoProp3; // 学段

    private String sysVideoProp4; // 年级

    private String sysVideoProp5;

    private String sysVideoProp6;

    private String sysVideoProp7;

    private String sysVideoProp8;

    private String sysVideoProp9;

    private String sysVideoProp10;

    private String ipGroup; // ip分组字段

    private String qaAnswer; // 视频问答观看

    // update 20170816 by lisheng 经世优学在线学习平台新增字段
    private long groupId;// 视频分类id

    private int authFlag;// 0，私有；1，公开待审核；2，公开已审核；3，公开审核不通过；
    // end

    private String videoMbTypeStr;

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoCname() {
        return videoCname;
    }

    public void setVideoCname(String videoCname) {
        this.videoCname = videoCname;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getVideoLong() {
        return videoLong;
    }

    public void setVideoLong(String videoLong) {
        this.videoLong = videoLong;
    }

    public String getVideoPublishId() {
        return videoPublishId;
    }

    public void setVideoPublishId(String videoPublishId) {
        this.videoPublishId = videoPublishId;
    }

    public String getVideoPublishName() {
        return videoPublishName;
    }

    public void setVideoPublishName(String videoPublishName) {
        this.videoPublishName = videoPublishName;
    }

    public String getVideoSpeak() {
        return videoSpeak;
    }

    public void setVideoSpeak(String videoSpeak) {
        this.videoSpeak = videoSpeak;
    }

    public int getVideoVod() {
        return videoVod;
    }

    public void setVideoVod(int videoVod) {
        this.videoVod = videoVod;
    }

    public int getVideoComment() {
        return videoComment;
    }

    public void setVideoComment(int videoComment) {
        this.videoComment = videoComment;
    }

    public int getVideoGood() {
        return videoGood;
    }

    public void setVideoGood(int videoGood) {
        this.videoGood = videoGood;
    }

    public int getVideoFavorite() {
        return videoFavorite;
    }

    public void setVideoFavorite(int videoFavorite) {
        this.videoFavorite = videoFavorite;
    }

    public int getVideoDownload() {
        return videoDownload;
    }

    public void setVideoDownload(int videoDownload) {
        this.videoDownload = videoDownload;
    }

    public int getVideoAuth() {
        return videoAuth;
    }

    public void setVideoAuth(int videoAuth) {
        this.videoAuth = videoAuth;
    }

    public int getVideoLevel() {
        return videoLevel;
    }

    public void setVideoLevel(int videoLevel) {
        this.videoLevel = videoLevel;
    }

    public String getVodPasswd() {
        return vodPasswd;
    }

    public void setVodPasswd(String vodPasswd) {
        this.vodPasswd = vodPasswd;
    }

    public int getDownloadAuthLevel() {
        return downloadAuthLevel;
    }

    public void setDownloadAuthLevel(int downloadAuthLevel) {
        this.downloadAuthLevel = downloadAuthLevel;
    }

    public int getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(int videoStatus) {
        this.videoStatus = videoStatus;
    }

    public int getVideoGroupType() {
        return videoGroupType;
    }

    public void setVideoGroupType(int videoGroupType) {
        this.videoGroupType = videoGroupType;
    }

    public int getVideoAuthLevel() {
        return videoAuthLevel;
    }

    public void setVideoAuthLevel(int videoAuthLevel) {
        this.videoAuthLevel = videoAuthLevel;
    }

    public String getVideoAuthGroup() {
        return videoAuthGroup;
    }

    public void setVideoAuthGroup(String videoAuthGroup) {
        this.videoAuthGroup = videoAuthGroup;
    }

    public String getVideoLabel() {
        return videoLabel;
    }

    public void setVideoLabel(String videoLabel) {
        this.videoLabel = videoLabel;
    }

    public String getVideoRec() {
        return videoRec;
    }

    public void setVideoRec(String videoRec) {
        this.videoRec = videoRec;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getVideoMbType() {
        return videoMbType;
    }

    public void setVideoMbType(String videoMbType) {
        this.videoMbType = videoMbType;
    }

    public double getVideoPayMoney() {
        return videoPayMoney;
    }

    public void setVideoPayMoney(double videoPayMoney) {
        this.videoPayMoney = videoPayMoney;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getLiveTemplatePc() {
        return liveTemplatePc;
    }

    public void setLiveTemplatePc(String liveTemplatePc) {
        this.liveTemplatePc = liveTemplatePc;
    }

    public String getLiveTemplateMb() {
        return liveTemplateMb;
    }

    public void setLiveTemplateMb(String liveTemplateMb) {
        this.liveTemplateMb = liveTemplateMb;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public int getVideoCaption() {
        return videoCaption;
    }

    public void setVideoCaption(int videoCaption) {
        this.videoCaption = videoCaption;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getVideoRoom() {
        return videoRoom;
    }

    public void setVideoRoom(String videoRoom) {
        this.videoRoom = videoRoom;
    }

    public String getVideoFtp() {
        return videoFtp;
    }

    public void setVideoFtp(String videoFtp) {
        this.videoFtp = videoFtp;
    }

    public int getVideoStick() {
        return videoStick;
    }

    public void setVideoStick(int videoStick) {
        this.videoStick = videoStick;
    }

    public int getAlbumVideoNum() {
        return albumVideoNum;
    }

    public void setAlbumVideoNum(int albumVideoNum) {
        this.albumVideoNum = albumVideoNum;
    }

    public String getVideoSampleDesc() {
        return videoSampleDesc;
    }

    public void setVideoSampleDesc(String videoSampleDesc) {
        this.videoSampleDesc = videoSampleDesc;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getVideoUse() {
        return videoUse;
    }

    public void setVideoUse(String videoUse) {
        this.videoUse = videoUse;
    }

    public String getVideoUploadid() {
        return videoUploadid;
    }

    public void setVideoUploadid(String videoUploadid) {
        this.videoUploadid = videoUploadid;
    }

    public String getVideoAttach() {
        return videoAttach;
    }

    public void setVideoAttach(String videoAttach) {
        this.videoAttach = videoAttach;
    }

    public String getVideoHt() {
        return videoHt;
    }

    public void setVideoHt(String videoHt) {
        this.videoHt = videoHt;
    }

    public String getMeetNumber() {
        return meetNumber;
    }

    public void setMeetNumber(String meetNumber) {
        this.meetNumber = meetNumber;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getSysVideoProp1() {
        return sysVideoProp1;
    }

    public void setSysVideoProp1(String sysVideoProp1) {
        this.sysVideoProp1 = sysVideoProp1;
    }

    public String getSysVideoProp2() {
        return sysVideoProp2;
    }

    public void setSysVideoProp2(String sysVideoProp2) {
        this.sysVideoProp2 = sysVideoProp2;
    }

    public String getSysVideoProp3() {
        return sysVideoProp3;
    }

    public void setSysVideoProp3(String sysVideoProp3) {
        this.sysVideoProp3 = sysVideoProp3;
    }

    public String getSysVideoProp4() {
        return sysVideoProp4;
    }

    public void setSysVideoProp4(String sysVideoProp4) {
        this.sysVideoProp4 = sysVideoProp4;
    }

    public String getSysVideoProp5() {
        return sysVideoProp5;
    }

    public void setSysVideoProp5(String sysVideoProp5) {
        this.sysVideoProp5 = sysVideoProp5;
    }

    public String getSysVideoProp6() {
        return sysVideoProp6;
    }

    public void setSysVideoProp6(String sysVideoProp6) {
        this.sysVideoProp6 = sysVideoProp6;
    }

    public String getSysVideoProp7() {
        return sysVideoProp7;
    }

    public void setSysVideoProp7(String sysVideoProp7) {
        this.sysVideoProp7 = sysVideoProp7;
    }

    public String getSysVideoProp8() {
        return sysVideoProp8;
    }

    public void setSysVideoProp8(String sysVideoProp8) {
        this.sysVideoProp8 = sysVideoProp8;
    }

    public String getSysVideoProp9() {
        return sysVideoProp9;
    }

    public void setSysVideoProp9(String sysVideoProp9) {
        this.sysVideoProp9 = sysVideoProp9;
    }

    public String getSysVideoProp10() {
        return sysVideoProp10;
    }

    public void setSysVideoProp10(String sysVideoProp10) {
        this.sysVideoProp10 = sysVideoProp10;
    }

    public String getIpGroup() {
        return ipGroup;
    }

    public void setIpGroup(String ipGroup) {
        this.ipGroup = ipGroup;
    }

    public String getQaAnswer() {
        return qaAnswer;
    }

    public void setQaAnswer(String qaAnswer) {
        this.qaAnswer = qaAnswer;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }

    public String getVideoMbTypeStr() {
        return videoMbTypeStr;
    }

    public void setVideoMbTypeStr(String videoMbTypeStr) {
        this.videoMbTypeStr = videoMbTypeStr;
    }
}
