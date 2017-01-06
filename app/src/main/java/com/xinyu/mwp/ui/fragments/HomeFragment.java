package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.bean.ProductBean;
import com.xinyu.mwp.ui.activities.TraceBillActivity;
import com.xinyu.mwp.ui.adapter.ProductAdapter;
import com.xinyu.mwp.utils.PixUtil;
import com.xinyu.mwp.utils.ToastUtil;
import com.xinyu.mwp.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.view_activity)
    LinearLayout mActivities;
    @BindView(R.id.ll_dot_activity)
    LinearLayout mLLDots;
    @BindView(R.id.rcv_product)
    RecyclerView mProduct;

    private int[] banners = new int[]{R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3,
            R.mipmap.banner4, R.mipmap.banner5, R.mipmap.banner6, R.mipmap.banner7};
    private AutoScrollViewPager mBannerViewPager;
    private AutoScrollViewPager mActivityViewPager;
    private List<ImageView> imgViews;
    private List<ImageView> imgDots;

    private int[] activities = new int[]{R.mipmap.activity1, R.mipmap.activity2, R.mipmap.activity3};
    private List<ImageView> imgActivities;
    private List<ImageView> imgLLDots;

    private List<ProductBean> mProducts;
    private ProductAdapter mProductAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        initView();
        initListener();
        return parentView;
    }

    @Override
    public void initView() {
        /**
         * 首页轮播图
         */
        imgViews = new ArrayList<>();
        int length = banners.length;
        for (int i = 0; i < length; i++) {
            ImageView view = new ImageView(context);
            view.setImageResource(banners[i]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgViews.add(view);
        }
        imgDots = new ArrayList<>();
        initDots(banners, mDots, imgDots);
        mBannerViewPager = new AutoScrollViewPager(context);
        mBannerViewPager.setImageView(imgViews);
        mBannerViewPager.setDots(imgDots);
        mBannerViewPager.start();
        mBanner.addView(mBannerViewPager);
        /**
         * 首页活动轮播图
         */
        imgActivities = new ArrayList<>();
        int len = activities.length;
        for (int i = 0; i < len; i++) {
            ImageView view = new ImageView(context);
            view.setImageResource(activities[i]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgActivities.add(view);
        }
        imgLLDots = new ArrayList<>();
        initDots(activities, mLLDots, imgLLDots);
        mActivityViewPager = new AutoScrollViewPager(context);
        mActivityViewPager.setImageView(imgActivities);
        mActivityViewPager.setDots(imgLLDots);
        mActivityViewPager.start();
        mActivities.addView(mActivityViewPager);

        /**
         * 制造假数据
         */
        mProducts = new ArrayList<>();
        List<String> products = Arrays.asList(getResources().getStringArray(R.array.products));
        int size = products.size();
        for (int i = 0; i < size; i++) {
            ProductBean bean = new ProductBean();
            bean.setName(products.get(i));
            bean.setPrice(3780);
            bean.setWavePerPrice(-0.47f);
            bean.setWavePrice(+18);
            bean.setMaxPrice(3900);
            bean.setMinPrice(3650);
            bean.setTodayPrice(3755);
            bean.setYesterdayPrice(3600);
            mProducts.add(bean);
        }
        mProduct.setLayoutManager(new LinearLayoutManager(context));
        mProduct.setHasFixedSize(true);
        mProductAdapter = new ProductAdapter(context, mProducts, R.layout.item_home_product);
        mProduct.setAdapter(mProductAdapter);
    }

    private void initDots(int[] imgs, LinearLayout layout, List<ImageView> dots) {
        dots.clear();
        layout.removeAllViews();
        int length = imgs.length;
        for (int i = 0; i < length; i++) {
            ImageView dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PixUtil.dip2px(context, 8), PixUtil.dip2px(context, 8));
            if (i == 0) {
                dot.setBackgroundResource(R.drawable.dot_focused);
            } else {
                params.leftMargin = PixUtil.dip2px(context, 8);
                dot.setBackgroundResource(R.drawable.dot_normal);
            }
            dot.setLayoutParams(params);
            dots.add(dot);
            layout.addView(dot);
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
        mBannerViewPager.setOnItemClickListener(new AutoScrollViewPager.onItemClickListener() {
            @Override
            public void onClick(int position) {
                // TODO: 2017/1/5 广告栏点击进入详情页待定
                ToastUtil.showToast("点击了第" + (position + 1) + "个条目", context);
            }
        });
        mActivityViewPager.setOnItemClickListener(new AutoScrollViewPager.onItemClickListener() {
            @Override
            public void onClick(int position) {
                // TODO: 2017/1/5 广告栏点击进入详情页待定
                ToastUtil.showToast("点击了第" + (position + 1) + "个条目", context);
            }
        });
        /**
         * 购买产品列表项点击监听
         */
        mProductAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // TODO: 2017/1/6 点击进入详情页待定
                ToastUtil.showToast(mProducts.get(position).getName(), context);
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
            }
        });
    }

    @OnClick({R.id.tv_notice, R.id.tv_trace_bill})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_notice:
                // TODO: 2017/1/6
                ToastUtil.showToast("通知界面待定。。。", context);
                break;
            case R.id.tv_trace_bill:
                toActivity(TraceBillActivity.class);
                break;
        }
    }
}
