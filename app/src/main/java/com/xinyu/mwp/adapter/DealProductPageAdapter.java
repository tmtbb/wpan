package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.DealProductPageEntity;
import com.xinyu.mwp.entity.ShareOrderPageEntity;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;
import com.xinyu.mwp.util.LogUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 交易 productPageAdapter
 */
public class DealProductPageAdapter extends BaseListViewAdapter<DealProductPageEntity> {

    public DealProductPageAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<DealProductPageEntity> getViewHolder(int position) {
        return new DealProductPageViewHolder(context);
    }


    class DealProductPageViewHolder extends BaseViewHolder<DealProductPageEntity> {
        @ViewInject(R.id.tv_deal_name)
        private TextView productName;
        @ViewInject(R.id.tv_deal_open_price)
        private TextView openPrice;

        @ViewInject(R.id.tv_deal_gross_profit)
        private TextView grossProfit;


        public DealProductPageViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_deal_order_page;
        }

        @Override
        protected void update(DealProductPageEntity data) {
            if (data != null) {
                productName.setText(data.getProductName());
                openPrice.setText(data.getOpenPrice()+"");
                grossProfit.setText(data.getGrossProfit()+"");

            }
        }
        @Event(R.id.layout)
        private void onClick(View view){
            onItemChildViewClick(view, 99);
            LogUtil.d("DealProductPageAdapter条目被点击了");
        }
    }
}
