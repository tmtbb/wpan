package com.xinyu.mwp.ui.adapter;

import android.content.Context;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseViewHolder;
import com.xinyu.mwp.bean.ProductBean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public class UserInfoAdapter extends BaseAdapter<ProductBean> {
    public UserInfoAdapter(Context context, List<ProductBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convert(Context context, BaseViewHolder holder, ProductBean bean) {
        holder.setText(R.id.tv_create_package, bean.getName())
        .setText(R.id.tv_create_time,"17-01-09 11:07");

    }

}
