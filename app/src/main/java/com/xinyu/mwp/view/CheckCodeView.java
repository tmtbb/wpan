package com.xinyu.mwp.view;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.util.CountUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class CheckCodeView extends BaseFrameLayout implements OnTextChangeListener {
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
        new CountUtil(send, "S", true).start();
    }

    @Override
    public void addITextChangedListener(TextWatcher textWatcher) {
        if (edit != null)
            edit.addTextChangedListener(textWatcher);
    }

    @Override
    public String getEditTextString() {
        if (edit != null) {
            return edit.getText().toString().trim();
        }
        return "";
    }
}
