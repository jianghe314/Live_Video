package com.szreach.ybolotv.presenter;

import com.szreach.ybolotv.base.BasePresenter;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.model.Model;

import java.util.List;

/**
 * Created by ZX on 2018/9/27
 */
public class VideoDetailPresenter extends BasePresenter<MVPView> {

    //获取点播视频路径
    public void getVideoUrl(List<String> params,List<Object> values){
        if(!isViewAttach()){
            return;
        }
        Model.postData();
    }
}
