package com.szreach.ybolotv.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.VodGroupBean;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class VodGroupItemView extends AppCompatTextView {
    private VodGroupBean vodGroup;

    public VodGroupItemView(final Context context, VodGroupBean group) {
        super(context);
        this.vodGroup = group;
        if(group != null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.x5), 0, getResources().getDimensionPixelOffset(R.dimen.x4));
            this.setLayoutParams(lp);
            this.setText(group.getGroupName());
            this.setLetterSpacing(-0.01f);
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.x10));
            this.setTextColor(0xffc0c0c0);
            this.setFocusable(true);
            this.setFocusableInTouchMode(true);
        }
    }

    public VodGroupBean getVodGroup() {
        return vodGroup;
    }

    public void setVodGroup(VodGroupBean vodGroup) {
        this.vodGroup = vodGroup;
    }
}
