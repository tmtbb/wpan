package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.bean.HoldBillProduct;
import com.xinyu.mwp.ui.adapter.DrawerSettingAdapter;
import com.xinyu.mwp.ui.adapter.HoldBillAdapter;
import com.xinyu.mwp.utils.SpaceItemDecoration;
import com.xinyu.mwp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Don on 2016/11/12 17:53.
 * Describe：${视频Fragment}
 * Modified：${TODO}
 */

public class ExchangeFragment extends BaseFragment {
    private static final String TAG = "ExchangeFragment";

    @BindView(R.id.tv_exchange_assets)
    TextView mTvExchangeAssets;
    @BindView(R.id.iv_exchange_assets_add)
    ImageView mIvExchangeAssetsAdd;
    @BindView(R.id.tv_exchange_ticket)
    TextView mTvExchangeTicket;
    @BindView(R.id.iv_exchange_ticket_add)
    ImageView mIvExchangeTicketAdd;
    @BindView(R.id.tv_exchange_price)
    TextView mTvExchangePrice;
    @BindView(R.id.tv_exchange_product)
    TextView mTvExchangeProduct;
    @BindView(R.id.tv_exchange_wave_per_price)
    TextView mTvExchangeWavePerPrice;
    @BindView(R.id.tv_exchange_wave_price)
    TextView mTvExchangeWavePrice;
    @BindView(R.id.tv_exchange_max_price)
    TextView mTvExchangeMaxPrice;
    @BindView(R.id.tv_exchange_today_price)
    TextView mTvExchangeTodayPrice;
    @BindView(R.id.tv_exchange_min_price)
    TextView mTvExchangeMinPrice;
    @BindView(R.id.tv_exchange_yesterday_price)
    TextView mTvExchangeYesterdayPrice;
    @BindView(R.id.rb_min_hour)
    RadioButton mRbMinHour;
    @BindView(R.id.rb_min_15)
    RadioButton mRbMin15;
    @BindView(R.id.rb_min_60)
    RadioButton mRbMin60;
    @BindView(R.id.rb_day_hour)
    RadioButton mRbDayHour;
    @BindView(R.id.tv_buy_plus_per)
    TextView mTvBuyPlusPer;
    @BindView(R.id.tv_buy_minus_per)
    TextView mTvBuyMinusPer;
    @BindView(R.id.pb_buy_plus)
    ProgressBar mPbBuyPlus;
    @BindView(R.id.tv_hold_bill)
    TextView mTvHoldBill;
    @BindView(R.id.tv_exchange_history)
    TextView mTvExchangeHistory;
    @BindView(R.id.rcv_hold_bill)
    RecyclerView mRcvHoldBill;

    private List<HoldBillProduct> mDatas;
    private HoldBillAdapter mHoldBillAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_exchange);
        initView();
        initListener();
        return parentView;
    }

    @Override
    public void initView() {
        mTvExchangeAssets.setText(String.format("%.2f", Float.valueOf(12.334f)));
        mTvExchangeTicket.setText(String.valueOf(12));
        mTvExchangeWavePerPrice.setText(String.format(getString(R.string.price_wave_per), String.format("%.2f", Float.valueOf(0.45f)), "%"));
        mTvBuyPlusPer.setText(String.format(getString(R.string.price_wave_per), String.format("%.2f", Float.valueOf(45.45f)), "%"));
        mTvBuyMinusPer.setText(String.format(getString(R.string.price_wave_per), String.format("%.2f", Float.valueOf(100f - 45.45f)), "%"));
        mPbBuyPlus.setProgress((int) Math.round(45.45f));

        mDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HoldBillProduct billProduct = new HoldBillProduct();
            billProduct.setName("粤贵银");
            billProduct.setWeight(999);
            billProduct.setCreateStoragePrice(9999f);
            billProduct.setLossOrProfit(+99);
            mDatas.add(billProduct);
        }
        mRcvHoldBill.setLayoutManager(new LinearLayoutManager(context));
        mRcvHoldBill.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        mRcvHoldBill.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mHoldBillAdapter = new HoldBillAdapter(context, mDatas, R.layout.item_hold_bill);
        mRcvHoldBill.setAdapter(mHoldBillAdapter);
    }

    @Override
    public void initData(Object data) {
    }

    @Override
    public void initListener() {
        mHoldBillAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // TODO: 2017/1/8
                ToastUtil.showToast(mDatas.get(position).getName(), context);
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
            }
        });
    }

    @OnClick({R.id.iv_exchange_assets_add, R.id.iv_exchange_ticket_add, R.id.rb_min_hour, R.id.rb_min_15, R.id.rb_min_60, R.id.rb_day_hour, R.id.tv_exchange_buy_plus, R.id.tv_exchange_buy_minus, R.id.tv_exchange_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_exchange_assets_add:
                // TODO: 2017/1/8
                ToastUtil.showToast("资产增加。。。", context);
                break;
            case R.id.iv_exchange_ticket_add:
                // TODO: 2017/1/8
                ToastUtil.showToast("体验券增加。。。", context);
                break;
            case R.id.rb_min_hour:
                // TODO: 2017/1/8
                ToastUtil.showToast("切换时分线图。。。", context);
                break;
            case R.id.rb_min_15:
                // TODO: 2017/1/8
                ToastUtil.showToast("切换15分钟线图。。。", context);
                break;
            case R.id.rb_min_60:
                // TODO: 2017/1/8
                ToastUtil.showToast("切换60分钟线图。。。", context);
                break;
            case R.id.rb_day_hour:
                // TODO: 2017/1/8
                ToastUtil.showToast("切换日时线图。。。", context);
                break;
            case R.id.tv_exchange_buy_plus:
                // TODO: 2017/1/8
                ToastUtil.showToast("切换买涨。。。", context);
                break;
            case R.id.tv_exchange_buy_minus:
                // TODO: 2017/1/8
                ToastUtil.showToast("切换买跌。。。", context);
                break;
            case R.id.tv_exchange_history:
                // TODO: 2017/1/8
                ToastUtil.showToast("切换交易历史fragment。。。", context);
                break;
            default:
                break;
        }
    }
}
