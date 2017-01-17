package com.xinyu.mwp.activity;

import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseActivity;
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

public class AddBankInfoActivity extends BaseActivity {
    @ViewInject(R.id.cardType)
    private CellView cardType;
    @ViewInject(R.id.phoneNumber)
    private CellEditView phoneNumber;
    @ViewInject(R.id.agreement)
    private TextView agreement;
    @ViewInject(R.id.next)
    private TextView next;

    @Override
    protected int getContentView() {
        return R.layout.activity_addbankinfo;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("填写银行卡号信息");
        agreement.setText(Html.fromHtml("同意<font color=\"#E9573E\">《用户协议》</font>"));
    }

    @Event(value = {R.id.next})
    private void click(View v) {
        next(AddBankInfoActivity_1.class);
    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(next, phoneNumber);
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
