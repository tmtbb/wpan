package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.ui.viewpagers.NoScrollViewPager;
import com.xinyu.mwp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Don on 2016/11/12 17:53.
 * Describe：晒单界面
 * Modified：${TODO}
 */

public class ShareFragment extends BaseFragment {
    private static final String TAG = "ShareFragment";
    @BindView(R.id.rb_share1)
    RadioButton rbShare1;
    @BindView(R.id.rb_share2)
    RadioButton rbShare2;
    @BindView(R.id.rb_share3)
    RadioButton rbShare3;
    @BindView(R.id.vp_share)
    NoScrollViewPager vpShare;
    private View view1;
    private View view2;
    private View view3;

    private List<Fragment> viewList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setContentView(R.layout.fragment_share);
        // tv.setText(this.getClass().getSimpleName());
        ButterKnife.bind(this, parentView);

        viewList = new ArrayList<>();

        initView();
        return parentView;
    }

    @Override
    public void initView() {
        super.initView();
        LogUtil.d("晒单界面初始化");
        YesterdayRankingFragment f1 = new YesterdayRankingFragment();
        WeekRankingFragment f2 = new WeekRankingFragment();
        MonthRankingFragment f3 = new MonthRankingFragment();

        viewList.add(f1);
        viewList.add(f2);
        viewList.add(f3);

        vpShare.setAdapter(new MyFrageStatePagerAdapter(getFragmentManager()));
        vpShare.setCurrentItem(1);  //设置默认显示的界面
    }

    @Override
    public void initData(Object data) {
        super.initData(data);
    }

    @OnClick({R.id.rb_share1, R.id.rb_share2, R.id.rb_share3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_share1:  //昨日
                vpShare.setCurrentItem(0);
                break;
            case R.id.rb_share2:    //今日
                vpShare.setCurrentItem(1);
                break;
            case R.id.rb_share3:    //月度
                vpShare.setCurrentItem(2);
                break;
        }
    }


    private class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {
        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return viewList.get(position);
        }

        @Override
        public int getCount() {
            return viewList.size();
        }
    }
}


