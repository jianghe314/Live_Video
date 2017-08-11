package com.szreach.ybolotv.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.szreach.ybolotv.beans.NewsBean;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class NewsConItemView extends LinearLayout {
    private NewsBean news;
    private NewsItemView newsItemView;

    public NewsConItemView(Context context, NewsItemView newsItemView, NewsBean news) {
        super(context);

        this.news = news;
        this.newsItemView = newsItemView;

        if(news != null) {
            LayoutParams lp = new LayoutParams(596, 160);
            this.setLayoutParams(lp);
            this.setOrientation(LinearLayout.HORIZONTAL);

            this.addView(newsItemView);
        }
    }

}
