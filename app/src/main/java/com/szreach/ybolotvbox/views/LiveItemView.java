package com.szreach.ybolotvbox.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotvbox.activities.LiveListActivity;
import com.szreach.ybolotvbox.activities.LivePlayActivity;
import com.szreach.ybolotvbox.beans.LiveBean;
import com.szreach.ybolotvbox.utils.Constant;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class LiveItemView extends LinearLayout {
    private LinearLayout upLayout;
    private LinearLayout downLayout;
    private TextView liveNameView;
    private TextView liveTimeView;
    private TextView liveflagView;
    private LiveBean liveInfo;

    public LiveItemView(final Context context, final LiveBean liveInfo) {
        super(context);
        this.liveInfo = liveInfo;

        if (liveInfo != null) {
            this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 123));
            this.setOrientation(LinearLayout.VERTICAL);
            if(liveInfo.getLiveFlag() == 1) {
                this.setFocusable(true);
                this.setFocusableInTouchMode(true);
                this.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if(keyCode == Constant.OK_BTN_KEYCODE && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            LiveListActivity act = (LiveListActivity) context;

                            act.getVideoView().stopPlayback();
                            Intent intent = new Intent(context, LivePlayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("coId", getLiveInfo().getCoId());
                            bundle.putSerializable("liveId", getLiveInfo().getLiveId());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                        return false;
                    }
                });
            }

            LayoutParams upLp = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 116);
            upLayout = new LinearLayout(context);
            upLayout.setLayoutParams(upLp);
            upLayout.setOrientation(LinearLayout.HORIZONTAL);

            liveNameView = new TextView(context);
            LayoutParams liveNameLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            liveNameLp.setMargins(20, 20, 13, 0);
            liveNameView.setLayoutParams(liveNameLp);
            liveNameView.setTextSize(22.4f);
            liveNameView.setText(liveInfo.getLiveName());
            liveNameView.getPaint().setFakeBoldText(true);
            if(liveInfo.getLiveFlag() == 1) {
                liveNameView.setTextColor(0xffffffff);
            } else {
                liveNameView.setTextColor(0xff868686);
            }

            upLayout.addView(liveNameView);

            LayoutParams downLp = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 70);
            downLayout = new LinearLayout(context);
            downLayout.setLayoutParams(downLp);
            downLayout.setOrientation(LinearLayout.HORIZONTAL);

            liveTimeView = new TextView(context);
            LayoutParams liveTimeLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            liveTimeLp.setMargins(20, 10, 0, 0);
            liveTimeView.setLayoutParams(liveTimeLp);
            liveTimeView.setTextSize(18f);
            liveTimeView.setText(liveInfo.getLiveDateTimeStr());
            liveTimeView.getPaint().setFakeBoldText(true);
            if(liveInfo.getLiveFlag() == 1) {
                liveTimeView.setTextColor(0xffffffff);
            } else {
                liveTimeView.setTextColor(0xff868686);
            }

            downLayout.addView(liveTimeView);

            liveflagView = new TextView(context);
            LayoutParams liveFlagLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            liveFlagLp.setMargins(77, 8, 0, 0);
            liveflagView.setLayoutParams(liveFlagLp);
            liveflagView.setTextSize(15f);
            liveflagView.setTextColor(0xff0084fd);
            liveflagView.setText(liveInfo.getLiveFlagStr());
            liveflagView.getPaint().setFakeBoldText(true);

            downLayout.addView(liveflagView);

            this.addView(upLayout);
            this.addView(downLayout);
        }

    }
    public TextView getLiveNameView() {
        return liveNameView;
    }

    public void setLiveNameView(TextView liveNameView) {
        this.liveNameView = liveNameView;
    }

    public TextView getLiveTimeView() {
        return liveTimeView;
    }

    public void setLiveTimeView(TextView liveTimeView) {
        this.liveTimeView = liveTimeView;
    }

    public TextView getLiveflagView() {
        return liveflagView;
    }

    public void setLiveflagView(TextView liveflagView) {
        this.liveflagView = liveflagView;
    }

    public LiveBean getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(LiveBean liveInfo) {
        this.liveInfo = liveInfo;
    }
}
