package com.xinyu.mwp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseViewHolder;
import com.xinyu.mwp.bean.HoldBillProduct;
import com.xinyu.mwp.utils.ToastUtil;

import java.util.List;

/**
 * Created by Tang on 2017/1/8.
 */
public class HoldBillAdapter extends BaseAdapter<HoldBillProduct> {

    public HoldBillAdapter(Context context, List<HoldBillProduct> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convert(final Context context, final BaseViewHolder holder, final HoldBillProduct holdBillProduct) {
        holder.setText(R.id.tv_hold_product_name, holdBillProduct.getName())
                .setText(R.id.tv_hold_product_weight, holdBillProduct.getWeight() + "G")
                .setText(R.id.tv_hold_product_price, String.format("%.0f", holdBillProduct.getCreateStoragePrice()))
                .setText(R.id.tv_hold_product_balance, String.format("%.0f", holdBillProduct.getLossOrProfit()));

        holder.setOnClickListener(R.id.tv_hold_product_clear, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("平仓" + holder.getLayoutPosition(), context);
            }
        });
    }
}
