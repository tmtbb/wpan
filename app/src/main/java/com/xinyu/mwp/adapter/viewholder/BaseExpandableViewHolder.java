package com.xinyu.mwp.adapter.viewholder;

import android.content.Context;
import android.view.View;

import com.xinyu.mwp.listener.OnExpandableItemChildViewClickListener;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;

public abstract class BaseExpandableViewHolder<T> extends BaseViewHolder<T> {

    protected int group;
    protected OnExpandableItemChildViewClickListener onExpandableItemChildViewClickListener;
    protected OnItemChildViewClickListener onItemChildViewClickListener;


    public BaseExpandableViewHolder(Context context) {
        super(context);
    }


    public void setOnExpandableItemChildViewClickListener(OnExpandableItemChildViewClickListener onExpandableItemChildViewClickListener) {
        this.onExpandableItemChildViewClickListener = onExpandableItemChildViewClickListener;
    }

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    /**
     * 更新数据
     *
     * @param group    组索引
     * @param position 索引
     * @param data
     */
    public void update(int group, int position, T data) {
        this.position = position;
        this.group = group;
        update(data);
    }


    /**
     * 子控件点击事件
     *
     * @param childView 事件子控件
     * @param action    活动类型
     * @param obj       额外数据
     */
    protected void onItemChildViewClick(View childView, int action, Object obj) {

        if (onItemChildViewClickListener != null)
            onItemChildViewClickListener.onItemChildViewClick(childView, position, action, obj);
    }

    /**
     * @param childView
     * @param group
     * @param position
     * @param action
     * @param obj
     */
    protected void onItemChildViewClick(View childView, int group, int position, int action, Object obj) {
        if (onExpandableItemChildViewClickListener != null)
            onExpandableItemChildViewClickListener.onItemChildViewClick(childView, group, position, action, obj);
    }

    protected void onItemChildViewClick(View childView, int group, int position, int action) {
        onItemChildViewClick(childView, group, position, action, null);
    }
}
