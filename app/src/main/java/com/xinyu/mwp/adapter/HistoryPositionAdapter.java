package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.TimeUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 持仓历史记录adapter
 */

public class HistoryPositionAdapter extends BaseListViewAdapter<HistoryPositionListReturnEntity> {
    public HistoryPositionAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<HistoryPositionListReturnEntity> getViewHolder(int position) {
        return new HistoryPositionViewHolder(context);
    }

    class HistoryPositionViewHolder extends BaseViewHolder<HistoryPositionListReturnEntity> {

        @ViewInject(R.id.tv_product_history_name)
        private TextView name;
        @ViewInject(R.id.tv_history_time)
        private TextView time;
        @ViewInject(R.id.tv_trade_history_turnover)
        private TextView price;
        @ViewInject(R.id.iv_history_profit)
        private ImageView profit;
        @ViewInject(R.id.iv_history_loss)
        private ImageView loss;

        public HistoryPositionViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_position_history;
        }

        @Override
        protected void update(HistoryPositionListReturnEntity data) {
            if (data != null) {
                name.setText(data.getName());
                price.setText("¥ " + NumberUtils.halfAdjust2(data.getOpenPrice()));
                if (data.isResult()) {
                    price.setTextColor(context.getResources().getColor(R.color.default_red));
                    profit.setVisibility(View.VISIBLE);
                    loss.setVisibility(View.GONE);

                } else {
                    price.setTextColor(context.getResources().getColor(R.color.default_green));
                    profit.setVisibility(View.GONE);
                    loss.setVisibility(View.VISIBLE);
                }
                long datas = data.getCloseTime() * 1000;
                time.setText(TimeUtil.getDateAndTime(datas));
            }
        }
    }
}
