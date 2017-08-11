/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.VideoBean;
import com.szreach.ybolotv.beans.VodGroupBean;
import com.szreach.ybolotv.listener.VideoItemListener;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.views.MoveFrameLayout;
import com.szreach.ybolotv.views.VideoImgItemView;
import com.szreach.ybolotv.views.VideoItemView;
import com.szreach.ybolotv.views.VodGroupItemView;

import java.util.ArrayList;
import java.util.List;

public class VodListActivity extends Activity {
    private LinearLayout vodListPage;
    private LinearLayout vodListGroups;
    private FlexboxLayout vodListRightLayout;
    private TextView countTextView;
    private MoveFrameLayout mMainMoveFrame;
    private View mCurrentGroup;
    private View mCurrentVideo;
    private List<VideoImgItemView> vodListRightImgList;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.vod_list_activity);
        vodListPage = (LinearLayout) findViewById(R.id.vod_list_page);
        vodListGroups = (LinearLayout) findViewById(R.id.vod_list_groups);
        vodListRightLayout = (FlexboxLayout) findViewById(R.id.vod_list_right_list);
        countTextView = (TextView) findViewById(R.id.vod_list_count);
        vodListRightImgList = new ArrayList<VideoImgItemView>();
        mMainMoveFrame = (MoveFrameLayout) findViewById(R.id.vod_list_page_move);
        mMainMoveFrame.setUpRectResource(R.drawable.conner_vod);
        mMainMoveFrame.setTranDurAnimTime(400);

        vodListPage.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                // 视频分组获得焦点
                if (newFocus instanceof VodGroupItemView) {
                    VodGroupItemView target = (VodGroupItemView) newFocus;
                    target.setTextColor(0xff0083fd);

                    mCurrentGroup = newFocus;
                    // 上个焦点是视频分组
                    if (oldFocus != null && oldFocus instanceof VodGroupItemView) {
                        VodGroupItemView oldTarget = (VodGroupItemView) oldFocus;
                        oldTarget.setTextColor(0xffc0c0c0);
                    }

                    // 上个焦点是视频封面
                    if (oldFocus != null && oldFocus instanceof VideoImgItemView) {
                        mCurrentGroup.requestFocus();
                        mMainMoveFrame.setVisibility(LinearLayout.INVISIBLE);
                    }

                    // 根据分组ID查询视频列表
                    VodGroupItemView newTarget = (VodGroupItemView) newFocus;
                    final long groupId = newTarget.getVodGroup().getGroupId();

                    final Handler mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();
                            ArrayList<VideoBean> videoList = data.getParcelableArrayList("videoList");
                            countTextView.setText("共计" + videoList.size() + "个视频");
                            updateRightLayout(videoList);
                        }
                    };

                    new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putParcelableArrayList("videoList", DataService.getInstance().getVideopListByGroupId(groupId));
                            msg.setData(data);
                            mHandler.sendMessage(msg);
                        }
                    }.start();

                }

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

        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                ArrayList<VodGroupBean> vodGroupBeanArrayList = data.getParcelableArrayList("groupList");
                if (vodGroupBeanArrayList != null && vodGroupBeanArrayList.size() > 0) {
                    int groupUUID = (int)(Math.random() * 10000000);
                    for (int i = 0; i < vodGroupBeanArrayList.size(); i++) {
                        VodGroupBean vgb = vodGroupBeanArrayList.get(i);
                        VodGroupItemView vg = new VodGroupItemView(VodListActivity.this, vgb);
                        vg.setId(groupUUID + i);
                        vodListGroups.addView(vg);

                        if (i == 0) {
                            vg.requestFocus();
                        }

                        if (i == vodGroupBeanArrayList.size() - 1) {
                            vg.setId(R.id.vod_list_group_last_id);
                            vg.setNextFocusDownId(vg.getId());
                        }
                    }
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putParcelableArrayList("groupList", DataService.getInstance().getVodGroupList());
                msg.setData(data);
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    private void updateRightLayout(List<VideoBean> videoList) {
        vodListRightLayout.removeAllViews();
        vodListRightImgList.clear();

        if (videoList != null && videoList.size() > 0) {
            for (int i = 0; i < videoList.size(); i++) {
                VideoBean vb = videoList.get(i);

                VideoImgItemView videoImg = new VideoImgItemView(this, vb);
                vodListRightImgList.add(videoImg);

                VideoItemView viv = new VideoItemView(this, vb, videoImg);
                vodListRightLayout.addView(viv);
            }

            // 初始化方向键, 点击事件
            for (int i = 0; i < vodListRightImgList.size(); i++) {
                VideoImgItemView imageView = vodListRightImgList.get(i);
                imageView.setId(R.id.vod_list_right_first_id + i);
                imageView.setOnKeyListener(new VideoItemListener(this));
            }
            new VideoItemListener(this).initItemFocus();
        }
    }

    public LinearLayout getVodListPage() {
        return vodListPage;
    }

    public void setVodListPage(LinearLayout vodListPage) {
        this.vodListPage = vodListPage;
    }

    public LinearLayout getVodListGroups() {
        return vodListGroups;
    }

    public void setVodListGroups(LinearLayout vodListGroups) {
        this.vodListGroups = vodListGroups;
    }

    public FlexboxLayout getVodListRightLayout() {
        return vodListRightLayout;
    }

    public void setVodListRightLayout(FlexboxLayout vodListRightLayout) {
        this.vodListRightLayout = vodListRightLayout;
    }

    public MoveFrameLayout getmMainMoveFrame() {
        return mMainMoveFrame;
    }

    public void setmMainMoveFrame(MoveFrameLayout mMainMoveFrame) {
        this.mMainMoveFrame = mMainMoveFrame;
    }

    public View getmCurrentGroup() {
        return mCurrentGroup;
    }

    public void setmCurrentGroup(View mCurrentGroup) {
        this.mCurrentGroup = mCurrentGroup;
    }

    public View getmCurrentVideo() {
        return mCurrentVideo;
    }

    public void setmCurrentVideo(View mCurrentVideo) {
        this.mCurrentVideo = mCurrentVideo;
    }

    public List<VideoImgItemView> getVodListRightImgList() {
        return vodListRightImgList;
    }

    public void setVodListRightImgList(List<VideoImgItemView> vodListRightImgList) {
        this.vodListRightImgList = vodListRightImgList;
    }

}
