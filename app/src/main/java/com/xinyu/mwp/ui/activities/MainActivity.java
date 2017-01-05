package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.bean.DrawerSettingBean;
import com.xinyu.mwp.ui.adapter.DrawerSettingAdapter;
import com.xinyu.mwp.ui.fragments.ExchangeFragment;
import com.xinyu.mwp.ui.fragments.HomeFragment;
import com.xinyu.mwp.ui.fragments.ShareFragment;
import com.xinyu.mwp.utils.SpaceItemDecoration;
import com.xinyu.mwp.utils.ToastUtil;
import com.xinyu.mwp.view.CircleImageView;

import java.util.ArrayList;
import java.util.Arrays;
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
    @BindView(R.id.tv_assets)
    TextView mAssets;
    @BindView(R.id.tv_grade)
    TextView mGrade;
    @BindView(R.id.rcv_drawer_list)
    RecyclerView mDrawerList;

    public static HomeFragment mHomeFragment;
    public static ExchangeFragment mExchangeFragment;
    public static ShareFragment mShareFragment;
    private List<View> mViews;
    private List<String> mSettings;
    private List<DrawerSettingBean> mDatas;

    private int[] settingImgs = new int[]{R.mipmap.ic_drawer_attention, R.mipmap.ic_drawer_push_bill, R.mipmap.ic_drawer_share_bill,
            R.mipmap.ic_drawer_exchange_detail, R.mipmap.ic_drawer_comments, R.mipmap.ic_drawer_products_grade, R.mipmap.ic_drawer_focus};
    private DrawerSettingAdapter mSettingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActinbar(mToolBar, mDrawer);
        initView();
        initListener();
    }

    @Override
    public void initActinbar(Toolbar toolbar, DrawerLayout drawer) {
        super.initActinbar(toolbar, drawer);
        mToolbarTitle.setText("喵仔微盘");
        mToolBar.setNavigationIcon(R.mipmap.ic_toolbar_menu);
    }

    @Override
    public void initView() {
        /**
         * 首页tab导航页
         */
        mViews = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mHomeFragment).commitAllowingStateLoss();
        mHomeLayout.setSelected(true);
        mViews.add(mHomeLayout);
        mViews.add(mExchangeLayout);
        mViews.add(mShareLayout);

        /**
         * 侧滑菜单栏
         */
        mAssets.setText(String.format(getString(R.string.my_assets_num), "￥16202.00"));
        mGrade.setText(String.format(getString(R.string.my_grade_num), "123"));
        mDatas = new ArrayList<>();
        mSettings = Arrays.asList(getResources().getStringArray(R.array.drawer_setting));
        int size = mSettings.size();
        for (int i = 0; i < size; i++) {
            DrawerSettingBean settingBean = new DrawerSettingBean();
            settingBean.setIcon(settingImgs[i]);
            settingBean.setText(mSettings.get(i));
            mDatas.add(settingBean);
        }
        mDrawerList.setLayoutManager(new LinearLayoutManager(context));
        mDrawerList.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        mDrawerList.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mSettingAdapter = new DrawerSettingAdapter(context, mDatas, R.layout.item_drawer_setting);
        mDrawerList.setAdapter(mSettingAdapter);
    }

    @Override
    public void initData(Object data) {
    }

    @Override
    public void initListener() {
        mSettingAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // TODO: 2017/1/5 跳转到详情界面待定。。。
                ToastUtil.showToast(mSettings.get(position), context);
            }

            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });
    }

    @OnClick({R.id.view_head, R.id.home_layout, R.id.exchange_layout, R.id.share_layout})
    public void onClick(View view) {
        FragmentTransaction tr = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.view_head:  //进入注册页面
                //ToastUtil.showToast("注册", context);
                // toActivity(RegisterActivity.class);
                toActivity(LogonActivity.class, false);
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
