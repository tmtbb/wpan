package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;

public abstract class BaseDataLinearLayout<T> extends BaseLinearLayout {
    public BaseDataLinearLayout(Context context) {
        super(context);
    }

    public BaseDataLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void update(T data);
}
