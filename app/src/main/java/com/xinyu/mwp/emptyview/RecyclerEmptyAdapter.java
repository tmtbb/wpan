package com.xinyu.mwp.emptyview;

import android.content.Context;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.BaseListDataRecyclerViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseRecyclerViewHolder;
import com.xinyu.mwp.entity.EmptyViewEntity;
import com.xinyu.mwp.listener.OnChildViewClickListener;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-04-14 10:01
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class RecyclerEmptyAdapter extends BaseListDataRecyclerViewAdapter<EmptyViewEntity> {
    public RecyclerEmptyAdapter(Context context) {
        super(context);
    }

    public RecyclerEmptyAdapter(Context context, List<EmptyViewEntity> list) {
        super(context, list);
    }

    @Override
    protected BaseRecyclerViewHolder<EmptyViewEntity> getViewHolder(int viewType) {
        return new EmptyViewHolder(context);
    }

    private class EmptyViewHolder extends BaseRecyclerViewHolder<EmptyViewEntity> {
        @ViewInject(R.id.emptyView)
        protected DefaultEmptyView emptyView;

        public EmptyViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_empty;
        }

        @Override
        protected void initListener() {
            super.initListener();
            emptyView.setOnChildViewClickListener(new OnChildViewClickListener() {
                @Override
                public void onChildViewClick(View childView, int action, Object obj) {
                    onItemChildViewClick(childView,action,obj);
                }
            });
        }

        @Override
        protected void update(EmptyViewEntity data) {
            emptyView.update(data);
        }
    }
}
