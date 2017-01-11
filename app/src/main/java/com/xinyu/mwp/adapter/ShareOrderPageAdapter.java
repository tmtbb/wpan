package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.ShareOrderPageEntity;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/11 0011
 * @describe : com.xinyu.mwp.adapter
 */
public class ShareOrderPageAdapter extends BaseListViewAdapter<ShareOrderPageEntity> {

    public ShareOrderPageAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<ShareOrderPageEntity> getViewHolder(int position) {
        return new ShareOrderPageViewHolder(context);
    }

    class ShareOrderPageViewHolder extends BaseViewHolder<ShareOrderPageEntity> {

        @ViewInject(R.id.number)
        private TextView number;
        @ViewInject(R.id.numberImage)
        private ImageView numberImage;
        @ViewInject(R.id.headImage)
        private ImageView headImage;
        @ViewInject(R.id.name)
        private TextView name;
        @ViewInject(R.id.content)
        private TextView content;
        private int[] numberImages = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

        public ShareOrderPageViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_share_order_page;
        }

        @Override
        protected void update(ShareOrderPageEntity data) {
            if(data != null){
                if(position < 3){
                    number.setVisibility(View.GONE);
                    numberImage.setVisibility(View.VISIBLE);
                    numberImage.setImageResource(numberImages[position]);
                }else {
                    numberImage.setVisibility(View.GONE);
                    number.setVisibility(View.VISIBLE);
                    number.setText(String.valueOf(position + 1));
                }
                ImageLoader.getInstance().displayImage(data.getHeadUrl(), headImage, DisplayImageOptionsUtil.getInstance().getUserHeaderOptions());
                name.setText(data.getName());
                content.setText(data.getContent());
            }
        }

        @Event(R.id.layout)
        private void onClick(View view){
            onItemChildViewClick(view, 99);
        }
    }
}
