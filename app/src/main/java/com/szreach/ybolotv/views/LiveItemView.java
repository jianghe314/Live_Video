package com.szreach.ybolotv.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.activities.LiveListActivity;
import com.szreach.ybolotv.activities.LivePlayActivity;
import com.szreach.ybolotv.beans.LiveBean;

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
            this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.setOrientation(LinearLayout.VERTICAL);
            if(liveInfo.getLiveFlag() == 1) {
                this.setFocusable(true);
                this.setFocusableInTouchMode(true);
                this.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) && keyEvent.getAction() == KeyEvent.ACTION_UP) {
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

            LayoutParams upLp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            upLayout = new LinearLayout(context);
            upLayout.setLayoutParams(upLp);
            upLayout.setOrientation(LinearLayout.HORIZONTAL);

            liveNameView = new TextView(context);
            LayoutParams liveNameLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            liveNameLp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x10), getResources().getDimensionPixelOffset(R.dimen.y8), getResources().getDimensionPixelOffset(R.dimen.x3), getResources().getDimensionPixelOffset(R.dimen.y3));
            liveNameView.setLayoutParams(liveNameLp);
            liveNameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.x7));
            liveNameView.setText(liveInfo.getLiveName());
            liveNameView.getPaint().setFakeBoldText(true);
            if(liveInfo.getLiveFlag() == 1) {
                liveNameView.setTextColor(0xffffffff);
            } else {
                liveNameView.setTextColor(0xff868686);
            }

            upLayout.addView(liveNameView);

            LayoutParams downLp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            downLayout = new LinearLayout(context);
            downLayout.setLayoutParams(downLp);
            downLayout.setOrientation(LinearLayout.HORIZONTAL);

            liveTimeView = new TextView(context);
            LayoutParams liveTimeLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            liveTimeLp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x10), 0, getResources().getDimensionPixelOffset(R.dimen.x3), getResources().getDimensionPixelOffset(R.dimen.y8));
            liveTimeView.setLayoutParams(liveTimeLp);
            liveTimeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.x6));
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
            liveFlagLp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x20), 0, 0, getResources().getDimensionPixelOffset(R.dimen.y8));
            liveflagView.setLayoutParams(liveFlagLp);
            liveflagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.x6));
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
