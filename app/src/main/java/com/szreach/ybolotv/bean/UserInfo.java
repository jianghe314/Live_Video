package com.szreach.ybolotv.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ZX on 2018/9/18
 */
@Entity
public class UserInfo {

    @Id
    private long coId;
    private String userId;
    private String userName;		// 昵称
    private String trueName; 		// 用户真实姓名
    private int userSex;			// 用户性别：  1-男 2-女
    private String introduction; 	// 用户个人简介
    private String userLogin;
    private String userPass;
    private String userOrg;
    private String userReg;//注册时间
    private String userType;//0，管理员；2，普通用户（学生）   3老师   4院校管理员  5新后台超管 6运维超管
    private String userFlag;//帐号状态：0，未审核；1，正常；2，冻结
    private String userLast;
    private String userImg;
    private String openId;
    private String userWeixin;
    private String userPhone;
    private String userEmail;
    private String orgId;
    private String postId;
    private String userPy;
    private String openAvatar;
    private String openStatus; //1已关注，2禁用成员，4未关注
    private String userMain; //1负责人 0 否
    private String isQy;    //0 不是 1是  是否是企业成员
    private String qyOpenId;
    private String unionId;
    private String lgOpenId;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private int syncFlag;//0:系统同步记录;1:手工新建记录;2:需删除记录;3:需同步记录
    private String userPassCalc;
    private String sessionId;
    private String ldapFlag;
    /** 发送邮件时间 */
    private String sendMailTime;

    /** 账互是否激活（ 0、否 ； 1、是） */
    private int activatedFlag;
    private String coName;

    @Generated(hash = 239033264)
    public UserInfo(long coId, String userId, String userName, String trueName,
            int userSex, String introduction, String userLogin, String userPass,
            String userOrg, String userReg, String userType, String userFlag,
            String userLast, String userImg, String openId, String userWeixin,
            String userPhone, String userEmail, String orgId, String postId,
            String userPy, String openAvatar, String openStatus, String userMain,
            String isQy, String qyOpenId, String unionId, String lgOpenId,
            String attribute1, String attribute2, String attribute3,
            String attribute4, int syncFlag, String userPassCalc, String sessionId,
            String ldapFlag, String sendMailTime, int activatedFlag,
            String coName) {
        this.coId = coId;
        this.userId = userId;
        this.userName = userName;
        this.trueName = trueName;
        this.userSex = userSex;
        this.introduction = introduction;
        this.userLogin = userLogin;
        this.userPass = userPass;
        this.userOrg = userOrg;
        this.userReg = userReg;
        this.userType = userType;
        this.userFlag = userFlag;
        this.userLast = userLast;
        this.userImg = userImg;
        this.openId = openId;
        this.userWeixin = userWeixin;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.orgId = orgId;
        this.postId = postId;
        this.userPy = userPy;
        this.openAvatar = openAvatar;
        this.openStatus = openStatus;
        this.userMain = userMain;
        this.isQy = isQy;
        this.qyOpenId = qyOpenId;
        this.unionId = unionId;
        this.lgOpenId = lgOpenId;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.syncFlag = syncFlag;
        this.userPassCalc = userPassCalc;
        this.sessionId = sessionId;
        this.ldapFlag = ldapFlag;
        this.sendMailTime = sendMailTime;
        this.activatedFlag = activatedFlag;
        this.coName = coName;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(String userOrg) {
        this.userOrg = userOrg;
    }

    public String getUserReg() {
        return userReg;
    }

    public void setUserReg(String userReg) {
        this.userReg = userReg;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public String getUserLast() {
        return userLast;
    }

    public void setUserLast(String userLast) {
        this.userLast = userLast;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserWeixin() {
        return userWeixin;
    }

    public void setUserWeixin(String userWeixin) {
        this.userWeixin = userWeixin;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserPy() {
        return userPy;
    }

    public void setUserPy(String userPy) {
        this.userPy = userPy;
    }

    public String getOpenAvatar() {
        return openAvatar;
    }

    public void setOpenAvatar(String openAvatar) {
        this.openAvatar = openAvatar;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public String getUserMain() {
        return userMain;
    }

    public void setUserMain(String userMain) {
        this.userMain = userMain;
    }

    public String getIsQy() {
        return isQy;
    }

    public void setIsQy(String isQy) {
        this.isQy = isQy;
    }

    public String getQyOpenId() {
        return qyOpenId;
    }

    public void setQyOpenId(String qyOpenId) {
        this.qyOpenId = qyOpenId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getLgOpenId() {
        return lgOpenId;
    }

    public void setLgOpenId(String lgOpenId) {
        this.lgOpenId = lgOpenId;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public int getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(int syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getUserPassCalc() {
        return userPassCalc;
    }

    public void setUserPassCalc(String userPassCalc) {
        this.userPassCalc = userPassCalc;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLdapFlag() {
        return ldapFlag;
    }

    public void setLdapFlag(String ldapFlag) {
        this.ldapFlag = ldapFlag;
    }

    public String getSendMailTime() {
        return sendMailTime;
    }

    public void setSendMailTime(String sendMailTime) {
        this.sendMailTime = sendMailTime;
    }

    public int getActivatedFlag() {
        return activatedFlag;
    }

    public void setActivatedFlag(int activatedFlag) {
        this.activatedFlag = activatedFlag;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }
}
