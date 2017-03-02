package com.xinyu.mwp.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.CurrentPositionListReturnEntity;
import com.xinyu.mwp.entity.DealProductPageEntity;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TimeUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 交易 productPageAdapter
 */
public class DealProductPageAdapter extends BaseListViewAdapter<CurrentPositionListReturnEntity> {

    public DealProductPageAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<CurrentPositionListReturnEntity> getViewHolder(int position) {
        return new DealProductPageViewHolder(context);
    }

    class DealProductPageViewHolder extends BaseViewHolder<CurrentPositionListReturnEntity> {
        @ViewInject(R.id.tv_deal_name)
        private TextView productName;
        @ViewInject(R.id.tv_open_position_count)
        private TextView openPositionCount;
        @ViewInject(R.id.pb_trade_countdown)
        private ProgressBar tradeCountDown;  //进度条
        @ViewInject(R.id.tv_trade_product_countdown)
        private TextView tradeCountDownTime; //倒计时

        public DealProductPageViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_deal_order_page;
        }

        @Override
        protected void update(final CurrentPositionListReturnEntity data) {
            if (data != null) {
                productName.setText(data.getName());
                openPositionCount.setText(data.getAmount() + "");
//                tradeCountDown.setProgress(data.getCloseTime());
                tradeCountDownTime.setText(TimeUtil.getMinuteAndSecond(data.getCloseTime()));
                final Handler handler = new Handler();
//                processCountDown((long) data.getInterval());//加载倒计时
            }
        }

        private void processCountDown(final long interval) {
            new CountDownTimer(interval, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    LogUtil.i("done!");
                }
            }.start();
        }
    }

}
