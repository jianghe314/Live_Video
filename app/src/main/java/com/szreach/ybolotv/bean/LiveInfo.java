package com.szreach.ybolotv.bean;

public class LiveInfo {
    private long coId;
    private String channelId;
    private String channelName;
    private String rtmpPush;
    private String rtmpHsPush;
    private int channelFlag;  // 1:普通(可用,时间不同),2:固定(仅供一个直播使用),3:复用(可用,时间可同),4:固定和复用(可重复自动开启；可用,时间可同),5:录播专用(固定),6:跨天直播,7:频道已冻结,9:y系列产品专用
    /**
     * 第1路至第3路直播源
     */
    private String ln01;
    private String ln02;
    private String ln03;
    private String hn01;
    private String hn02;
    private String hn03;
    private int ls01;
    private int ls02;
    private int ls03;
    private int hs01;
    private int hs02;
    private int hs03;

    /**
     * 第4路至第6路直播源
     */
    private String ln04;
    private String ln05;
    private String ln06;
    private String hn04;
    private String hn05;
    private String hn06;
    private int ls04;
    private int ls05;
    private int ls06;
    private int hs04;
    private int hs05;
    private int hs06;

    /**
     * 第7路直播源
     */
    private String ln07;
    private int ls07;

    private String ln11;
    private String hn11;
    private String ln17;

    /**
     * l1:第1路至第3路的有效直播路当选
     * l2:第4路至第6路的有效直播路当选
     */
    private int l1;
    private int l2;

    private int liveMovie;
    private int liveRes;
    private int resNum;
    private int movieSd;
    private int movieHd;
    private int resSd;
    private int resHd;
    private int streamType;

    private String rtmpAddress;
    private String hlsAddress;
    private String liveId;
    private String liveName;
    private String liveStart;
    private String liveEnd;
    private int liveLevel;
    private String livePass;
    private int liveFlag;
    private int commReview;
    private String loId;//抽奖Id
    private String voId;//投票Id
    private String qnId;//调研Id
    private int onLine;
    private String payType;
    private double payMoney;
    private String templatePc;
    private String templateMb;
    private String liveRemark;
    private String cfgBackgroundImg1;
    private String cfgType;


    //直播配置信息
    private int isConfig = 0;   //默认是没有配置
    private String title;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;
    private String item7;
    private String item8;
    private String item9;
    private String item10;

    private int autoReply = 0;    //直播问答自动回复  默认0-不自动回复
    private String replyName;     //问答自定义回复人
    private String replyContent ; //直播问答自动回复内容
    private String recordId;	//录制ID
    private int recordStatus;   //录制  0 未录制 1录制中 2录制结束
    private String userId;     	// 直播主讲人Id
    private String userName;	// 直播主讲人名称
    private int voiceCount;		// 语音数量
    private int likeCount;		// 点赞数量
    private String headImg;		// 封面图片
    private int onlineCount;    //观看人数(rtmp和hls总数)
    private int liveType;		// 直播方式：0，自建；1，CDN。	默认直播方式0，自建
    private int channelStatus;	// 频道状态 0:启用 1:禁用

    private int authFlag;		// 直播公开权限 0，私有；1，公开待审核；2，公开已审核；3，公开审核不通过；

    private String payTypeStr;

    private String phoneValidate;

    public long getCoId() {
        return coId;
    }

    public void setCoId(long coId) {
        this.coId = coId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRtmpPush() {
        return rtmpPush;
    }

    public void setRtmpPush(String rtmpPush) {
        this.rtmpPush = rtmpPush;
    }

    public String getRtmpHsPush() {
        return rtmpHsPush;
    }

    public void setRtmpHsPush(String rtmpHsPush) {
        this.rtmpHsPush = rtmpHsPush;
    }

    public int getChannelFlag() {
        return channelFlag;
    }

    public void setChannelFlag(int channelFlag) {
        this.channelFlag = channelFlag;
    }

    public String getLn01() {
        return ln01;
    }

    public void setLn01(String ln01) {
        this.ln01 = ln01;
    }

    public String getLn02() {
        return ln02;
    }

    public void setLn02(String ln02) {
        this.ln02 = ln02;
    }

    public String getLn03() {
        return ln03;
    }

    public void setLn03(String ln03) {
        this.ln03 = ln03;
    }

    public String getHn01() {
        return hn01;
    }

    public void setHn01(String hn01) {
        this.hn01 = hn01;
    }

    public String getHn02() {
        return hn02;
    }

    public void setHn02(String hn02) {
        this.hn02 = hn02;
    }

    public String getHn03() {
        return hn03;
    }

    public void setHn03(String hn03) {
        this.hn03 = hn03;
    }

    public int getLs01() {
        return ls01;
    }

    public void setLs01(int ls01) {
        this.ls01 = ls01;
    }

    public int getLs02() {
        return ls02;
    }

    public void setLs02(int ls02) {
        this.ls02 = ls02;
    }

    public int getLs03() {
        return ls03;
    }

    public void setLs03(int ls03) {
        this.ls03 = ls03;
    }

    public int getHs01() {
        return hs01;
    }

    public void setHs01(int hs01) {
        this.hs01 = hs01;
    }

    public int getHs02() {
        return hs02;
    }

    public void setHs02(int hs02) {
        this.hs02 = hs02;
    }

    public int getHs03() {
        return hs03;
    }

    public void setHs03(int hs03) {
        this.hs03 = hs03;
    }

    public String getLn04() {
        return ln04;
    }

    public void setLn04(String ln04) {
        this.ln04 = ln04;
    }

    public String getLn05() {
        return ln05;
    }

    public void setLn05(String ln05) {
        this.ln05 = ln05;
    }

    public String getLn06() {
        return ln06;
    }

    public void setLn06(String ln06) {
        this.ln06 = ln06;
    }

    public String getHn04() {
        return hn04;
    }

    public void setHn04(String hn04) {
        this.hn04 = hn04;
    }

    public String getHn05() {
        return hn05;
    }

    public void setHn05(String hn05) {
        this.hn05 = hn05;
    }

    public String getHn06() {
        return hn06;
    }

    public void setHn06(String hn06) {
        this.hn06 = hn06;
    }

    public int getLs04() {
        return ls04;
    }

    public void setLs04(int ls04) {
        this.ls04 = ls04;
    }

    public int getLs05() {
        return ls05;
    }

    public void setLs05(int ls05) {
        this.ls05 = ls05;
    }

    public int getLs06() {
        return ls06;
    }

    public void setLs06(int ls06) {
        this.ls06 = ls06;
    }

    public int getHs04() {
        return hs04;
    }

    public void setHs04(int hs04) {
        this.hs04 = hs04;
    }

    public int getHs05() {
        return hs05;
    }

    public void setHs05(int hs05) {
        this.hs05 = hs05;
    }

    public int getHs06() {
        return hs06;
    }

    public void setHs06(int hs06) {
        this.hs06 = hs06;
    }

    public String getLn07() {
        return ln07;
    }

    public void setLn07(String ln07) {
        this.ln07 = ln07;
    }

    public int getLs07() {
        return ls07;
    }

    public void setLs07(int ls07) {
        this.ls07 = ls07;
    }

    public String getLn11() {
        return ln11;
    }

    public void setLn11(String ln11) {
        this.ln11 = ln11;
    }

    public String getHn11() {
        return hn11;
    }

    public void setHn11(String hn11) {
        this.hn11 = hn11;
    }

    public String getLn17() {
        return ln17;
    }

    public void setLn17(String ln17) {
        this.ln17 = ln17;
    }

    public int getL1() {
        return l1;
    }

    public void setL1(int l1) {
        this.l1 = l1;
    }

    public int getL2() {
        return l2;
    }

    public void setL2(int l2) {
        this.l2 = l2;
    }

    public int getLiveMovie() {
        return liveMovie;
    }

    public void setLiveMovie(int liveMovie) {
        this.liveMovie = liveMovie;
    }

    public int getLiveRes() {
        return liveRes;
    }

    public void setLiveRes(int liveRes) {
        this.liveRes = liveRes;
    }

    public int getResNum() {
        return resNum;
    }

    public void setResNum(int resNum) {
        this.resNum = resNum;
    }

    public int getMovieSd() {
        return movieSd;
    }

    public void setMovieSd(int movieSd) {
        this.movieSd = movieSd;
    }

    public int getMovieHd() {
        return movieHd;
    }

    public void setMovieHd(int movieHd) {
        this.movieHd = movieHd;
    }

    public int getResSd() {
        return resSd;
    }

    public void setResSd(int resSd) {
        this.resSd = resSd;
    }

    public int getResHd() {
        return resHd;
    }

    public void setResHd(int resHd) {
        this.resHd = resHd;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public String getRtmpAddress() {
        return rtmpAddress;
    }

    public void setRtmpAddress(String rtmpAddress) {
        this.rtmpAddress = rtmpAddress;
    }

    public String getHlsAddress() {
        return hlsAddress;
    }

    public void setHlsAddress(String hlsAddress) {
        this.hlsAddress = hlsAddress;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getLiveName() {
        if(null==liveName||liveName.equals("")){
            return "暂无视频标题数据";
        }
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLiveStart() {
        if(null==liveStart||liveStart.equals("")){
            return "--";
        }
        return liveStart;
    }

    public void setLiveStart(String liveStart) {
        this.liveStart = liveStart;
    }

    public String getLiveEnd() {
        if(null==liveEnd||liveEnd.equals("")){
            return "--";
        }
        return liveEnd;
    }

    public void setLiveEnd(String liveEnd) {
        this.liveEnd = liveEnd;
    }

    public int getLiveLevel() {
        return liveLevel;
    }

    public void setLiveLevel(int liveLevel) {
        this.liveLevel = liveLevel;
    }

    public String getLivePass() {
        return livePass;
    }

    public void setLivePass(String livePass) {
        this.livePass = livePass;
    }

    public int getLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(int liveFlag) {
        this.liveFlag = liveFlag;
    }

    public int getCommReview() {
        return commReview;
    }

    public void setCommReview(int commReview) {
        this.commReview = commReview;
    }

    public String getLoId() {
        return loId;
    }

    public void setLoId(String loId) {
        this.loId = loId;
    }

    public String getVoId() {
        return voId;
    }

    public void setVoId(String voId) {
        this.voId = voId;
    }

    public String getQnId() {
        return qnId;
    }

    public void setQnId(String qnId) {
        this.qnId = qnId;
    }

    public int getOnLine() {
        return onLine;
    }

    public void setOnLine(int onLine) {
        this.onLine = onLine;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getTemplatePc() {
        return templatePc;
    }

    public void setTemplatePc(String templatePc) {
        this.templatePc = templatePc;
    }

    public String getTemplateMb() {
        return templateMb;
    }

    public void setTemplateMb(String templateMb) {
        this.templateMb = templateMb;
    }

    public String getLiveRemark() {
        return liveRemark;
    }

    public void setLiveRemark(String liveRemark) {
        this.liveRemark = liveRemark;
    }

    public String getCfgBackgroundImg1() {
        return cfgBackgroundImg1;
    }

    public void setCfgBackgroundImg1(String cfgBackgroundImg1) {
        this.cfgBackgroundImg1 = cfgBackgroundImg1;
    }

    public String getCfgType() {
        return cfgType;
    }

    public void setCfgType(String cfgType) {
        this.cfgType = cfgType;
    }

    public int getIsConfig() {
        return isConfig;
    }

    public void setIsConfig(int isConfig) {
        this.isConfig = isConfig;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public String getItem8() {
        return item8;
    }

    public void setItem8(String item8) {
        this.item8 = item8;
    }

    public String getItem9() {
        return item9;
    }

    public void setItem9(String item9) {
        this.item9 = item9;
    }

    public String getItem10() {
        return item10;
    }

    public void setItem10(String item10) {
        this.item10 = item10;
    }

    public int getAutoReply() {
        return autoReply;
    }

    public void setAutoReply(int autoReply) {
        this.autoReply = autoReply;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
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

    public int getVoiceCount() {
        return voiceCount;
    }

    public void setVoiceCount(int voiceCount) {
        this.voiceCount = voiceCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getLiveType() {
        return liveType;
    }

    public void setLiveType(int liveType) {
        this.liveType = liveType;
    }

    public int getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(int channelStatus) {
        this.channelStatus = channelStatus;
    }

    public int getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }

    public String getPayTypeStr() {
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public String getPhoneValidate() {
        return phoneValidate;
    }

    public void setPhoneValidate(String phoneValidate) {
        this.phoneValidate = phoneValidate;
    }
}
