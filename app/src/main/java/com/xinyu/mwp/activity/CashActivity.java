package com.xinyu.mwp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.WithDrawCashReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactory;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.util.ToastUtils;
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
    @ViewInject(R.id.cash_pwd)
    private CellEditView pwd;
    @ViewInject(R.id.cash_comments)
    private CellEditView cash_comments;
    @ViewInject(R.id.checkCode)
    private CheckCodeView checkCode;

    @ViewInject(R.id.cash)
    private View cash;
    private double price;
    private String password;
    private String comment;


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
        checkButtonState(cash, bank, branch, address, cardNo, cardName, money, pwd);
        money.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                money.setEditTextString(UserManager.getInstance().getUserEntity().getBalance() + "");
                LogUtil.d("提现全部余额:" + UserManager.getInstance().getUserEntity().getBalance());
            }
        });
    }

    @Event(value = {R.id.cash, R.id.rightText})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.cash:
                getInPutInfo();
                requestCash();

                break;
            case R.id.rightText:
                next(CashRecordActivity.class);
                break;
        }
    }

    private void getInPutInfo() {
        String bankInfo = bank.getEditTextString();
        String branchBank = branch.getEditTextString();
        String addressBank = address.getEditTextString();
        String cardNumber = cardNo.getEditTextString();
        String userName = cardName.getEditTextString();
        comment = cash_comments.getEditTextString();
        password = pwd.getEditTextString();
        price = Double.parseDouble(money.getEditTextString());
    }

    private void requestCash() {
        int bankId = 49;
        NetworkAPIFactoryImpl.getDealAPI().cash(price, bankId, password, comment, new OnAPIListener<WithDrawCashReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                ToastUtils.show(context, "网络连接失败");
            }

            @Override
            public void onSuccess(WithDrawCashReturnEntity withDrawCashReturnEntity) {
                LogUtil.d("提现访问网络成功:" + withDrawCashReturnEntity.toString());
//                -1:余额不足，-2：账户不存在 -3:提现密码错误
                switch (withDrawCashReturnEntity.getStatus()) {
                    case -1:
                        ToastUtils.show(context, "余额不足");
                        break;
                    case -2:
                        ToastUtils.show(context, "账户不存在");
                        break;
                    case -3:
                        ToastUtils.show(context, "提现密码错误");
                        break;
                    default:
                        Intent intent = new Intent(CashActivity.this, CashResaultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("cash", withDrawCashReturnEntity);
                        intent.putExtra("tag", bundle);
                        startActivity(intent);
                        break;
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
