package com.szreach.ybolotv.mInterface;

import android.content.Context;
import android.content.SharedPreferences;

import com.szreach.ybolotv.MyApplication;

/**
 * Created by ZX on 2018/9/18
 */
public class Interface {

    //用户REST接口前缀
    public static final String URL_PREFIX_MY_HOME="/rest/VuxMyHomeService";
    public static final String LOGIN= "/android/myHomeCenter/login/submit";//用户登录
    //直播REST接口前缀
    public static final String URL_PREFIX_LIVE = "/rest/VuxWxLiveService";
    public static final String URL_POST_LIVE_LIST = "/android/live/livelist";//直播
    //直播封面图片前缀
    public static final String URL_REC_COIMG = "/Rec/coImg/";

    //点播REST接口前缀
    public static final String URL_PREFIX_VIDEO="/rest/VuxVideoService";
    //视频接口后缀
    public static final String URL_POST_VIDEO_LIST_NOGROUP="/video/android/index/list";

    //直播
    public static final String URL_POST_LIVE_PLAY="/live/android/livePlay";
    //点播播放REST接口前缀
    public static final String URL_PREFIX_VIDEO_PLAY="/rest/VuxVideoPlayService";
    public static final String URL_POST_VIDEO_PLAY="/video/android/player";

    public static final String URL_POST_VIDEO_GROUP_LIST ="/video/android/group";//分类列表
    public static final String URL_POST_USER_MODIFY_INFO= "/myHomeCenter/my/modifyUserInfo";//个人信息修改

    public static final String URL_POST_USER_PWD = "/myHomeCenter/my/pwd";//修改密码

    public static final String URL_POST_VIDEO_INFO = "/vod/vodIntro";//点播信息

    //点播评论REST接口前缀
    public static final String URL_PREFIX_VIDEO_COMMENT= "/rest/VuxVideoCommentService";
    public static final String URL_POST_VIDEO_COMMENT = "/video/comment/list";//点播评论页面

    public static final String USER_IMG="/Rec/userImg/";
    public static final String URL_POST_VIDEO_COMMENT_SUBMIT="/android/video/comment/submit";//点播评论提交

    public static final String URL_POST_LIVE_INFO="/live/liveIntro";//直播介绍

    //直播评论REST接口前缀
    public static final String URL_PREFIX_LIVE_COMMENT="/rest/VuxWxLiveCommentService";
    public static final String URL_POST_LIVE_COMMENT="/live/comment/list";//直播评论页面

    public static final String URL_POST_LIVE_COMMENT_SUBMIT="/live/android/comment/submit";//提交评论

    public static String getIpAddress(Context context){
        SharedPreferences sp=context.getSharedPreferences("Address",Context.MODE_PRIVATE);
        return sp.getString("address","");
    }

    //登录
    public static String Login(){
        String url=getIpAddress(MyApplication.getApplication())+URL_PREFIX_MY_HOME+LOGIN;
        return url;
    }

    //首页直播列表
    public static String LiveList(){
        String url=getIpAddress(MyApplication.getApplication())+URL_PREFIX_LIVE+URL_POST_LIVE_LIST;
        return url;
    }

    //首页视频列表接口
    public static String VideoList(){
        String url=getIpAddress(MyApplication.getApplication())+URL_PREFIX_VIDEO+URL_POST_VIDEO_LIST_NOGROUP;
        return url;
    }
}
