package com.xinyu.mwp.view.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/4/26 0026
 * @describe : MyViewPager
 */
public class MyViewPager_1 extends ViewPager {

    private boolean noScroll = true;

    public MyViewPager_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyViewPager_1(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (noScroll) {
            super.scrollTo(x, y);
        }
    }
}
