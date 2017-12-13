package com.szreach.ybolotv.views;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Adams.Tsui on 2017/07/28.
 */

public class DateTimeTextView extends AppCompatTextView {
    private Runnable runnable;
    private Handler handler;

    public DateTimeTextView(Context context) {
        super(context);
    }

    public DateTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                setText(getCurrentDateInfo());
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                handler.postAtTime(runnable, next);
            }
        };
        runnable.run();
    }

    private String getCurrentDateInfo() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        return getDateStr(year, month, day) + " " + getWeekStr(week) + " " + getTimeStr(hour, minute);
    }

    private String getDateStr(int year, int month, int day) {
        return year + "年" + month + "月" + day + "日";
    }

    private String getWeekStr(int week) {
        String[] weeks = new String[] {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weeks[week - 1];
    }

    private String getTimeStr(int hour, int minute, int second) {
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    }

    private String getTimeStr(int hour, int minute) {
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

}
