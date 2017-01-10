package com.xinyu.mwp.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.networkapi.NetworkAPIException;
import com.xinyu.mwp.swipeback.ScrollFinishListener;
import com.xinyu.mwp.swipeback.SwipeBackFragmentActivity;
import com.xinyu.mwp.swipeback.SwipeBackLayout;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.LoaderLayout;
import com.xinyu.mwp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-06-17 10:55
 * @describe : BaseFragmentActivity
 * @for your attention : none
 * @revise : none
 */
public abstract class BaseFragmentActivity extends SwipeBackFragmentActivity {

    @Nullable
    @ViewInject(R.id.container)
    protected View container;
    @Nullable
    @ViewInject(R.id.titleText)
    protected TextView titleText;
    @Nullable
    @ViewInject(R.id.leftImage)
    protected ImageButton leftImage;
    @Nullable
    @ViewInject(R.id.rightImage)
    protected ImageButton rightImage;
    @Nullable
    @ViewInject(R.id.rightText)
    protected TextView rightText;
    @Nullable
    @ViewInject(R.id.leftText)
    protected TextView leftText;

    protected Context context;
    protected FragmentManager fragmentManager;
    protected View rootView;

    protected LoaderLayout loaderLayout;

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        rootView = LayoutInflater.from(this).inflate(getLayoutID(), null);
        setContentView(rootView);
        fragmentManager = this.getSupportFragmentManager();
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

    /**
     * 设置标题
     *
     * @param resid 标题资源id
     */
    @Override
    public void setTitle(int resid) {
        if (titleText != null)
            titleText.setText(resid);
    }

    public void setTitle(String titleString) {
        if (titleText != null)
            titleText.setText(titleString);
    }

    protected abstract int getLayoutID();

    protected void onInit() {
        x.view().inject(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    /**
     * 初始化 view
     */
    protected void initView() {
        container = rootView.findViewById(R.id.container);
        titleText = (TextView) rootView.findViewById(R.id.titleText);
        leftImage = (ImageButton) rootView.findViewById(R.id.leftImage);
        rightImage = (ImageButton) rootView.findViewById(R.id.rightImage);
        rightText = (TextView) rootView.findViewById(R.id.rightText);
        leftText = (TextView) rootView.findViewById(R.id.leftText);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        mSwipeBackLayout.setScrollFinishListener(new ScrollFinishListener() {
            @Override
            public void scrollFinish() {
                beforeFinish();
            }
        });
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().unregister(this);
    }

    public View getRootView() {
        return rootView;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    public void showToast(@NonNull CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    public void back() {
        scrollToFinishActivity();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void beforeFinish() {

    }

    public void next(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
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
