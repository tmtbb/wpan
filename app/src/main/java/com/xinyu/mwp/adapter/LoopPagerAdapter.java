package com.xinyu.mwp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;

import java.security.AccessController;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的循环滑动viewPagerAdapter
 * Created by Administrator on 2017/2/10.
 */
public class LoopPagerAdapter extends PagerAdapter {
    private List<ProductEntity> mUnitViewList;
    private Context context;
    private List<CurrentPriceReturnEntity> entitys;

    public LoopPagerAdapter(Context context, List<ProductEntity> mUnitViewList, List<CurrentPriceReturnEntity> entitys) {
        this.mUnitViewList = mUnitViewList;
        this.context = context;
        this.entitys = entitys;
    }

    @Override
    public int getCount() {
//        return Integer.MAX_VALUE;
        return mUnitViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //对ViewPager页号求模取出View列表中要显示的项
//        position %= mUnitViewList.size();
//        if (position < 0) {
//            position = mUnitViewList.size() + position;
//        }
        View view = LayoutInflater.from(context).inflate(R.layout.ll_trade_time_type, null);
        TextView minText = (TextView) view.findViewById(R.id.tv_trade_min60);
        TextView priceHand = (TextView) view.findViewById(R.id.tv_trade_price_hand);
        TextView serviceCharge = (TextView) view.findViewById(R.id.tv_trade_service_price60);
        if (mUnitViewList != null && entitys != null) {
            LogUtil.d("vpadapter获取mUnitViewList长度" + mUnitViewList.size() + ",entitys长度:" + entitys.size());
            ProductEntity productEntity = mUnitViewList.get(position);
            minText.setText(productEntity.getShowName());
            double unitPrice = entitys.get(position).getCurrentPrice() * productEntity.getDepositFee();
            priceHand.setText(NumberUtils.halfAdjust4(unitPrice) + "/手");

            long current = 30;
            long all = 70;
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2); //即保留两位小数
            serviceCharge.setText("手续费" + nt.format(productEntity.getOpenChargeFee()));
        }
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//        ViewParent vp = view.getParent();
//        if (vp != null) {
//            ViewGroup parent = (ViewGroup) vp;
//            parent.removeView(view);
//        }
//        try {
//            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
//        }catch(Exception e){
//            //handler something
//        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Warning：不要在这里调用removeView
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void updateItemsData(List<ProductEntity> mUnitViewList, List<CurrentPriceReturnEntity> entitys) {
        this.mUnitViewList = mUnitViewList;
        this.entitys = entitys;
        notifyDataSetChanged();
    }
}
