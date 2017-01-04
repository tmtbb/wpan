package com.xinyu.mwp.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Don on 2016/11/28.
 */

public class Transformer  implements ViewPager.PageTransformer{
    private static final float DEFAULT_MIN_ALPHA = 0.8f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View view, float position) {

            if (position < -1)
            {
                view.setScaleX(mMinAlpha);
                view.setScaleY(mMinAlpha);
            } else if (position <= 1)
            { // [-1,1]

                if (position < 0) //[0，-1]
                {
                    float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                    view.setScaleX(factor);
                    view.setScaleY(factor);
                } else//[1，0]
                {
                    float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                    view.setScaleX(factor);
                    view.setScaleY(factor);
                }
            } else
            { // (1,+Infinity]
                view.setScaleX(mMinAlpha);
                view.setScaleY(mMinAlpha);
            }
        }
}
