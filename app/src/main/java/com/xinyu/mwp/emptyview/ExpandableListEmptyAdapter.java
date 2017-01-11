package com.xinyu.mwp.emptyview;

import android.content.Context;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.MyBaseExpandableListAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.EmptyViewEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-04-14 10:55
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ExpandableListEmptyAdapter extends MyBaseExpandableListAdapter<EmptyViewEntity, EmptyViewEntity> {
    public ExpandableListEmptyAdapter(Context context) {
        super(context);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public EmptyViewEntity getChild(int i, int i1) {

        return null;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    protected BaseViewHolder<EmptyViewEntity> getGroupViewHolder(int group) {
        return new EmptyViewHolder(context);
    }

    class EmptyViewHolder extends BaseViewHolder<EmptyViewEntity> {

        @ViewInject(R.id.emptyView)
        private DefaultEmptyView emptyView;

        public EmptyViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_empty;
        }

        @Override
        protected void update(EmptyViewEntity data) {
            emptyView.update(data);
        }
    }
}
