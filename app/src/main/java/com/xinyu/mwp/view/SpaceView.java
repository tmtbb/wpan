package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;

import com.xinyu.mwp.R;

/**
 * Created by Benjamin on 17/1/13.
 */

public class SpaceView extends BaseFrameLayout {
    public SpaceView(Context context) {
        super(context);
    }

    public SpaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_space;
    }
}
