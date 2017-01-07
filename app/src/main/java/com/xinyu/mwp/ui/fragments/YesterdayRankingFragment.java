package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * 昨日之星
 * Created by Administrator on 2017/1/6.
 */
public class YesterdayRankingFragment extends BaseFragment {

    @BindView(R.id.rv_ranking_yesterday)
    RecyclerView lvShareRankingYesterday;
    List<ProductBean> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_share_yesterday);
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
        lvShareRankingYesterday.setLayoutManager(new LinearLayoutManager(context));
        lvShareRankingYesterday.setHasFixedSize(true);

        RankingInfoAdapter adapter = new RankingInfoAdapter(context, mList, R.layout.item_share_ranking);
        lvShareRankingYesterday.setAdapter(adapter);
    }
}
