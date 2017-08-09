/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.VideoBean;
import com.szreach.ybolotvbox.listener.VideoHisItemListener;
import com.szreach.ybolotvbox.utils.StoreObjectUtils;
import com.szreach.ybolotvbox.views.MoveFrameLayout;
import com.szreach.ybolotvbox.views.VideoImgItemView;
import com.szreach.ybolotvbox.views.VideoItemView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VodHisListActivity extends Activity {
    private LinearLayout vodHisListPage;
    private FlexboxLayout vodHisListRightLayout;
    private TextView countTextView;
    private MoveFrameLayout mMainMoveFrame;
    private View mCurrentVideo;
    private List<VideoImgItemView> vodHisListRightImgList;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.vod_his_list_activity);
        vodHisListPage = (LinearLayout) findViewById(R.id.vod_his_list_page);
        countTextView = (TextView) findViewById(R.id.vod_his_list_count);
        vodHisListRightLayout = (FlexboxLayout) findViewById(R.id.vod_his_list_right_list);
        vodHisListRightImgList = new ArrayList<VideoImgItemView>();
        mMainMoveFrame = (MoveFrameLayout) findViewById(R.id.vod_his_list_page_move);
        mMainMoveFrame.setUpRectResource(R.drawable.conner_vod);
        mMainMoveFrame.setTranDurAnimTime(400);

        vodHisListPage.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                // 视频封面获得焦点
                if (newFocus instanceof VideoImgItemView) {
                    // 视频封面获取焦点
                    mMainMoveFrame.setDrawUpRectEnabled(true);
                    float scale = 1.015f;
                    mMainMoveFrame.setFocusView(newFocus, mCurrentVideo, scale);
                    mMainMoveFrame.bringToFront();
                    mCurrentVideo = newFocus;
                }
            }
        });

        initCenterLayout();
    }

    private void initCenterLayout() {
        vodHisListRightLayout.removeAllViews();

        StoreObjectUtils sou = new StoreObjectUtils(this, StoreObjectUtils.SP_VOD_HISTORY);
        Map<String, VideoBean> vodHisMap = sou.getMap(StoreObjectUtils.DATA_VOD_HISTORY, String.class, VideoBean.class);

        if (vodHisMap != null && vodHisMap.size() > 0) {
            countTextView.setText("共计" + vodHisMap.size() +  "个视频");

            Iterator<Map.Entry<String, VideoBean>> vodEntrys = vodHisMap.entrySet().iterator();
            while (vodEntrys.hasNext()) {
                VideoBean vb = vodEntrys.next().getValue();
                VideoImgItemView videoImg = new VideoImgItemView(this, vb);
                vodHisListRightImgList.add(videoImg);
                VideoItemView viv = new VideoItemView(this, vb, videoImg);
                vodHisListRightLayout.addView(viv);
            }

            // 初始化点击事件
            for (int i = 0; i < vodHisListRightImgList.size(); i++) {
                VideoImgItemView imageView = vodHisListRightImgList.get(i);
                imageView.setOnKeyListener(new VideoHisItemListener(this));
            }
        } else {
            countTextView.setText("共计0个视频");
        }

    }

    public LinearLayout getVodHisListPage() {
        return vodHisListPage;
    }

    public void setVodHisListPage(LinearLayout vodHisListPage) {
        this.vodHisListPage = vodHisListPage;
    }

    public FlexboxLayout getVodHisListRightLayout() {
        return vodHisListRightLayout;
    }

    public void setVodHisListRightLayout(FlexboxLayout vodHisListRightLayout) {
        this.vodHisListRightLayout = vodHisListRightLayout;
    }

    public TextView getCountTextView() {
        return countTextView;
    }

    public void setCountTextView(TextView countTextView) {
        this.countTextView = countTextView;
    }

    public MoveFrameLayout getmMainMoveFrame() {
        return mMainMoveFrame;
    }

    public void setmMainMoveFrame(MoveFrameLayout mMainMoveFrame) {
        this.mMainMoveFrame = mMainMoveFrame;
    }

    public View getmCurrentVideo() {
        return mCurrentVideo;
    }

    public void setmCurrentVideo(View mCurrentVideo) {
        this.mCurrentVideo = mCurrentVideo;
    }

    public List<VideoImgItemView> getVodHisListRightImgList() {
        return vodHisListRightImgList;
    }

    public void setVodHisListRightImgList(List<VideoImgItemView> vodHisListRightImgList) {
        this.vodHisListRightImgList = vodHisListRightImgList;
    }
}
