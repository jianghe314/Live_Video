package com.szreach.ybolotv.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.szreach.ybolotv.activities.MainActivity;

/**
 * Created by Adams.Tsui on 2017/8/8 0008.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mBootIntent = new Intent(context, MainActivity.class);
        mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mBootIntent);
    }
}
