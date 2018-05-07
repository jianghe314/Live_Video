/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotv.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.utils.DataService;
import com.szreach.ybolotv.views.YBoloWebViewClient;

public class NewsPlayActivity extends Activity {
    private WebView webView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.news_play_activity);
        webView = (WebView) findViewById(R.id.news_play_view);
        webView.setWebViewClient(new YBoloWebViewClient());

        new DataThread(this.getIntent(), this.handler).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String newsPath = (String) bundle.getCharSequence("newsPath");
            Log.d("dd", newsPath + "");
            webView.loadUrl(newsPath);
        }
    };

    private class DataThread extends Thread {
        private Intent intent;
        private Handler handler;

        public DataThread(Intent intent, Handler handler) {
            this.intent = intent;
            this.handler = handler;
        }

        @Override
        public void run() {
            Bundle bundle = intent.getExtras();
            long coId = (long) bundle.getSerializable("coId");
            String nnId = (String) bundle.getSerializable("nnId");
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putCharSequence("newsPath", DataService.getInstance().getNewsUrl(coId, nnId));
            msg.setData(data);
            handler.sendMessage(msg);
        }
    }
}
