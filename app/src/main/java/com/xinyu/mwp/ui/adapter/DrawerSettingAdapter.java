package com.xinyu.mwp.ui.adapter;

import android.content.Context;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseViewHolder;
import com.xinyu.mwp.bean.DrawerSettingBean;

import java.util.List;

/**
 * Created by Don on 2017/1/5.
 * Describe ${TODO}
 * Modified ${TODO}
 */

public class DrawerSettingAdapter extends BaseAdapter<DrawerSettingBean> {

    public DrawerSettingAdapter(Context context, List<DrawerSettingBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convert(Context context, BaseViewHolder holder, DrawerSettingBean bean) {
        holder.setImageResource(R.id.icon, bean.getIcon())
                .setText(R.id.text, bean.getText());
    }
}
