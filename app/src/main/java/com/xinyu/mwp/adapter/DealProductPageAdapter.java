package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.CurrentPositionListReturnEntity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.iwgang.countdownview.CountdownView;

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
        @ViewInject(R.id.cv_countdownView)
        private CountdownView tradeCountDownTime; //倒计时
        @ViewInject(R.id.layout)
        private LinearLayout mLayout;
        private double timer;

        public DealProductPageViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_deal_order_page;
        }

        @Override
        protected void update(final CurrentPositionListReturnEntity data) {
            //刷新
            if (data != null) {
                productName.setText(data.getName());
                openPositionCount.setText(data.getAmount() + "");

                mLayout.setVisibility(View.VISIBLE);
                final long newInterval = data.getEndTime() - System.currentTimeMillis();
                tradeCountDownTime.start(newInterval);
                tradeCountDownTime.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        mLayout.setVisibility(View.GONE);
                        if (litener != null) {
                            litener.refreshData();
                        }
                    }
                });

                tradeCountDownTime.setOnCountdownIntervalListener(200, new CountdownView.OnCountdownIntervalListener() {
                    @Override
                    public void onInterval(CountdownView cv, long remainTime) {
                        int process = (int) ((newInterval - remainTime) * 100 / newInterval);
                        tradeCountDown.setProgress(process);
                    }
                });
            }
        }
    }

    public TimeFinishLitener litener;

    public void setTimeFinishLitener(TimeFinishLitener litener) {
        this.litener = litener;
    }

    public interface TimeFinishLitener {
        void refreshData();
    }
}
