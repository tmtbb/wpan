package com.xinyu.mwp.adapter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.MyPushOrderItemEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyPushOrderAdapter extends BaseListViewAdapter<MyPushOrderItemEntity> {
    public MyPushOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<MyPushOrderItemEntity> getViewHolder(int position) {
        return new MyPushOrderViewHolder(context);
    }

    class MyPushOrderViewHolder extends BaseViewHolder<MyPushOrderItemEntity> {
        @ViewInject(R.id.sucOdds)
        private TextView sucOdds;
        @ViewInject(R.id.sucCount)
        private TextView sucCount;
        @ViewInject(R.id.sucProfit)
        private TextView sucProfit;
        @ViewInject(R.id.progressBar)
        private ProgressBar progressBar;

        public MyPushOrderViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_mypushorder;
        }

        @Override
        protected void update(MyPushOrderItemEntity data) {
            sucOdds.setText("本月胜率：" + data.getSucOdds() + "%");
            sucCount.setText("胜场：" + data.getSucCount());

            float sp = Float.parseFloat(data.getProfit());
            if (sp < 0) {
                sucProfit.setTextColor(context.getResources().getColor(R.color.default_green));
                progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.bg_progress_default_green));
            } else {
                sucProfit.setTextColor(context.getResources().getColor(R.color.default_red));
                progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.bg_progress_default_red));
            }
            //防止进度条太短，很难看。
            progressBar.setProgress(Math.abs((int) sp) < 10 ? 10 : Math.abs((int) sp));
            sucProfit.setText(data.getProfit() + "%");
        }
    }
}
