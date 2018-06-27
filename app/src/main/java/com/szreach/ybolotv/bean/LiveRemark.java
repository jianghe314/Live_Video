package com.szreach.ybolotv.bean;

public class LiveRemark {
    private long coId;
    private String commId;
    private String liveId;
    private String userId;
    private String userName;
    private String userImg;
    private String commTime;
    private String commContent;
    private String commReply;
    private String commReview;
    private String commReviewId;
    private String commReviewTime;
    private String commReplyId;
    private String commReplyTime;
    private int commFlag;
    private int likeCount;
    private String commContentFace;
    private String indexCommTime;
    private String indexCommReplyTime;

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

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
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
        return commTime;
    }

    public void setCommTime(String commTime) {
        this.commTime = commTime;
    }

    public String getCommContent() {
        if(null==commContent||commContent.equals("")){
            return "暂无评论数据";
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

    public String getCommReview() {
        return commReview;
    }

    public void setCommReview(String commReview) {
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommContentFace() {
        return commContentFace;
    }

    public void setCommContentFace(String commContentFace) {
        this.commContentFace = commContentFace;
    }

    public String getIndexCommTime() {
        if(null==commTime||commTime.equals("")){
            return "----";
        }
        return indexCommTime;
    }

    public void setIndexCommTime(String indexCommTime) {
        this.indexCommTime = indexCommTime;
    }

    public String getIndexCommReplyTime() {
        return indexCommReplyTime;
    }

    public void setIndexCommReplyTime(String indexCommReplyTime) {
        this.indexCommReplyTime = indexCommReplyTime;
    }
}
