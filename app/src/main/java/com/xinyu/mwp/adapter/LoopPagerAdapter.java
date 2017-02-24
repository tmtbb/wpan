package com.xinyu.mwp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.util.LogUtil;

import java.security.AccessController;
import java.util.List;

/**
 * 自定义的循环滑动viewPagerAdapter
 * Created by Administrator on 2017/2/10.
 */
public class LoopPagerAdapter extends PagerAdapter {
    private List<ProductEntity> mUnitViewList;
    private Context context;

    public LoopPagerAdapter(Context context, List<ProductEntity> mUnitViewList) {
        this.mUnitViewList = mUnitViewList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //对ViewPager页号求模取出View列表中要显示的项
        position %= mUnitViewList.size();
        if (position < 0) {
            position = mUnitViewList.size() + position;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.ll_trade_time_type, null);
        TextView minText = (TextView) view.findViewById(R.id.tv_trade_min60);
        TextView priceText = (TextView) view.findViewById(R.id.tv_trade_price60);
        LogUtil.d("mUnitViewList长度是:" + mUnitViewList.size());
        if (mUnitViewList != null){
            ProductEntity productEntity = mUnitViewList.get(position);
            minText.setText(productEntity.getShowName());
        }
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Warning：不要在这里调用removeView
    }
}
