package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.util.ToastUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class CheckCodeView extends BaseFrameLayout {
    @ViewInject(R.id.edit)
    private EditText edit;
    @ViewInject(R.id.send)
    private TextView send;

    public CheckCodeView(Context context) {
        super(context);
    }

    public CheckCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_checkcode;
    }

    @Event(value = R.id.send)
    private void click(View v) {
        ToastUtils.show(context, "Send");
    }
}
