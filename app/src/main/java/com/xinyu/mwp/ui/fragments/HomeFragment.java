package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.utils.PixUtil;
import com.xinyu.mwp.utils.ToastUtil;
import com.xinyu.mwp.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Don on 2016/11/12 17:53.
 * Describe：${视频Fragment}
 * Modified：${TODO}
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.view_banner)
    LinearLayout mBanner;
    @BindView(R.id.ll_dots)
    LinearLayout mDots;

    private int[] banners = new int[]{R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3,
            R.mipmap.banner4, R.mipmap.banner5, R.mipmap.banner6, R.mipmap.banner7};
    private AutoScrollViewPager mViewPager;
    private List<ImageView> imgViews;
    private List<ImageView> imgDots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        initView();
        initData(null);
        initListener();
        return parentView;
    }

    @Override
    public void initView() {
        /**
         * 首页轮播图
         */
        imgViews = new ArrayList<>();
        imgDots = new ArrayList<>();
        int length = banners.length;
        for (int i = 0; i < length; i++) {
            ImageView view = new ImageView(context);
            view.setImageResource(banners[i]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgViews.add(view);
        }
        imgDots = new ArrayList<>();
        initDots(length);
        mViewPager = new AutoScrollViewPager(context);
        mViewPager.setImageView(imgViews);
        mViewPager.setDots(imgDots);
        mViewPager.start();
        mBanner.addView(mViewPager);
    }

    private void initDots(int size) {
        imgDots.clear();
        mDots.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PixUtil.dip2px(context, 8), PixUtil.dip2px(context, 8));
            if (i == 0) {
                dot.setBackgroundResource(R.drawable.dot_focused);
            } else {
                params.leftMargin = PixUtil.dip2px(context, 8);
                dot.setBackgroundResource(R.drawable.dot_normal);
            }
            dot.setLayoutParams(params);
            imgDots.add(dot);
            mDots.addView(dot);
        }
    }

    @Override
    public void initData(Object data) {

    }

    @Override
    public void initListener() {
        /**
         * 轮播图点击监听
         */
        mViewPager.setOnItemClickListener(new AutoScrollViewPager.onItemClickListener() {
            @Override
            public void onClick(int position) {
                ToastUtil.showToast("点击了第" + (position + 1) + "个条目", context);
            }
        });
    }
}
