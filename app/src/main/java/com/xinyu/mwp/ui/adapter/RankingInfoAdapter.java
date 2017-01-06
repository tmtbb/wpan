package com.xinyu.mwp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseViewHolder;
import com.xinyu.mwp.bean.ProductBean;
import com.xinyu.mwp.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public class RankingInfoAdapter extends BaseAdapter<ProductBean> {
    public RankingInfoAdapter(Context context, List<ProductBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convert(Context context, BaseViewHolder holder, ProductBean bean) {
        holder.setText(R.id.tv_user_name, bean.getName())
        .setText(R.id.tv_goods,"青铜");

    }

}
