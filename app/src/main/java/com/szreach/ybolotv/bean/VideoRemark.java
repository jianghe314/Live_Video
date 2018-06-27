package com.szreach.ybolotv.bean;

public class VideoRemark {
    private long coId;					// 帐套ID
    private String commId;				// 评论主键ID
    private String videoId;				// 课件主键ID
    private String userId;				// 用户ID
    private String userName;			// 用户名
    private String userImg;				//用户头像
    private String commTime;			// 评论时间
    private String commContent;			// 评论内容
    private String commReply;			//
    private int commReview;				// 是否审核：0表示未审核，1表示已审核
    private String commReviewId;		// 审核者ID
    private String commReviewTime;		// 审核者用户名
    private String commReplyId;			// 评论者ID
    private String commReplyTime;		// 评论者用户名
    private int commFlag;				//
    private String videoCname;			// 视频别名
    private String videoSpeak;			// 主讲人
    private String commReviewName;		// 审核人名称

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        if(null==userName||userName.equals("")){
            return "游客";
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getCommTime() {
        if(null==commTime||commTime.equals("")){
            return "暂无评论时间";
        }
        return commTime;
    }

    public void setCommTime(String commTime) {
        this.commTime = commTime;
    }

    public String getCommContent() {
        if(null==commContent||commContent.equals("")){
            return "暂无评论内容";
        }
        return commContent;
    }

    public void setCommContent(String commContent) {
        this.commContent = commContent;
    }

    public String getCommReply() {
        return commReply;
    }

    public void setCommReply(String commReply) {
        this.commReply = commReply;
    }

    public int getCommReview() {
        return commReview;
    }

    public void setCommReview(int commReview) {
        this.commReview = commReview;
    }

    public String getCommReviewId() {
        return commReviewId;
    }

    public void setCommReviewId(String commReviewId) {
        this.commReviewId = commReviewId;
    }

    public String getCommReviewTime() {
        return commReviewTime;
    }

    public void setCommReviewTime(String commReviewTime) {
        this.commReviewTime = commReviewTime;
    }

    public String getCommReplyId() {
        return commReplyId;
    }

    public void setCommReplyId(String commReplyId) {
        this.commReplyId = commReplyId;
    }

    public String getCommReplyTime() {
        return commReplyTime;
    }

    public void setCommReplyTime(String commReplyTime) {
        this.commReplyTime = commReplyTime;
    }

    public int getCommFlag() {
        return commFlag;
    }

    public void setCommFlag(int commFlag) {
        this.commFlag = commFlag;
    }

    public String getVideoCname() {
        return videoCname;
    }

    public void setVideoCname(String videoCname) {
        this.videoCname = videoCname;
    }

    public String getVideoSpeak() {
        return videoSpeak;
    }

    public void setVideoSpeak(String videoSpeak) {
        this.videoSpeak = videoSpeak;
    }

    public String getCommReviewName() {
        return commReviewName;
    }

    public void setCommReviewName(String commReviewName) {
        this.commReviewName = commReviewName;
    }
}
