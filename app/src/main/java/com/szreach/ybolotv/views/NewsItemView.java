package com.szreach.ybolotv.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.beans.NewsBean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Adams.Tsui on 2017/7/28 0028.
 */

public class NewsItemView extends LinearLayout {
    private Activity act;
    private NewsBean news;
    private ImageView imageView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            Bitmap bitMap = data.getParcelable("bitMap");
            getImageView().setImageBitmap(bitMap);
        }
    };

    public NewsItemView(Context context, final NewsBean news) {
        super(context);

        this.act = (Activity) context;
        this.news = news;

        if (news != null) {
            LayoutParams lp = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x195), getResources().getDimensionPixelOffset(R.dimen.x40));
            lp.gravity = Gravity.TOP;
            this.setLayoutParams(lp);
            this.setOrientation(LinearLayout.HORIZONTAL);
            this.setFocusable(true);
            this.setFocusableInTouchMode(true);

            // 左边区域
            LayoutParams lLp = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x60), ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout lll = new LinearLayout(context);
            lll.setLayoutParams(lLp);
            lll.setOrientation(HORIZONTAL);

            LayoutParams imageLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(imageLp);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            lll.addView(imageView);
            this.imageView = imageView;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        URL url = new URL(news.getThumbnail());
                        Bitmap bitMap = BitmapFactory.decodeStream(url.openStream());
                        data.putParcelable("bitMap", bitMap);
                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();


            // 右边区域
            LayoutParams rLp = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x100), ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout rll = new LinearLayout(context);
            rll.setLayoutParams(rLp);
            rll.setOrientation(VERTICAL);

            LayoutParams titleLp = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x100), ViewGroup.LayoutParams.WRAP_CONTENT);
            titleLp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x5), getResources().getDimensionPixelOffset(R.dimen.x5), 0, 0);
            TextView title = new TextView(context);
            title.setLayoutParams(titleLp);
            title.setText(news.getTitle());
            title.setTextColor(0xffeeeeee);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelOffset(R.dimen.x9));
            rll.addView(title);

            LayoutParams timeLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            timeLp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x5), 0, 0, 0);
            TextView time = new TextView(context);
            time.setLayoutParams(timeLp);
            time.setText(news.getCreateTime());
            time.setTextColor(0xffeeeeee);
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelOffset(R.dimen.x6));
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

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
