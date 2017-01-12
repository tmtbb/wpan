package com.xinyu.mwp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.UserAssetsItemEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class UserAssetsAdapter extends BaseListViewAdapter<UserAssetsItemEntity> {
    public UserAssetsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return TextUtils.isEmpty(getItem(position).getTimeTag()) ? 0 : 1;
    }

    @Override
    protected BaseViewHolder<UserAssetsItemEntity> getViewHolder(int position) {
        if (getItemViewType(position) == 0) {
            return new UserAssetsContentViewHolder(context);
        }
        return new UserAssetsTitleViewHolder(context);
    }

    class UserAssetsTitleViewHolder extends BaseViewHolder<UserAssetsItemEntity> {
        @ViewInject(R.id.name)
        private TextView name;

        public UserAssetsTitleViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_userassets_title;
        }

        @Override
        protected void update(UserAssetsItemEntity data) {
            name.setText(data.getTimeTag());
        }
    }

    class UserAssetsContentViewHolder extends BaseViewHolder<UserAssetsItemEntity> {
        @ViewInject(R.id.time)
        private TextView time;
        @ViewInject(R.id.silverLayout)
        private View silverLayout;
        @ViewInject(R.id.oilLayout)
        private View oilLayout;
        @ViewInject(R.id.cafeLayout)
        private View cafeLayout;

        @ViewInject(R.id.silverMoney)
        private TextView silverMoney;
        @ViewInject(R.id.oilMoney)
        private TextView oilMoney;
        @ViewInject(R.id.cafeMoney)
        private TextView cafeMoney;


        public UserAssetsContentViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_userassets_content;
        }

        @Override
        protected void update(UserAssetsItemEntity data) {
            if (TextUtils.isEmpty(data.getSilverPrice())) {
                silverLayout.setVisibility(View.INVISIBLE);
            } else {
                silverLayout.setVisibility(View.VISIBLE);
                float sm = Float.parseFloat(data.getSilverPrice());
                silverMoney.setTextColor(context.getResources().getColor(sm < 0 ? R.color.default_green : R.color.default_red));
                silverMoney.setText(sm < 0 ? String.valueOf(sm) : "+" + sm);
            }

            if (TextUtils.isEmpty(data.getOilPrice())) {
                oilLayout.setVisibility(View.INVISIBLE);
            } else {
                oilLayout.setVisibility(View.VISIBLE);
                float om = Float.parseFloat(data.getOilPrice());
                oilMoney.setTextColor(context.getResources().getColor(om < 0 ? R.color.default_green : R.color.default_red));
                oilMoney.setText(om < 0 ? String.valueOf(om) : "+" + om);
            }

            if (TextUtils.isEmpty(data.getCafePrice())) {
                cafeLayout.setVisibility(View.INVISIBLE);
            } else {
                cafeLayout.setVisibility(View.VISIBLE);
                float cm = Float.parseFloat(data.getCafePrice());
                cafeMoney.setTextColor(context.getResources().getColor(cm < 0 ? R.color.default_green : R.color.default_red));
                cafeMoney.setText(cm < 0 ? String.valueOf(cm) : "+" + cm);
            }
        }
    }
}
