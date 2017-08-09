package com.szreach.ybolotvbox.listener;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.activities.VodListActivity;
import com.szreach.ybolotvbox.activities.VodPlayActivity;
import com.szreach.ybolotvbox.beans.VideoBean;
import com.szreach.ybolotvbox.utils.Constant;
import com.szreach.ybolotvbox.utils.StoreObjectUtils;
import com.szreach.ybolotvbox.views.VideoImgItemView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adams.Tsui on 2017/7/31 0031.
 */

public class VideoItemListener implements View.OnKeyListener {

    private static final int STEP = 5;
    private static final int CENTER = 0;
    private static final int LEFT = 1;
    private static final int TOP = 2;
    private static final int RIGHT = 3;
    private static final int BOTTOM = 4;
    private static final int LEFT_TOP = 5;
    private static final int RIGHT_TOP = 6;
    private static final int LEFT_BOTTOM = 7;

    private VodListActivity act;

    public VideoItemListener(VodListActivity act) {
        super();
        this.act = act;
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == Constant.OK_BTN_KEYCODE && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            VideoImgItemView viv = (VideoImgItemView) view;
            Intent intent = new Intent(act, VodPlayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("coId", viv.getVideo().getCoId());
            bundle.putSerializable("videoId", viv.getVideo().getVideoId());
            intent.putExtras(bundle);

            // 存储历史记录
            StoreObjectUtils sou = new StoreObjectUtils(act, StoreObjectUtils.SP_VOD_HISTORY);
            Map<String, VideoBean> vodHisMap = sou.getMap(StoreObjectUtils.DATA_VOD_HISTORY, String.class, VideoBean.class);
            if(vodHisMap == null) {
                vodHisMap = new HashMap<String, VideoBean>();
            }
            vodHisMap.put(viv.getVideo().getVideoId(), viv.getVideo());
            sou.saveObject(StoreObjectUtils.DATA_VOD_HISTORY, vodHisMap);

            // 跳转页面
            act.startActivity(intent);
        }
        return false;
    }

    public void initItemFocus() {
        act.getmCurrentGroup().setNextFocusRightId(R.id.vod_list_right_first_id);
        List<VideoImgItemView> imgList = act.getVodListRightImgList();
        for(int i = 0; i < imgList.size(); i++) {
            VideoImgItemView viiv = imgList.get(i);
            if(i == 0) {
                viiv.setNextFocusLeftId(act.getmCurrentGroup().getId());
            } else  {
                viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
            }
            if(isEleExists(i - STEP)) {
                viiv.setNextFocusUpId(imgList.get(i - STEP).getId());
            } else {
                viiv.setNextFocusUpId(viiv.getId());
            }

            if(isEleExists(i + 1)) {
                viiv.setNextFocusRightId(imgList.get(i + 1).getId());
            } else {
                viiv.setNextFocusRightId(viiv.getId());
            }

            if(isEleExists(i + STEP)) {
                viiv.setNextFocusDownId(imgList.get(i + STEP).getId());
            } else {
                viiv.setNextFocusDownId(viiv.getId());
            }

        }

    }
    public void initItemFocus_() {
        act.getmCurrentGroup().setNextFocusRightId(R.id.vod_list_right_first_id);
        List<VideoImgItemView> imgList = act.getVodListRightImgList();
        for(int i = 0; i < imgList.size(); i++) {
            int posType = getPosType(i);
            VideoImgItemView viiv = imgList.get(i);
            switch (posType) {
                case LEFT_TOP:
                    viiv.setNextFocusLeftId(act.getmCurrentGroup().getId());
                    viiv.setNextFocusUpId(viiv.getId());
                    if(isEleExists(i + 1)) {
                        viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    } else {
                        viiv.setNextFocusRightId(viiv.getId());
                    }
                    if(isEleExists(i + STEP)) {
                        viiv.setNextFocusDownId(imgList.get(i + STEP).getId());
                    } else {
                        viiv.setNextFocusDownId(viiv.getId());
                    }
                    break;

                case RIGHT_TOP:
                    viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
                    viiv.setNextFocusUpId(viiv.getId());
                    if(isEleExists(i + 1)) {
                        viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    } else {
                        viiv.setNextFocusRightId(viiv.getId());
                    }
                    if(isEleExists(i + STEP)) {
                        viiv.setNextFocusDownId(imgList.get(i + STEP).getId());
                    } else {
                        viiv.setNextFocusDownId(viiv.getId());
                    }
                    break;

                case LEFT_BOTTOM:
                    viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
                    viiv.setNextFocusUpId(imgList.get(i - STEP).getId());
                    if(isEleExists(i + 1)) {
                        viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    } else {
                        viiv.setNextFocusRightId(viiv.getId());
                    }
                    viiv.setNextFocusDownId(viiv.getId());
                    break;

                case LEFT:
                    viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
                    viiv.setNextFocusUpId(imgList.get(i - STEP).getId());
                    viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    viiv.setNextFocusDownId(imgList.get(i + STEP).getId());
                    break;

                case TOP:
                    viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
                    viiv.setNextFocusUpId(viiv.getId());
                    if(isEleExists(i + 1)) {
                        viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    } else {
                        viiv.setNextFocusUpId(viiv.getId());
                    }
                    if(isEleExists(i + STEP)) {
                        viiv.setNextFocusDownId(imgList.get(i + STEP).getId());
                    } else {
                        viiv.setNextFocusDownId(viiv.getId());
                    }
                    break;

                case RIGHT:
                    viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
                    viiv.setNextFocusUpId(imgList.get(i - STEP).getId());
                    if(isEleExists(i + 1))  {
                        viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    } else {
                        viiv.setNextFocusDownId(viiv.getId());
                    }
                    if(isEleExists(i + STEP)) {
                        viiv.setNextFocusDownId(imgList.get(i + STEP).getId());
                    } else {
                        viiv.setNextFocusDownId(viiv.getId());
                    }
                    break;

                case BOTTOM:
                    viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
                    viiv.setNextFocusUpId(imgList.get(i - STEP).getId());
                    if(isEleExists(i + 1))  {
                        viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    } else {
                        viiv.setNextFocusDownId(viiv.getId());
                    }
                    viiv.setNextFocusDownId(viiv.getId());
                    break;
                case CENTER:
                    viiv.setNextFocusLeftId(imgList.get(i - 1).getId());
                    viiv.setNextFocusUpId(imgList.get(i - STEP).getId());
                    viiv.setNextFocusRightId(imgList.get(i + 1).getId());
                    if(isEleExists(i + STEP)) {
                        viiv.setNextFocusUpId(imgList.get(i + STEP).getId());
                    } else {
                        viiv.setNextFocusDownId(viiv.getId());
                    }
                    break;
            }
        }

    }

    /**
     * 通过下标获取元素的位置
     * @param index
     * @return
     */
    private int getPosType(int index) {
        int step = STEP;
        int result = CENTER;
        int total = act.getVodListRightImgList().size();
        int mode = total % step;

        if(index % step == 0) {
            result = LEFT;
        }

        if(index < step) {
            result = TOP;
        }

        if((index + 1) % step == 0) {
            result = RIGHT;
        }

        if(index < total && index >= (total - mode)) {
            result = BOTTOM;
            // 只有一行的情况
            if(total <= STEP) {
                result = TOP;
            }
        }

        if(index == 0) {
            result = LEFT_TOP;
        }
        if(index == step - 1) {
            result = RIGHT_TOP;
        }

        if((total % STEP == 0 && index == (total - STEP)) ||  index == (total - mode) ) {
            result = LEFT_BOTTOM;
            // 只有一行的情况
            if(total <= STEP) {
                result = LEFT_TOP;
            }
        }
        return result;
    }

    /**
     * 通过下标判断元素是否存在
     * @param index
     * @return
     */
    private boolean isEleExists(int index) {
        boolean reuslt = false;
        if(index >= 0 && index < act.getVodListRightImgList().size()) {
            reuslt = true;
        }
        return reuslt;
    }

}
