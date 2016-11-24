package com.skyzone.gank.Detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skyzone.gank.Data.Bean.Info;
import com.skyzone.gank.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Skyzone on 11/24/2016.
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.activity_detail_toolbar)
    Toolbar mActivityDetailToolbar;
    @BindView(R.id.activity_detail_web_view)
    WebView mActivityDetailWebView;

    Info mInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mActivityDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mInfo = getIntent().getParcelableExtra("info");
        getSupportActionBar().setTitle(mInfo.getDesc());

        mActivityDetailWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings webSettings = mActivityDetailWebView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        mActivityDetailWebView.loadUrl(mInfo.getUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DetailActivity.this.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
