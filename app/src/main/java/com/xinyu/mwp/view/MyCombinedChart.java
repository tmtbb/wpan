package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.CombinedChart;

/**
 * 解决触摸拦截冲突
 * Created by Administrator on 2017/1/18.
 */
public class MyCombinedChart extends CombinedChart {
    public MyCombinedChart(Context context) {
        super(context);
    }

    public MyCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
