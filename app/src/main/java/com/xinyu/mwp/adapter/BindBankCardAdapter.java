package com.xinyu.mwp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.BankCardEntity;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.GradientDrawableUtil;
import com.xinyu.mwp.view.SwipeLayout;

import org.xutils.view.annotation.ViewInject;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/16 0016
 * @describe : com.xinyu.mwp.adapter
 */
public class BindBankCardAdapter extends BaseListViewAdapter<BankCardEntity> {

    private PtrFrameLayout ptrFrameLayout;

    public BindBankCardAdapter(Context context) {
        super(context);
    }

    public void setPtrFrameLayout(PtrFrameLayout ptrFrameLayout) {
        this.ptrFrameLayout = ptrFrameLayout;
    }

    @Override
    protected BaseViewHolder<BankCardEntity> getViewHolder(int position) {
        return new BindBankCardViewHolder(context);
    }

    class BindBankCardViewHolder extends BaseViewHolder<BankCardEntity> {

        @ViewInject(R.id.swipeLayout)
        private SwipeLayout swipeLayout;
        @ViewInject(R.id.itemLayout)
        private View itemLayout;
        @ViewInject(R.id.iconView)
        private ImageView iconView;
        @ViewInject(R.id.titleView)
        private TextView titleView;
        @ViewInject(R.id.typeView)
        private TextView typeView;
        @ViewInject(R.id.numberView)
        private TextView numberView;
        @ViewInject(R.id.deleteView)
        private TextView deleteView;

        public BindBankCardViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_bind_bank_card;
        }

        @Override
        protected void initView() {
            super.initView();
            if(ptrFrameLayout != null){
                swipeLayout.setPtrFrameLayout(ptrFrameLayout);
            }
        }

        @Override
        protected void initListener() {
            super.initListener();
            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        protected void update(BankCardEntity data) {
            if(data != null){
                ImageLoader.getInstance().displayImage(data.getIcon(), iconView);
                titleView.setText(data.getTitle());
                typeView.setText(data.getType());
                numberView.setText(data.getNumber());
                Drawable drawable = GradientDrawableUtil.getGradientDrawable(Color.parseColor(data.getBackground()), DisplayUtil.dip2px(5, context));
                itemLayout.setBackgroundDrawable(drawable);
                String menuBg = "#";
                if(data.getBackground().contains("#")){
                    menuBg = data.getBackground().substring(1);
                    menuBg = "#87" + menuBg;
                }
                Drawable menuDrawable = GradientDrawableUtil.getGradientDrawable(Color.parseColor(menuBg), DisplayUtil.dip2px(5, context));
                deleteView.setBackgroundDrawable(menuDrawable);
            }
        }
    }
}
