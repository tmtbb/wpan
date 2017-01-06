package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinyu.mwp.R;
import com.xinyu.mwp.utils.LogUtil;


/**
 * 月度名人
 * Created by Administrator on 2017/1/6.
 */
public class MonthRankingFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();

        return inflater.inflate(R.layout.fragment_share_month, container, false);
    }

    private void initData() {
        LogUtil.d("月度名人初始化");


    }
}
