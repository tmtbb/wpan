package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.UnitEntity;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyAttentionAdapter extends BaseListViewAdapter<UnitEntity> {
    public MyAttentionAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<UnitEntity> getViewHolder(int position) {
        return new MyAttentionViewHolder(context);
    }

    class MyAttentionViewHolder extends BaseViewHolder<UnitEntity> {

        @ViewInject(R.id.index)
        private TextView index;
        @ViewInject(R.id.icon)
        private ImageView icon;
        @ViewInject(R.id.name)
        private TextView name;

        public MyAttentionViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_myattention;
        }

        @Event(value = R.id.rootLayout)
        private void click(View v) {
            onItemChildViewClick(v, 99);
        }

        @Override
        protected void update(UnitEntity data) {
            index.setText(String.valueOf(position + 1));
            ImageLoader.getInstance().displayImage(data.getIcon(), icon, DisplayImageOptionsUtil.getInstance().getUserHeaderOptions());
            name.setText(data.getName());
        }
    }
}
