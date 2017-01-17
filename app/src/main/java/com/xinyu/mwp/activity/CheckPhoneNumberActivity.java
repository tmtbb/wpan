package com.xinyu.mwp.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseActivity;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.util.CountUtil;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.view.SimpleTextWatcher;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/16.
 */

public class CheckPhoneNumberActivity extends BaseActivity implements OnTextChangeListener {
    @ViewInject(R.id.edit)
    private EditText edit;
    @ViewInject(R.id.send)
    private TextView send;
    @ViewInject(R.id.next)
    private TextView next;

    @Override
    protected int getContentView() {
        return R.layout.activity_checkphonenumber;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("验证手机号");
    }

    @Event(value = {R.id.send, R.id.next})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.send:
                new CountUtil(send, "S", true).start();
                break;
            case R.id.next:
                showToast("下一步");
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(next, this);
    }

    private void checkButtonState(final View button, final OnTextChangeListener... editText) {
        button.setEnabled(false);
        for (int i = 0; i < editText.length; i++) {
            final int positon = i;
            editText[i].addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable editable) {
                    super.afterTextChanged(editable);
                    if (!StringUtil.isEmpty(editable.toString())) {
                        boolean enable = true;
                        for (int j = 0; j < editText.length; j++) {
                            if (StringUtil.isEmpty(editText[j].getEditTextString())) {
                                enable = false;
                                break;
                            }
                        }
                        button.setEnabled(enable);
                    } else {
                        button.setEnabled(false);
                    }
                }
            });
        }
    }

    @Override
    public void addTextChangedListener(TextWatcher textWatcher) {
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
