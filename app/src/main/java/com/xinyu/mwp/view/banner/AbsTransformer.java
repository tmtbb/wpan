package com.xinyu.mwp.view.banner;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-10-08 14:52
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public abstract class AbsTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        onPreTransform(page, position);
        onTransform(page, position);
        onPostTransform(page, position);
    }

    protected abstract void onTransform(View page, float position);

    protected boolean hideOffscreenPages() {
        return true;
    }

    protected boolean isPagingEnabled() {
        return false;
    }

    protected void onPreTransform(View page, float position) {
        final float width = page.getWidth();

        page.setRotationX(0);
        page.setRotationY(0);
        page.setRotation(0);
        page.setScaleX(1);
        page.setScaleY(1);
        page.setPivotX(0);
        page.setPivotY(0);
        page.setTranslationY(0);
        page.setTranslationX(isPagingEnabled() ? 0f : -width * position);

        if (hideOffscreenPages()) {
            page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
        } else {
            page.setAlpha(1f);
        }
    }

    protected void onPostTransform(View page, float position) {
    }

    protected static final float min(float val, float min) {
        return val < min ? min : val;
    }
}
