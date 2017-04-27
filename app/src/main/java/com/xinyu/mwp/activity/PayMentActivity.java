package com.xinyu.mwp.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.entity.UnionPayReturnEntity;
import com.xinyu.mwp.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;

/**
 * 调用第三方支付 加载url页面
 * Created by Administrator on 2017/4/24.
 */
public class PayMentActivity extends BaseControllerActivity {
    @ViewInject(R.id.wv_payment)
    WebView webView;

    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("银行卡充值");
        Bundle bundle = getIntent().getBundleExtra("pay");
        UnionPayReturnEntity entity = (UnionPayReturnEntity) bundle.getSerializable("payment");
        if (entity != null) {
            final String url = entity.getPaymentInfo();
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        LogUtil.d("发送消息,关闭页面");
        EventBus.getDefault().postSticky(new EventBusMessage(-10));    //发送广播
        super.onDestroy();
    }

//    @Override
//    public void initStatusBar() {
////        StatusBarUtil.setColor(this, getResources().getColor(R.color.default_main_color), 0);
//        StatusBarUtil.setColor(this, 50);
//    }
}
