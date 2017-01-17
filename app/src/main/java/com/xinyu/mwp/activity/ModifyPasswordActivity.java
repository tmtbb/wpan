package com.xinyu.mwp.activity;

import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.view.ClearEditText;
import com.xinyu.mwp.view.SimpleTextWatcher;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/17.
 */

public abstract class ModifyPasswordActivity extends BaseControllerActivity {
    @ViewInject(R.id.oldPsw)
    private ClearEditText oldPsw;
    @ViewInject(R.id.newPsw)
    private ClearEditText newPsw;
    @ViewInject(R.id.confirm)
    private TextView confirm;

    @Override
    protected int getContentView() {
        return R.layout.activity_modifypsw;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(getCosTitle());
        oldPsw.setHint(getOldPswHint());
        newPsw.setHint(getNewPswHint());
    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(confirm, oldPsw, newPsw);
    }

    @Event(value = R.id.confirm)
    private void click(View v) {
        doConfirm(v);
    }

    private void checkButtonState(final View button, final OnTextChangeListener... editText) {
        button.setEnabled(false);
        for (int i = 0; i < editText.length; i++) {
            final int positon = i;
            editText[i].addITextChangedListener(new SimpleTextWatcher() {
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

    protected abstract String getCosTitle();

    protected abstract String getOldPswHint();

    protected abstract String getNewPswHint();

    protected abstract void doConfirm(View v);

}
