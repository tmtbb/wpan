package com.xinyu.mwp.ui.activities;

import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.ui.fragments.ExchangeFragment;
import com.xinyu.mwp.ui.fragments.HomeFragment;
import com.xinyu.mwp.ui.fragments.ShareFragment;
import com.xinyu.mwp.utils.ToastUtil;
import com.xinyu.mwp.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;
    @BindView(R.id.view_head)
    CircleImageView mHead;
    @BindView(R.id.nav_bar)
    LinearLayout mNavBar;
    @BindView(R.id.home_layout)
    LinearLayout mHomeLayout;
    @BindView(R.id.exchange_layout)
    LinearLayout mExchangeLayout;
    @BindView(R.id.share_layout)
    LinearLayout mShareLayout;

    public static HomeFragment mHomeFragment;
    public static ExchangeFragment mExchangeFragment;
    public static ShareFragment mShareFragment;
    private List<View> mViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActinbar(mToolBar, mDrawer);
    }

    @Override
    public void initActinbar(Toolbar toolbar, DrawerLayout drawer) {
        super.initActinbar(toolbar, drawer);
        mToolbarTitle.setText("喵仔微盘");
        mToolBar.setNavigationIcon(R.mipmap.ic_toolbar_menu);
    }

    @Override
    public void initView() {
        mHomeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mHomeFragment).commitAllowingStateLoss();
        mHomeLayout.setSelected(true);
        mViews.add(mHomeLayout);
        mViews.add(mExchangeLayout);
        mViews.add(mShareLayout);
    }

    @Override
    public void initData(Object data) {
    }

    @Override
    public void initListener() {
    }

    @OnClick({R.id.view_head, R.id.home_layout, R.id.exchange_layout, R.id.share_layout})
    public void onClick(View view) {
        FragmentTransaction tr = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.view_head:  //进入注册页面
                //ToastUtil.showToast("注册", context);
               // toActivity(RegisterActivity.class);
                toActivity(LogonActivity.class,false);
                break;
            case R.id.home_layout:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                if (!mHomeFragment.isAdded()) {
                    tr.add(R.id.container, mHomeFragment);
                }
                hideAllFragments(tr);
                tr.show(mHomeFragment);
                setFragmentSelectedById(0);
                break;
            case R.id.exchange_layout:
                if (mExchangeFragment == null) {
                    mExchangeFragment = new ExchangeFragment();
                }
                if (!mExchangeFragment.isAdded()) {
                    tr.add(R.id.container, mExchangeFragment);
                }
                hideAllFragments(tr);
                tr.show(mExchangeFragment);
                setFragmentSelectedById(1);
                break;
            case R.id.share_layout:
                if (mShareFragment == null) {
                    mShareFragment = new ShareFragment();
                }
                if (!mShareFragment.isAdded()) {
                    tr.add(R.id.container, mShareFragment);
                }
                hideAllFragments(tr);
                tr.show(mShareFragment);
                setFragmentSelectedById(2);
                break;
            default:
                break;
        }
        tr.commitAllowingStateLoss();
    }

    private void setFragmentSelectedById(int page) {
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            View view = mViews.get(i);
            if (page == i) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
        }
    }

    private void hideAllFragments(FragmentTransaction tr) {
        if (mHomeFragment != null) {
            tr.hide(mHomeFragment);
        }
        if (mExchangeFragment != null) {
            tr.hide(mExchangeFragment);
        }
        if (mShareFragment != null) {
            tr.hide(mShareFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast("再按一次返回键退出程序", context);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
