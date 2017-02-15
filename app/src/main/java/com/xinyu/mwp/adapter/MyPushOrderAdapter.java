package com.xinyu.mwp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.MyPushOrderEntity;
import com.xinyu.mwp.entity.MyPushOrderItemEntity;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CustomDialog;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyPushOrderAdapter extends BaseListViewAdapter<MyPushOrderItemEntity> {

    private static final int TYPE_WIN = 0; //本月,本周胜率
    private static final int TYPE_ORDER = 1; //好友订单信息

    public MyPushOrderAdapter(Context context) {
        super(context);
    }

    /**
     * 自定义itemType
     *
     * @param position 条目位置
     * @return type类型
     */
    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (position < 2) {
            viewType = TYPE_WIN;
        } else {
            viewType = TYPE_ORDER;
        }
        return viewType;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    protected BaseViewHolder<MyPushOrderItemEntity> getViewHolder(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_WIN) {
            return new MyPushOrderViewHolder(context);
        } else {
            return new MyPushOrderInfoViewHolder(context);
        }
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
            LogUtil.d(" sss+data.getProfit()" + data.getProfit());
            float sp = 0;
            if (data.getProfit() != null) {
                sp = Float.parseFloat(data.getProfit());
            }

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

    //推单,好友推单详情
    private class MyPushOrderInfoViewHolder extends BaseViewHolder<MyPushOrderItemEntity> {
        @ViewInject(R.id.tv_push_order_name)
        private TextView orderNname;
        @ViewInject(R.id.tv_push_order_open_time)
        private TextView time;
        @ViewInject(R.id.tv_push_order_price)
        private TextView orderPrice;
        @ViewInject(R.id.btn_follow_order)
        private Button followOrder;

        public MyPushOrderInfoViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_push_order_info;
        }

        @Override
        protected void update(MyPushOrderItemEntity data) {
            time.setText(data.getTime());
            orderPrice.setText(data.getOrderPrice());
            orderNname.setText(data.getOrderNname());
        }

        @Event(value = R.id.btn_follow_order)
        private void click(View v) {
           //需要判断被点击的是买涨,买跌,然后跟单
            showDialog(Constant.TYPE_BUY_MINUS);
        }

        private void showDialog(int type) {
            String buyType = null;
            if (type == Constant.TYPE_BUY_MINUS) {
                buyType = "买跌";
            } else if (type == Constant.TYPE_BUY_PLUS) {
                buyType = "买涨";
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(context, type);

            builder.setPositiveButton(buyType, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //买涨点击后操作
                    ToastUtils.show(context, "买跌");
                }
            });

            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //取消点击后操作
                            ToastUtils.show(context, "取消");
                        }
                    });
            builder.create().show();
        }
    }
}
