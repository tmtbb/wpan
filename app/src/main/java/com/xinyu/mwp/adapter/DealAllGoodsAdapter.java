package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.DealAllGoodsItemEntity;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealAllGoodsAdapter extends BaseListViewAdapter<DealAllGoodsItemEntity> {
    public DealAllGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<DealAllGoodsItemEntity> getViewHolder(int position) {
        return new DealAllGoodsViewHolder(context);
    }

    class DealAllGoodsViewHolder extends BaseViewHolder<DealAllGoodsItemEntity> {

        @ViewInject(R.id.name)
        private TextView time;
        @ViewInject(R.id.buyUp)
        private CellView buyUp;
        @ViewInject(R.id.buyDown)
        private CellView buyDown;
        @ViewInject(R.id.createDepot)
        private CellView createDepot;
        @ViewInject(R.id.flatDepot)
        private CellView flatDepot;

        public DealAllGoodsViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_dealallgoods;
        }

        @Event(value = {R.id.buyUp, R.id.buyDown, R.id.createDepot, R.id.flatDepot})
        private void click(View v) {
            switch (v.getId()) {
                case R.id.buyUp:
                    onItemChildViewClick(v, 99);
                    break;
                case R.id.buyDown:
                    onItemChildViewClick(v, 98);
                    break;
                case R.id.createDepot:
                    onItemChildViewClick(v, 97);
                    break;
                case R.id.flatDepot:
                    onItemChildViewClick(v, 96);
                    break;
            }
        }

        @Override
        protected void initListener() {
            super.initListener();
            OnChildViewClickListener listener = new OnChildViewClickListener() {
                @Override
                public void onChildViewClick(View childView, int action, Object obj) {
                    onItemChildViewClick(childView, action, obj);
                }
            };
            buyUp.setOnChildViewClickListener(listener);
            buyDown.setOnChildViewClickListener(listener);
            createDepot.setOnChildViewClickListener(listener);
            flatDepot.setOnChildViewClickListener(listener);
        }

        @Override
        protected void update(DealAllGoodsItemEntity data) {
            time.setText(data.getTime());
            buyUp.updateContent(data.getBuyUp());
            buyDown.updateContent(data.getBuyDown());
            createDepot.updateContent(data.getCreateDepot());
            flatDepot.updateContent(data.getFlatDepot());
        }
    }
}
