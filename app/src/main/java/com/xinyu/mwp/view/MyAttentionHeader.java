package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xinyu.mwp.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyAttentionHeader extends BaseFrameLayout {
    @ViewInject(R.id.name)
    private TextView name;

    public MyAttentionHeader(Context context) {
        super(context);
    }

    public MyAttentionHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_myattentionheader;
    }

    public void update(String name) {
        this.name.setText(name);
    }
}
