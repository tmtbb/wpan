package com.xinyu.mwp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseActivity;
import com.xinyu.mwp.entity.BankInfoEntity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.entity.WithDrawCashReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CellEditView;
import com.xinyu.mwp.view.CellView;
import com.xinyu.mwp.view.SimpleTextWatcher;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/16.
 */

public class AddBankInfoActivity extends BaseActivity {
    @ViewInject(R.id.cardType)
    private CellView cardType;
    @ViewInject(R.id.branch)
    private CellEditView branch;
    @ViewInject(R.id.person)
    private CellEditView person;
    @ViewInject(R.id.phoneNumber)
    private CellEditView phoneNumber;
    @ViewInject(R.id.agreement)
    private TextView agreement;
    @ViewInject(R.id.next)
    private TextView next;
    private BankInfoEntity entity;

    @Override
    protected int getContentView() {
        return R.layout.activity_addbankinfo;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("填写银行卡号信息");
        agreement.setText(Html.fromHtml("同意<font color=\"#E9573E\">《用户协议》</font>"));

        Bundle bundle = getIntent().getBundleExtra("tag");
        entity = (BankInfoEntity) bundle.getSerializable("bankInfo");
        cardType.updateContentLeft(entity.getBankName());

    }

    @Event(value = {R.id.next})
    private void click(View v) {
        LogUtil.d("绑定银行卡");
        final long bankId = entity.getBankId();
        String branchBank = branch.getEditTextString();
        String cardNO = entity.getCardNO();
        String name = person.getEditTextString();
        String bankName = entity.getBankName();
        NetworkAPIFactoryImpl.getDealAPI().bindCard(bankId, bankName, branchBank, cardNO, name, new OnAPIListener<BankInfoEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                ToastUtils.show(context, "绑定失败,请检查输入信息");
            }

            @Override
            public void onSuccess(BankInfoEntity bankInfoEntity) {
                ToastUtils.show(context,"绑定成功!");
                LogUtil.d("绑定成功ssss,发送消息3");
                EventBus.getDefault().postSticky(new EventBusMessage(-3));  //传递消息
                finish();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(next, branch, person, phoneNumber);
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
