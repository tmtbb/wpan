package com.xinyu.mwp.fragment;


import android.os.Handler;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;


import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.RechargeActivity;
import com.xinyu.mwp.activity.UserAssetsActivity;
import com.xinyu.mwp.adapter.GalleryAdapter;

import com.xinyu.mwp.fragment.base.BaseFragment;

import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 交易界面
 * Created by Benjamin on 17/1/10.
 */

public class DealFragment extends BaseFragment {

    @ViewInject(R.id.leftImage)
    private ImageButton leftImage; //左侧返回按钮

    @ViewInject(R.id.titleText)
    private TextView titleText; //正标题
    @ViewInject(R.id.tv_exchange_assets)
    private TextView tvExchangeAssets; //总资产数目
    @ViewInject(R.id.iv_exchange_assets_add)
    private ImageView ivAssetsAdd; //加号按钮

    @ViewInject(R.id.container_chart)
    FrameLayout containerChart;

    @ViewInject(R.id.rv_title)
    RecyclerView mRecyclerView;


    private List<String> mTitleList = new ArrayList<>();

    private List<Fragment> fragmentList = new ArrayList<>();
    private GalleryAdapter mAdapter;
    private FragmentTransaction fragmentTransaction;
    private DealProductPageFragment dealProductPageFragment;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_deal;
    }


    @Override
    protected void initView() {
        super.initView();

        mTitleList.add("白银");
        mTitleList.add("原油");
        mTitleList.add("咖啡");
        mTitleList.add("黄金");
        mTitleList.add("大豆");
        mTitleList.add("棉花");
        mTitleList.add("钢铁");

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(context, mTitleList);

        mRecyclerView.setAdapter(mAdapter);


        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(new DealProductPageFragment(), "sss");
        fragmentTransaction.commit();
        dealProductPageFragment = new DealProductPageFragment();
        fragmentTransaction.replace(R.id.container_chart, dealProductPageFragment);
        leftImage.setVisibility(View.INVISIBLE);
        titleText.setText("交易");


    }


    @Override
    protected void initListener() {
        super.initListener();

        mAdapter.setOnItemClick(new GalleryAdapter.OnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                ToastUtils.show(context, mTitleList.get(position) + "刷新数据");
                //调用方法,传递数据到dealProductPageFragment界面,更新数据
                dealProductPageFragment.setData(mTitleList.get(position));

                dealProductPageFragment.loadChartData(); //刷新数据
                dealProductPageFragment.createAdapter().notifyDataSetChanged(); //刷新持仓

                LogUtil.d("当前点击的条目位置是:" + mTitleList.get(position));
                mAdapter.setPosition(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean OnLongClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                // ToastUtils.show(context,(position + 1) + "onLongClick");
                return false;
            }
        });
    }

    @Event(value = {R.id.iv_exchange_assets_add, R.id.tv_exchange_assets})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.iv_exchange_assets_add:
                showToast("充值");
                next(RechargeActivity.class);
                break;
            case R.id.tv_exchange_assets:
                showToast("个人资产");
                next(UserAssetsActivity.class);
                break;

            default:
                break;
        }
    }
}
