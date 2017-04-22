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
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.fragment.DealProductPageFragment;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;

import java.text.NumberFormat;
import java.util.List;

/**
 * 自定义的循环滑动viewPagerAdapter
 * Created by Administrator on 2017/2/10.
 */
public class LoopPagerAdapter extends PagerAdapter {
    private List<ProductEntity> mNewUnitList;
    private Context context;

    public LoopPagerAdapter(Context context, List<ProductEntity> mNewUnitList) {
        this.mNewUnitList = mNewUnitList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mNewUnitList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.ll_trade_time_type, null);
        TextView minText = (TextView) view.findViewById(R.id.tv_trade_min60);
        TextView priceHand = (TextView) view.findViewById(R.id.tv_trade_price_hand);
        TextView serviceCharge = (TextView) view.findViewById(R.id.tv_trade_service_price60);
        if (mNewUnitList != null) {
            ProductEntity productEntity = mNewUnitList.get(position);
            minText.setText(productEntity.getShowName());
            double unitPrice = mNewUnitList.get(position).getCurrentPrice() * productEntity.getDepositFee();
            priceHand.setText(NumberUtils.halfAdjust4(unitPrice) + "/手");
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2); //即保留两位小数
            serviceCharge.setText("手续费" + nt.format(productEntity.getOpenChargeFee()));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void updateItemsData(List<ProductEntity> mNewUnitList) {
        this.mNewUnitList = mNewUnitList;
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

    private int mChildCount = 0;

    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        // 重写getItemPosition,保证每次获取时都强制重绘UI
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
