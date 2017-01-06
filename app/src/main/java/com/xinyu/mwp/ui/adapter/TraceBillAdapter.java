package com.xinyu.mwp.ui.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseViewHolder;
import com.xinyu.mwp.bean.TraceBillBean;

import java.util.List;

/**
 * Created by Don on 2017/1/6.
 * Describe ${TODO}
 * Modified ${TODO}
 */
public class TraceBillAdapter extends BaseAdapter<TraceBillBean> {

    public TraceBillAdapter(Context context, List<TraceBillBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convert(Context context, BaseViewHolder holder, TraceBillBean traceBillBean) {
        holder.setImageResource(R.id.civ_trace_bill_imgRes, traceBillBean.getImgRes())
                .setText(R.id.tv_trace_bill_name, traceBillBean.getPersonName())
                .setText(R.id.tv_trace_bill_product, SpannableStringBuilder.valueOf(traceBillBean.getProduct()))
                .setText(R.id.tv_trace_bill_over_time, traceBillBean.getProductOverTime())
                .setText(R.id.tv_trace_bill_create_time, traceBillBean.getProductCreateTime())
                .setText(R.id.tv_trace_bill_price, String.format("%.1f", traceBillBean.getProductPrice()))
                .setText(R.id.tv_trace_bill_person_num, String.valueOf(traceBillBean.getNumPersonAttention()));
    }
}
