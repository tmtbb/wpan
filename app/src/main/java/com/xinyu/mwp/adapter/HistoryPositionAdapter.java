package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.user.UserManager;
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

    //局部刷新
    public void notifyPartsData(View view, int position, int handle) {
        if (view != null) {
            HistoryPositionListReturnEntity historyPositionListReturnEntity = getList().get(position);
            historyPositionListReturnEntity.setHandle(handle);
            getList().set(position,historyPositionListReturnEntity);
            TextView actionType = (TextView) view.findViewById(R.id.tv_action_type);
            TextView actionNone = (TextView) view.findViewById(R.id.tv_action_none);

            actionType.setVisibility(View.VISIBLE);
            actionNone.setVisibility(View.INVISIBLE);
            actionType.setText(Constant.handleText[handle]);
        }
    }

    class HistoryPositionViewHolder extends BaseViewHolder<HistoryPositionListReturnEntity> {

        @ViewInject(R.id.tv_product_history_name)
        private TextView name;
        @ViewInject(R.id.tv_history_time)
        private TextView time;
        @ViewInject(R.id.tv_trade_history_turnover)
        private TextView price;
        @ViewInject(R.id.tv_history_profit)
        private TextView profit;
        @ViewInject(R.id.tv_history_loss)
        private TextView loss;
        @ViewInject(R.id.tv_action_none)
        private TextView actionNone;
        @ViewInject(R.id.tv_action_type)
        private TextView actionType;
        @ViewInject(R.id.tv_desc_turnover)
        private TextView tradeDirection;  //交易方向

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
                price.setText("¥ " + NumberUtils.halfAdjust2(data.getOpenCost()));
                tradeDirection.setText(data.getBuySell() == 1 ? "买入" : "卖出");
                if (data.isResult()) {  //盈
                    price.setTextColor(context.getResources().getColor(R.color.default_red));
                    profit.setVisibility(View.VISIBLE);
                    loss.setVisibility(View.INVISIBLE);

                } else {
                    price.setTextColor(context.getResources().getColor(R.color.default_green));
                    profit.setVisibility(View.INVISIBLE);
                    loss.setVisibility(View.VISIBLE);
                }
                switch (data.getHandle()) {
                    case Constant.ACTION_NONE:
                        actionNone.setVisibility(View.VISIBLE);
                        actionType.setVisibility(View.INVISIBLE);
                        actionNone.setText(Constant.handleText[data.getHandle()]);
                        break;
                    case Constant.ACTION_DOUBLE:
                    case Constant.ACTION_FREIGHT:
                    case Constant.ACTION_RETURN:
                        actionType.setVisibility(View.VISIBLE);
                        actionNone.setVisibility(View.INVISIBLE);
                        actionType.setText(Constant.handleText[data.getHandle()]);
                        break;
                }
                //普通会员 买跌--上涨, 亏损   卖亏了 未操作---->  不显示
                if (UserManager.getInstance().getUserEntity().getUserType() == 0 && data.getBuySell() != 1 && !data.isResult()){
                    if (data.getHandle() == Constant.ACTION_NONE){
                        actionNone.setVisibility(View.INVISIBLE);
                        actionType.setVisibility(View.INVISIBLE);
                    }
                }
                long datas = data.getCloseTime() * 1000;
                time.setText(TimeUtil.getDateAndTime(datas));
            }
        }

        @Event(value = R.id.rl_history_item)
        private void click(View v) {
            onItemChildViewClick(v, 99);
        }
    }
}
