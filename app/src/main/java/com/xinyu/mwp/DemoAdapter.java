package com.xinyu.mwp;

import android.content.Context;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/12/28 0028
 * @describe : com.yaowang.commonproject
 */
public class DemoAdapter extends BaseListViewAdapter<String> {

    public DemoAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<String> getViewHolder(int position) {
        return new DemoViewHolder(context);
    }

    class DemoViewHolder extends BaseViewHolder<String> {

        @ViewInject(R.id.demoTextView)
        private TextView demoTextView;

        public DemoViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_demo;
        }

        @Override
        protected void update(String data) {
            demoTextView.setText(data);
        }
    }
}
