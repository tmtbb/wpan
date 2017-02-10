package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.CashRecordEntity;
import com.xinyu.mwp.entity.HistoryPositionEntity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 持仓历史记录adapter
 */

public class HistoryPositionAdapter extends BaseListViewAdapter<HistoryPositionEntity> {
    public HistoryPositionAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<HistoryPositionEntity> getViewHolder(int position) {
        return new HistoryPositionViewHolder(context);
    }

    class HistoryPositionViewHolder extends BaseViewHolder<HistoryPositionEntity> {

        @ViewInject(R.id.tv_product_history_name)
        private TextView name;
        @ViewInject(R.id.tv_history_time)
        private TextView time;
        @ViewInject(R.id.tv_trade_history_turnover)
        private TextView price;

        public HistoryPositionViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_position_history;
        }

        @Override
        protected void update(HistoryPositionEntity data) {
            if (data != null) {
                name.setText(data.getName());
                price.setText(data.getPrice()); //如果是买跌的,价格颜色设置为绿色
                time.setText(data.getTime());
            }
        }
    }
}
