package com.xinyu.mwp.view.banner;

import android.view.View;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-10-08 18:18
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class AccordionTransformer extends AbsTransformer {
    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setScaleX(position < 0 ? 1f + position : 1f - position);
    }
}
