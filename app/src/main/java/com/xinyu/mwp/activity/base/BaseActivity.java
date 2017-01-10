package com.xinyu.mwp.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.networkapi.NetworkAPIException;
import com.xinyu.mwp.swipeback.ScrollFinishListener;
import com.xinyu.mwp.swipeback.SwipeBackActivity;
import com.xinyu.mwp.swipeback.SwipeBackLayout;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.LoaderLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-06-05 11:21
 * @describe : BaseActivity
 * @for your attention : none
 * @revise : none
 */
public abstract class BaseActivity extends SwipeBackActivity {

    @Nullable
    @ViewInject(R.id.titleText)
    protected TextView titleText;
    @Nullable
    @ViewInject(R.id.container)
    protected View titleContainer;
    @Nullable
    @ViewInject(R.id.leftImage)
    protected ImageButton leftImage;
    @Nullable
    @ViewInject(R.id.leftText)
    protected TextView leftText;
    @Nullable
    @ViewInject(R.id.rightImage)
    protected ImageButton rightImage;
    @Nullable
    @ViewInject(R.id.rightImage1)
    protected ImageButton rightImage1;
    @Nullable
    @ViewInject(R.id.leftImageView)
    protected ImageView leftImageView;
    @Nullable
    @ViewInject(R.id.rightText)
    protected TextView rightText;
    @Nullable
    @ViewInject(R.id.container)
    protected View container;

    protected LoaderLayout loaderLayout;
    protected View rootView;
    protected Context context;

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        rootView = LayoutInflater.from(this).inflate(getContentView(), null);
        setContentView(rootView);
        MyApplication.getApplication().register(this);
        onInit();
        initView();
        initListener();
        initData();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void onInit() {
        x.view().inject(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }


    protected void onToastError(Throwable ex, String defError) {
        try {
            if (!isFinishing()) {
                ToastUtils.show(MyApplication.getApplication(), NetworkAPIException.formatException(ex, defError), 2000);
            }
            LogUtil.showException((Exception) ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onToastError(Throwable ex) {
        onToastError(ex, null);
        closeLoader();
    }


    public void showLoader() {
        showLoader(null);
    }

    public void showLoader(String msgContent) {
        try {
            if (loaderLayout == null) {
                loaderLayout = new LoaderLayout(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                loaderLayout.setLayoutParams(params);
                if (rootView != null) {
                    ((FrameLayout) rootView.getParent()).addView(loaderLayout);
                }
            }
            loaderLayout.setVisibility(View.VISIBLE);
            if (msgContent != null) {
                loaderLayout.setMsgContent(msgContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoader(int resId) {
        showLoader(getString(resId));
    }

    public void closeLoader() {
        if (loaderLayout != null)
            loaderLayout.setVisibility(View.GONE);
    }


    protected abstract int getContentView();

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        mSwipeBackLayout.setScrollFinishListener(new ScrollFinishListener() {
            @Override
            public void scrollFinish() {
                //退出之前的操作
                beforeFinish();
            }
        });
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 跳转到下一个Activity
     *
     * @param cls Activity class
     */
    public void next(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    /**
     * 跳转到下一个Activity并finish当前Activity
     *
     * @param cls Activity class
     */
    public void nextFinish(Class<? extends Activity> cls) {
        next(cls);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().unregister(this);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

    }

    @Override
    public void setTitle(int resid) {
        if (titleText != null)
            titleText.setText(resid);
    }

    public void setTitle(String titleString) {
        if (titleText != null)
            titleText.setText(titleString);
    }

    @Event(value = R.id.leftImage)
    private void clickLeftImage(View view) {
        back();
    }

    public void back() {
        scrollToFinishActivity();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    /**
     * 返回键，物理返回键，以及手势返回，统一在finish之前都会调用这个方法
     */
    public void beforeFinish() {
        LogUtil.e("beforeFinish");
    }

    public void showToast(@NonNull CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public View getRootView() {
        return rootView;
    }

    public void showRightImage(int resId) {
        if (rightImage != null) {
            rightImage.setVisibility(View.VISIBLE);
            if (resId != 0) {
                rightImage.setImageResource(resId);
            }
        }
    }
}