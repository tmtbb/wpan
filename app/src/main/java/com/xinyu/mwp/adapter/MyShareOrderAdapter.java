package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.MyShareOrderItemEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyShareOrderAdapter extends BaseListViewAdapter<MyShareOrderItemEntity> {
    public MyShareOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<MyShareOrderItemEntity> getViewHolder(int position) {
        return new MyShareOrderViewHolder(context);
    }

    class MyShareOrderViewHolder extends BaseViewHolder<MyShareOrderItemEntity> {
        @ViewInject(R.id.name)
        private TextView name;
        @ViewInject(R.id.status)
        private TextView status;
        @ViewInject(R.id.statusLayout)
        private View statusLayout;
        @ViewInject(R.id.createPrice)
        private TextView createPrice;
        @ViewInject(R.id.createTime)
        private TextView createTime;
        @ViewInject(R.id.flatPrice)
        private TextView flatPrice;
        @ViewInject(R.id.flatTime)
        private TextView flatTime;
        @ViewInject(R.id.profit)
        private TextView profit;


        public MyShareOrderViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_myshareorder;
        }

        @Override
        protected void update(MyShareOrderItemEntity data) {
            name.setText(data.getName());
            switch (data.getStatus()) {
                case "0":
                    statusLayout.setBackgroundColor(context.getResources().getColor(R.color.default_green));
                    status.setText("空");
                    break;
                case "1":
                    statusLayout.setBackgroundColor(context.getResources().getColor(R.color.default_red));
                    status.setText("多");
                    break;
            }
            createPrice.setText("价位：" + data.getCreateDepotPrice());
            createTime.setText("建仓" + data.getCreateDepotTime());
            flatPrice.setText("价位：" + data.getFlatDepotPrice());
            flatTime.setText("平仓" + data.getFlatDepotTime());

            float pf = Float.parseFloat(data.getProfit());
            if (pf < 0) {
                profit.setTextColor(context.getResources().getColor(R.color.default_green));
                profit.setText("-" + Math.abs(pf));
            } else {
                profit.setTextColor(context.getResources().getColor(R.color.default_red));
                profit.setText("+" + pf);
            }

        }
    }
}
