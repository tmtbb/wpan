package com.xinyu.mwp.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.xinyu.mwp.adapter.viewholder.BaseExpandableViewHolder;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.listener.OnExpandableItemChildViewClickListener;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.view.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;


public abstract class MyBaseExpandableListAdapter<T, T1> extends AnimatedExpandableListView.AnimatedExpandableListAdapter
        implements IListAdapter<T> {

    protected Context context;
    protected List<T> lists;

    public MyBaseExpandableListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void setList(List<T> list) {
        this.lists = list;
    }

    @Override
    public void addData(T t) {
        if (this.lists == null)
            this.lists = new ArrayList<T>();
        this.lists.add(t);
    }

    public List<T> getLists() {
        return lists;
    }

    @Override
    public void addList(List<T> list) {
        if (this.lists == null)
            this.lists = list;
        else if (list != null)
            this.lists.addAll(list);
    }

    @Override
    public void remove(int index) {
        if (this.lists != null && index >= 0 && this.lists.size() > index)
            lists.remove(index);
    }

    /**
     * 移除一项数据
     *
     * @param t 数据实体
     */
    public void remove(T t) {
        for (int i = 0; lists != null && i < lists.size(); ++i) {
            if (compareEqual(lists.get(i), t)) {
                lists.remove(i);
                break;
            }
        }
    }

    @Override
    public void clear() {
        if( lists != null )
            lists.clear();
    }

    /**
     * 比较两数据实体是否相等 默认为对象引用地址比较，如果换比较方式重载本函数
     *
     * @param t  list 中的实体
     * @param t1 比较实体
     * @return
     */
    protected boolean compareEqual(T t, T t1) {
        return t == t1;
    }

    @Override
    public List<T> getList() {
        return lists;
    }

    /**
     * ChildView点击事件处理
     */
    protected OnExpandableItemChildViewClickListener onExpandableItemChildViewClickListener;

    public void setOnExpandableItemChildViewClickListener(OnExpandableItemChildViewClickListener onExpandableItemChildViewClickListener) {
        this.onExpandableItemChildViewClickListener = onExpandableItemChildViewClickListener;
    }

    protected void onItemChildViewClick(View childView, int group, int position, int action, Object obj) {
        if (onExpandableItemChildViewClickListener != null)
            onExpandableItemChildViewClickListener.onItemChildViewClick(childView, group, position, action, obj);
    }

    protected void onItemChildViewClick(View childView, int group, int position, int action) {
        onItemChildViewClick(childView, group, position, action, null);
    }

    /**
     * GroupView点击事件处理
     */
    protected OnItemChildViewClickListener onGroupItemChildViewClickListener;

    public void setOnGroupItemChildViewClickListener(OnItemChildViewClickListener onGroupItemChildViewClickListener) {
        this.onGroupItemChildViewClickListener = onGroupItemChildViewClickListener;
    }

    protected void onItemChildViewClick(View childView, int position, int action, Object obj) {
        if (onGroupItemChildViewClickListener != null)
            onGroupItemChildViewClickListener.onItemChildViewClick(childView, position, action, obj);
    }

    protected void onItemChildViewClick(View childView, int position, int action) {
        onItemChildViewClick(childView, position, action, null);
    }


    @Override
    public abstract T1 getChild(int i, int i1);

    @Override
    public int getGroupCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public T getGroup(int i) {
        return lists != null ? lists.get(i) : null;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        BaseViewHolder<T> viewHolder = null;
        if (view == null) {
            viewHolder = getGroupViewHolder(i);
            viewHolder.setOnItemChildViewClickListener(onGroupItemChildViewClickListener);
        } else {
            viewHolder = (BaseViewHolder<T>) view.getTag();
        }
        viewHolder.update(i, getGroup(i));
        return viewHolder.getRootView();
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        BaseExpandableViewHolder<T1> viewHolder = null;
        if (convertView == null) {
            viewHolder = getViewHolder(groupPosition, childPosition);
            viewHolder.setOnExpandableItemChildViewClickListener(onExpandableItemChildViewClickListener);
        } else {
            viewHolder = (BaseExpandableViewHolder<T1>) convertView.getTag();
        }
        viewHolder.update(groupPosition, childPosition, getChild(groupPosition, childPosition));
        return viewHolder.getRootView();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    protected BaseExpandableViewHolder<T1> getViewHolder(int group, int position) {
        return null;
    }

    protected BaseViewHolder<T> getGroupViewHolder(int group) {
        return null;
    }


    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

}
