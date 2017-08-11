/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.VideoBean;
import com.szreach.ybolotv.listener.VideoHisItemListener;
import com.szreach.ybolotv.utils.Constant;
import com.szreach.ybolotv.utils.StoreObjectUtils;
import com.szreach.ybolotv.views.MoveFrameLayout;
import com.szreach.ybolotv.views.VideoImgItemView;
import com.szreach.ybolotv.views.VideoItemView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VodHisListActivity extends Activity {
    private LinearLayout vodHisListPage;
    private FlexboxLayout vodHisListRightLayout;
    private TextView countTextView;
    private MoveFrameLayout mMainMoveFrame;
    private View mCurrentFocus;
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
                    mMainMoveFrame.setFocusView(newFocus, mCurrentFocus, scale);
                    mMainMoveFrame.bringToFront();
                    mCurrentFocus = newFocus;

                    if(oldFocus instanceof TextView) {
                        ((TextView) oldFocus).setTextColor(0xff8e8e8e);
                    }
                }

                if (newFocus instanceof TextView) {
                    mMainMoveFrame.setVisibility(View.INVISIBLE);
                    ((TextView) newFocus).setTextColor(0xffee0000);
                }
            }
        });

        initCenterLayout();

        new VideoHisItemListener(this).initItemFocus();

        findViewById(R.id.vod_his_clear).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == Constant.OK_BTN_KEYCODE && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    new AlertDialog.Builder(VodHisListActivity.this)
                            .setTitle("警告")
                            .setMessage("确定要清空观看历史记录？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    StoreObjectUtils sou = new StoreObjectUtils(VodHisListActivity.this, StoreObjectUtils.SP_VOD_HISTORY);
                                    sou.saveObject(StoreObjectUtils.DATA_VOD_HISTORY, null);
                                    vodHisListRightLayout.removeAllViews();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                }
                return false;
            }
        });
    }

    private void initCenterLayout() {
        vodHisListRightLayout.removeAllViews();

        StoreObjectUtils sou = new StoreObjectUtils(this, StoreObjectUtils.SP_VOD_HISTORY);
        Map<String, VideoBean> vodHisMap = sou.getMap(StoreObjectUtils.DATA_VOD_HISTORY, String.class, VideoBean.class);

        if (vodHisMap != null && vodHisMap.size() > 0) {
            countTextView.setText("共计" + vodHisMap.size() +  "个视频");

            Iterator<Map.Entry<String, VideoBean>> vodEntrys = vodHisMap.entrySet().iterator();
            int ind = 0;
            while (vodEntrys.hasNext()) {
                VideoBean vb = vodEntrys.next().getValue();
                VideoImgItemView videoImg = new VideoImgItemView(this, vb);
                videoImg.setId(R.id.vod_list_right_first_id + ind);
                vodHisListRightImgList.add(videoImg);
                VideoItemView viv = new VideoItemView(this, vb, videoImg);
                vodHisListRightLayout.addView(viv);
                ind++;
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

    public View getmCurrentFocus() {
        return mCurrentFocus;
    }

    public void setmCurrentFocus(View mCurrentFocus) {
        this.mCurrentFocus = mCurrentFocus;
    }

    public List<VideoImgItemView> getVodHisListRightImgList() {
        return vodHisListRightImgList;
    }

    public void setVodHisListRightImgList(List<VideoImgItemView> vodHisListRightImgList) {
        this.vodHisListRightImgList = vodHisListRightImgList;
    }
}
