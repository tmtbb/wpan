package com.xinyu.mwp.ui.adapter;

import android.content.Context;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseViewHolder;
import com.xinyu.mwp.bean.ProductBean;

import java.util.List;

/**
 * Created by Don on 2017/1/6.
 * Describe ${TODO}
 * Modified ${TODO}
 */
public class ProductAdapter extends BaseAdapter<ProductBean> {
    public ProductAdapter(Context context, List<ProductBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convert(Context context, BaseViewHolder holder, ProductBean productBean) {
        holder.setText(R.id.tv_name, productBean.getName())
                .setText(R.id.tv_price, String.valueOf(productBean.getPrice()))
                .setText(R.id.tv_wave_per_price, String.format(context.getString(R.string.price_wave_per), String.format("%.2f", productBean.getWavePerPrice()), "%"))
                .setText(R.id.tv_wave_price, String.format("%.0f", productBean.getWavePrice()))
                .setText(R.id.tv_max_price, String.format("%.0f", productBean.getMaxPrice()))
                .setText(R.id.tv_min_price, String.format("%.0f", productBean.getMinPrice()))
                .setText(R.id.tv_today_price, String.format("%.0f", productBean.getTodayPrice()))
                .setText(R.id.tv_yesterday_price, String.format("%.0f", productBean.getYesterdayPrice()));
    }
}
