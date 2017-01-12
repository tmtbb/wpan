package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.CashRecordEntity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/13.
 */

public class CashRecordAdapter extends BaseListViewAdapter<CashRecordEntity> {
    public CashRecordAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<CashRecordEntity> getViewHolder(int position) {
        return new CashRecordViewHolder(context);
    }

    class CashRecordViewHolder extends BaseViewHolder<CashRecordEntity> {

        @ViewInject(R.id.date)
        private TextView date;
        @ViewInject(R.id.time)
        private TextView time;
        @ViewInject(R.id.icon)
        private ImageView icon;
        @ViewInject(R.id.money)
        private TextView money;
        @ViewInject(R.id.bankInfo)
        private TextView bankInfo;
        @ViewInject(R.id.status)
        private TextView status;

        public CashRecordViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_cashrecord;
        }

        @Event(value = R.id.rootLayout)
        private void click(View v) {
            onItemChildViewClick(v, 99);
        }

        @Override
        protected void update(CashRecordEntity data) {
            date.setText(data.getDate());
            time.setText(data.getTime());
            money.setText(data.getMoney());
            switch (data.getType()) {
                case "1":
                    icon.setBackgroundColor(context.getResources().getColor(R.color.default_red));
                    bankInfo.setText("提现到中国工商银行");
                    break;
            }

            switch (data.getStatus()) {
                case "1":
                    break;
            }
        }
    }
}
