package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.bean.ProductBean;
import com.xinyu.mwp.ui.adapter.RankingInfoAdapter;
import com.xinyu.mwp.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 上周名人
 * Created by Administrator on 2017/1/6.
 */
public class WeekRankingFragment extends BaseFragment {
    @BindView(R.id.tv_share_ranking)
    TextView tvShareRanking;
    @BindView(R.id.lv_share_ranking_week)
    RecyclerView lvShareRankingWeek;

    List<ProductBean> mList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_share_week);

        // ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));

        initView();

        return parentView;
    }

    @Override
    public void initView() {
        /**
         *jiashuju
         */
        mList = new ArrayList<>();
        List<String> products = Arrays.asList(getResources().getStringArray(R.array.defaultname));
        ProductBean bean;
        int size = products.size();
        for (int i = 0; i < size; i++) {
            bean = new ProductBean();
            bean.setName(products.get(i));
            mList.add(bean);
        }
        lvShareRankingWeek.setLayoutManager(new LinearLayoutManager(context));
        lvShareRankingWeek.setHasFixedSize(true);
        tvShareRanking.setText("100");
        RankingInfoAdapter adapter = new RankingInfoAdapter(context, mList, R.layout.item_share_ranking);
        lvShareRankingWeek.setAdapter(adapter);
    }
}

