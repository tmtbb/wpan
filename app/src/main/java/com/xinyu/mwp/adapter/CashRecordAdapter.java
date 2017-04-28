package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.WithDrawCashReturnEntity;
import com.xinyu.mwp.util.BankInfoUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/13.
 */

public class CashRecordAdapter extends BaseListViewAdapter<WithDrawCashReturnEntity> {
    public CashRecordAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<WithDrawCashReturnEntity> getViewHolder(int position) {
        return new CashRecordViewHolder(context);
    }

    class CashRecordViewHolder extends BaseViewHolder<WithDrawCashReturnEntity> {

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
        protected void update(WithDrawCashReturnEntity data) {
            date.setText(data.getWithdrawTime().substring(0, 10));
            time.setText(data.getWithdrawTime().substring(11, 19));
            money.setText(data.getAmount() + "");
            icon.setImageResource(BankInfoUtil.getIcon(data.getBank()));
            bankInfo.setText(String.format("提现到%s",data.getBank()));
            switch (data.getStatus()) {
                case 1:
                    status.setText("处理中");
                    break;
                case 2:
                    status.setText("到账成功");
                    break;
                case 3:
                    status.setText("提现失败");
                    break;
                case 4:
                    status.setText("已退款");
                    break;
            }
        }
    }
}
