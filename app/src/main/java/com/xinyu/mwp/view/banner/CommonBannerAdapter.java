package com.xinyu.mwp.view.banner;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-10-06 11:54
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public abstract class CommonBannerAdapter extends PagerAdapter {
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(LayoutInflater.from(container.getContext()), position);
        container.addView(view);
        return view;
    }

    public int getPositionForIndicator(int position) {
        return position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public abstract View getView(LayoutInflater layoutInflater, int position);

    public abstract int getRealCount();

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
