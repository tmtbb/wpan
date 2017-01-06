package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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
import com.xinyu.mwp.ui.fragments.ToolNavigationDrawerFragment;
import com.xinyu.mwp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ToolNavigationDrawerFragment.menuItemClickListener {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.home_layout)
    LinearLayout mHomeLayout;
    @BindView(R.id.exchange_layout)
    LinearLayout mExchangeLayout;
    @BindView(R.id.share_layout)
    LinearLayout mShareLayout;
    @BindView(R.id.left_drawer)
    DrawerLayout mDrawer;

    public HomeFragment mHomeFragment;
    public ExchangeFragment mExchangeFragment;
    public ShareFragment mShareFragment;
    private List<View> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar(mToolbar, mToolbarTitle, getString(R.string.home_toolbar));
        mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_menu);
        initView();
        initFragments(savedInstanceState);
    }

    /**
     * 为页面加载初始状态的fragment
     */
    private void initFragments(Bundle savedInstanceState) {
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if (savedInstanceState == null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (currentFragment == null) {
                currentFragment = new HomeFragment();
            }
            mHomeFragment = (HomeFragment) currentFragment;
            ft.replace(R.id.container, currentFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void initToolbar(Toolbar toolbar, TextView titleView, String title) {
        super.initToolbar(toolbar, titleView, title);
        if (toolbar == null) {
            return;
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawer.isDrawerVisible(Gravity.START)) {
                    mDrawer.closeDrawer(Gravity.START);
                } else {
                    mDrawer.openDrawer(Gravity.START);
                }
            }
        });
    }

    @Override
    public void initView() {
        /**
         * 首页tab导航页
         */
        mViews = new ArrayList<>();
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

    @OnClick({R.id.home_layout, R.id.exchange_layout, R.id.share_layout})
    public void onClick(View view) {
        FragmentTransaction tr = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.home_layout:
                initToolbar(mToolbar, mToolbarTitle, getString(R.string.home_toolbar));
                mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_menu);
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
                initToolbar(mToolbar, mToolbarTitle, getString(R.string.tab_exchange));
                mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_back);
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
                initToolbar(mToolbar, mToolbarTitle, getString(R.string.tab_share));
                mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_back);
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

    @Override
    public void menuItemClick(int position, String menuName) {
        initToolbar(mToolbar, mToolbarTitle, menuName);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                break;
            default:
                break;
        }
        invalidateOptionsMenu();
        //关闭左侧滑出菜单
        mDrawer.closeDrawers();
    }
}
