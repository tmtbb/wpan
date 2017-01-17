package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.RechargeRecordItemEntity;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;

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
            date_1.setText(data.getTimeWeek());
            date_2.setText(data.getTimeDate());
            ImageLoader.getInstance().displayImage(data.getIcon(), icon, DisplayImageOptionsUtil.getInstance().getUserHeaderOptions());
            money.setText(data.getMoney());
            info.setText(data.getInfo());
            time.setText(data.getTime());

            if ("1".equals(data.getStatus())) {
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
