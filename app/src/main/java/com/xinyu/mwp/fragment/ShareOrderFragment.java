package com.xinyu.mwp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.xinyu.mwp.R;
import com.xinyu.mwp.fragment.base.BaseFragment;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 17/1/10.
 */

public class ShareOrderFragment extends BaseFragment {

    @ViewInject(R.id.tabLayout)
    private LinearLayout tabLayout;
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_shareorder;
    }

    @Override
    protected void initView() {
        super.initView();
        viewPager.setOffscreenPageLimit(tabLayout.getChildCount());
        fragmentList.add(new ShareOrderPageFragment());
        fragmentList.add(new ShareOrderPageFragment());
        fragmentList.add(new ShareOrderPageFragment());
        tabLayout.getChildAt(0).setSelected(true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList != null ? fragmentList.size() : 0;
            }

            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tabLayout.getChildCount(); i++){
                    tabLayout.getChildAt(i).setSelected(position == i);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event({R.id.itemLayout1, R.id.itemLayout2, R.id.itemLayout3})
    private void onClick(View view){
        viewPager.setCurrentItem(tabLayout.indexOfChild(view));
    }

    @Override
    public void initStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(getActivity(),
                (DrawerLayout) getActivity().findViewById(R.id.drawer),
                getResources().getColor(R.color.default_main_color), 0);
    }
}
