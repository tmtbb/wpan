package com.xinyu.mwp.activity;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.entity.IndexBannerEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/29.
 */
public class IndexBannerActivity extends BaseControllerActivity {
    @ViewInject(R.id.wv_banner)
    WebView bannerView;
    private WebViewClient mWebViewClient = new WebViewClient();

    @Override
    protected int getContentView() {
        return R.layout.activity_banner_webview;
    }

    @Override
    protected void initView() {
        super.initView();

        Intent intent = getIntent();
        IndexBannerEntity entity = (IndexBannerEntity) intent.getSerializableExtra("entity");
        if (entity != null) {
            setTitle(entity.getTitle());
            bannerView.loadUrl(entity.getJumpUrl());
            bannerView.setWebViewClient(mWebViewClient);
        }
    }
}
