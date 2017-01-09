package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.ui.fragments.MonthRankingFragment;
import com.xinyu.mwp.ui.fragments.UserFriendPushBillFragment;
import com.xinyu.mwp.ui.fragments.UserFriendShareFragment;
import com.xinyu.mwp.ui.fragments.UserTotalRevenueFragment;
import com.xinyu.mwp.ui.fragments.WeekRankingFragment;
import com.xinyu.mwp.ui.fragments.YesterdayRankingFragment;
import com.xinyu.mwp.ui.viewpagers.NoScrollViewPager;
import com.xinyu.mwp.utils.LogUtil;
import com.xinyu.mwp.view.AlwaysMarqueeTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 用户主页
 * Created by Administrator on 2017/1/6.
 */
public class UserHomeActivity extends BaseActivity {


    @BindView(R.id.iv_user_home_back)
    ImageView ivUserHomeBack;
    @BindView(R.id.tv_special_notice)
    AlwaysMarqueeTextView tvSpecialNotice;
    @BindView(R.id.rb_total_revenue)
    RadioButton rbTotalRevenue;
    @BindView(R.id.rb_friend_share) //好友晒单
    RadioButton rbFriendShare;
    @BindView(R.id.rb_friend_push_bill) //好友推单
    RadioButton rbFriendPush;
    @BindView(R.id.vp_user_revenue)
    NoScrollViewPager vpUserRevenue;

    private List<Fragment> mViewList;  //三个收益fragment集合

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_home);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView() {
        mViewList = new ArrayList<>();

        LogUtil.d("晒单界面初始化");
        UserTotalRevenueFragment f1 = new UserTotalRevenueFragment(); //用户总收益
        UserFriendShareFragment f2 = new UserFriendShareFragment(); //好友晒单
        UserFriendPushBillFragment f3 = new UserFriendPushBillFragment(); //好友推单

        mViewList.add(f1);
        mViewList.add(f2);
        mViewList.add(f3);

        vpUserRevenue.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
        vpUserRevenue.setCurrentItem(0);  //设置默认显示的界面
    }

    @Override
    public void initData(Object data) {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_user_home_back, R.id.tv_special_notice, R.id.rb_total_revenue, R.id.rb_friend_share, R.id.rb_friend_push_bill})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_home_back: //返回键
                finish();
                break;
            case R.id.tv_special_notice:  //特别通知
                break;
            case R.id.rb_total_revenue: //总收益
                vpUserRevenue.setCurrentItem(0);
                break;
            case R.id.rb_friend_share: //好友晒单
                vpUserRevenue.setCurrentItem(1);
                break;
            case R.id.rb_friend_push_bill: //好友推单
                vpUserRevenue.setCurrentItem(2);
                break;

        }
    }

    private class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {
        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mViewList.get(position);
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }
    }
}

