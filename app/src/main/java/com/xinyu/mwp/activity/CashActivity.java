package com.xinyu.mwp.activity;

import android.text.Editable;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.view.CellEditView;
import com.xinyu.mwp.view.CheckCodeView;
import com.xinyu.mwp.view.SimpleTextWatcher;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class CashActivity extends BaseControllerActivity {
    @ViewInject(R.id.bank)
    private CellEditView bank;
    @ViewInject(R.id.branch)
    private CellEditView branch;
    @ViewInject(R.id.address)
    private CellEditView address;
    @ViewInject(R.id.cardNo)
    private CellEditView cardNo;
    @ViewInject(R.id.cardName)
    private CellEditView cardName;
    @ViewInject(R.id.money)
    private CellEditView money;
    @ViewInject(R.id.checkCode)
    private CheckCodeView checkCode;

    @ViewInject(R.id.cash)
    private View cash;


    @Override
    protected int getContentView() {
        return R.layout.activity_cash;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("提现");
        rightText.setVisibility(View.VISIBLE);
        rightText.setText("提现记录");
    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(cash, bank, branch, address, cardNo, cardName, money, checkCode);
        money.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                money.setEditTextString("19999.00");
            }
        });
    }

    @Event(value = {R.id.cash, R.id.rightText})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.cash:
                next(CashResaultActivity.class);
                break;
            case R.id.rightText:
                next(CashRecordActivity.class);
                break;
        }
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
}
