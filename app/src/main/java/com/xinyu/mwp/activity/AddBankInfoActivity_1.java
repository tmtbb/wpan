package com.xinyu.mwp.activity;

import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseActivity;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.view.CellEditView;
import com.xinyu.mwp.view.CellView;
import com.xinyu.mwp.view.SimpleTextWatcher;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/16.
 */

public class AddBankInfoActivity_1 extends BaseActivity {
    @ViewInject(R.id.person)
    private CellView person;
    @ViewInject(R.id.cardNumber)
    private CellEditView cardNumber;
    @ViewInject(R.id.next)
    private TextView next;

    @Override
    protected int getContentView() {
        return R.layout.activity_addbankinfo_1;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("填写银行卡号信息");
    }

    @Event(value = {R.id.next})
    private void click(View v) {
        showToast("下一步");
    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(next, cardNumber);
        cardNumber.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (action == 98) {
                    showToast("扫描银行卡");
                }
            }
        });
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
}
