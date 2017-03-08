package com.xinyu.mwp.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.CurrentPositionListReturnEntity;
import com.xinyu.mwp.entity.DealProductPageEntity;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TimeUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.sql.Time;

/**
 * 交易 productPageAdapter
 */
public class DealProductPageAdapter extends BaseListViewAdapter<CurrentPositionListReturnEntity> {

//    private CurrentPositionListReturnEntity dataEntity;

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
        @ViewInject(R.id.tv_trade_countdown)
        private TextView tradeCountdownDesc; //倒计时
        private double timer;

        public DealProductPageViewHolder(Context context) {
            super(context);
            handler.sendEmptyMessage(1);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_deal_order_page;
        }

        CurrentPositionListReturnEntity dataEntity;

        @Override
        protected void update(final CurrentPositionListReturnEntity data) {
            dataEntity = data;
            //刷新
            if (data != null) {
                productName.setText(data.getName());
                openPositionCount.setText(data.getAmount() + "");

                tradeCountDownTime.setText(TimeUtil.getMinuteAndSecond((long) (dataEntity.getInterval() * 1000)));
                double interval = dataEntity.getInterval();
                timer = interval;
            }
        }

        public Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        boolean isNeedCountTime = false;

                        if (timer > 0) {
                            isNeedCountTime = true;
                            timer = timer - 1;
                            tradeCountDownTime.setText(TimeUtil.getMinuteAndSecond((long) (timer * 1000)));
                            int process = (int) ((dataEntity.getInterval() - timer) * 100 / dataEntity.getInterval());
                            tradeCountDown.setProgress(process);
                        } else {
                            tradeCountDownTime.setText("00:00");
                            tradeCountDownTime.setTextColor(context.getResources().getColor(R.color.color_cccccc));
                            tradeCountdownDesc.setTextColor(context.getResources().getColor(R.color.color_cccccc));
                            if (litener != null) {
                                litener.refreshData();  //结束的接口回调
                            }
                        }
                        if (isNeedCountTime) {
                            handler.sendEmptyMessageDelayed(1, 1000);
                        }
                        break;
                }
            }
        };
    }

    public TimeFinishLitener litener;

    public void setTimeFinishLitener(TimeFinishLitener litener) {
        this.litener = litener;
    }

    public interface TimeFinishLitener {
        void refreshData();
    }
}
