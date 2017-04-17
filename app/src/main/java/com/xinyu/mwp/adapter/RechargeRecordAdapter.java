package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.RechargeRecordItemEntity;
import com.xinyu.mwp.util.LogUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/16.
 */

public class RechargeRecordAdapter extends BaseListViewAdapter<RechargeRecordItemEntity> {

    public RechargeRecordAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<RechargeRecordItemEntity> getViewHolder(int position) {
        return new RechargeRecordViewHolder(context);
    }

    class RechargeRecordViewHolder extends BaseViewHolder<RechargeRecordItemEntity> {

        @ViewInject(R.id.date_1)
        private TextView date_1;
        @ViewInject(R.id.date_2)
        private TextView date_2;
        @ViewInject(R.id.time)
        private TextView time;
        @ViewInject(R.id.status)
        private TextView status;
        @ViewInject(R.id.icon)
        private ImageView icon;
        @ViewInject(R.id.money)
        private TextView money;
        @ViewInject(R.id.info)
        private TextView info;


        public RechargeRecordViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_rechargerecord;
        }

        @Event(value = R.id.rootLayout)
        private void click(View v) {
            onItemChildViewClick(v, 99);
        }

        @Override
        protected void update(RechargeRecordItemEntity data) {
            date_1.setText(data.getDepositTime().substring(0, 10));
            date_2.setText(data.getDepositTime().substring(11));
            LogUtil.d("date1:" + data.getDepositTime().substring(0, 10) + ",date2:" + data.getDepositTime().substring(11));

//            ImageLoader.getInstance().displayImage(data.getIcon(), icon, DisplayImageOptionsUtil.getInstance().getUserHeaderOptions());
            if (data.getDepositName().equals("微信")) {
                icon.setImageResource(R.mipmap.icon_wx);
            } else {
                icon.setImageResource(R.mipmap.icon_bank_union_pay);
            }
            money.setText(data.getAmount() + "");
            info.setText(data.getDepositName());

            if (data.getStatus() == 1) {
                status.setText("处理中");
                date_1.setTextColor(context.getResources().getColor(R.color.font_999));
                date_2.setTextColor(context.getResources().getColor(R.color.font_999));
                money.setTextColor(context.getResources().getColor(R.color.font_333));
                info.setTextColor(context.getResources().getColor(R.color.font_666));
                time.setTextColor(context.getResources().getColor(R.color.font_999));
                status.setTextColor(context.getResources().getColor(R.color.font_666));
            } else if (data.getStatus() == 2) {
                status.setText("充值成功");
                date_1.setTextColor(context.getResources().getColor(R.color.font_999));
                date_2.setTextColor(context.getResources().getColor(R.color.font_999));
                money.setTextColor(context.getResources().getColor(R.color.font_333));
                info.setTextColor(context.getResources().getColor(R.color.font_666));
                time.setTextColor(context.getResources().getColor(R.color.font_999));
                status.setTextColor(context.getResources().getColor(R.color.font_666));
            } else {
                status.setText("充值失败");
                date_1.setTextColor(context.getResources().getColor(R.color.font_ccc));
                date_2.setTextColor(context.getResources().getColor(R.color.font_ccc));
                money.setTextColor(context.getResources().getColor(R.color.font_ccc));
                info.setTextColor(context.getResources().getColor(R.color.font_ccc));
                time.setTextColor(context.getResources().getColor(R.color.font_ccc));
                status.setTextColor(context.getResources().getColor(R.color.font_ccc));
            }
        }
    }
}
