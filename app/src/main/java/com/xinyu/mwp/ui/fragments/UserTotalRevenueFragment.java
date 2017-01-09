package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.bean.ProductBean;
import com.xinyu.mwp.ui.adapter.RankingInfoAdapter;
import com.xinyu.mwp.utils.LogUtil;
import com.xinyu.mwp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 用户总收益
 * Created by Administrator on 2017/1/6.
 */
public class UserTotalRevenueFragment extends BaseFragment {
    @BindView(R.id.tv_user_total_revenue)
    TextView tvUserTotalRevenue;
    @BindView(R.id.tv_user_month_revenue)
    TextView tvUserMonthRevenue;
    @BindView(R.id.tv_user_week_revenue)
    TextView tvUserWeekRevenue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_user_total_revenue);
        initView();
        initListener();
        ButterKnife.bind(this, parentView);
        return parentView;
    }
}

