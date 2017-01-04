package com.xinyu.mwp.view;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotatePagerTransformer implements ViewPager.PageTransformer {

    private static float MAX_SCALE = 25f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setRotation(0);
        } else if (position <= 0) {
            page.setRotation(position * MAX_SCALE);
            page.setPivotX(page.getWidth() / 2);
            page.setPivotY(page.getHeight());
        } else if (position <= 1) {
            page.setRotation(position * MAX_SCALE);
            page.setPivotX(page.getWidth() / 2);
            page.setPivotY(page.getHeight());
        } else {
            page.setRotation(0);
        }
    }
}
