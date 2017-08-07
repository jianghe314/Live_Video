/**
 * Adams.Tsui 2017.07.23
 */

package com.szreach.ybolotvbox.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.szreach.ybolotvbox.R;
import com.szreach.ybolotvbox.beans.NewsBean;
import com.szreach.ybolotvbox.views.YBoloWebViewClient;

import io.vov.vitamio.Vitamio;

public class NewsPlayActivity extends Activity {
    private WebView webView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Vitamio.isInitialized(getApplicationContext());

        setContentView(R.layout.news_play_activity);
        webView = (WebView) findViewById(R.id.news_play_view);
        webView.setWebViewClient(new YBoloWebViewClient());

        Intent intent = this.getIntent();
        NewsBean news = (NewsBean) intent.getSerializableExtra("news");

//        webView.loadUrl(news.getNewsUrl());
    }
}
