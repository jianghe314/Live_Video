package com.szreach.ybolotvbox.views;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.NewsBean;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class NewsItemView extends LinearLayout {
    private Activity act;
    private NewsBean news;

    public NewsItemView(Context context, NewsBean news) {
        super(context);

        this.act = (Activity) context;
        this.news = news;

        if(news != null) {
            LayoutParams lp = new LayoutParams(543, 120);
            lp.gravity = Gravity.TOP;
            this.setLayoutParams(lp);
            this.setOrientation(LinearLayout.HORIZONTAL);
            this.setFocusable(true);
            this.setFocusableInTouchMode(true);

            // 左边区域
            LayoutParams lLp = new LayoutParams(154, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout lll = new LinearLayout(context);
            lll.setLayoutParams(lLp);
            lll.setOrientation(HORIZONTAL);

            LayoutParams imageLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(imageLp);
//            imageView.setImageURI();
            imageView.setImageResource(R.drawable.news_img);

            lll.addView(imageView);


            // 右边区域
            LayoutParams rLp = new LayoutParams(389, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout rll = new LinearLayout(context);
            rll.setLayoutParams(rLp);
            rll.setOrientation(VERTICAL);

            LayoutParams titleLp = new LayoutParams(342, 74);
            titleLp.setMargins(20, 14, 0, 0);
            TextView title = new TextView(context);
            title.setLayoutParams(titleLp);
            title.setText(news.getNewsTitle());
            title.setTextColor(0xffeeeeee);
            title.setTextSize(22.3f);
            rll.addView(title);

            LayoutParams timeLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 47);
            timeLp.setMargins(20, 0, 0, 0);
            TextView time = new TextView(context);
            time.setLayoutParams(timeLp);
            time.setText(news.getNewsDatetime());
            time.setTextColor(0xffeeeeee);
            time.setTextSize(19.4f);
            time.setLetterSpacing(0.05f);
            rll.addView(time);

            this.addView(lll);
            this.addView(rll);
        }
    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }
}
