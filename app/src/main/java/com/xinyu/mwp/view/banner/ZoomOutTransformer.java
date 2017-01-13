package com.xinyu.mwp.view.banner;

import android.view.View;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-10-08 14:54
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ZoomOutTransformer extends AbsTransformer {
    @Override
    protected void onTransform(View view, float position) {
        final float scale = 1f + Math.abs(position);
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setAlpha(position < -1f || position > 1f ? 0f : 1f - (scale - 1f));
        if (position == -1) {
            view.setTranslationX(view.getWidth() * -1);
        }
    }
}
