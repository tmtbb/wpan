package com.xinyu.mwp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.entity.BankCardEntity;
import com.xinyu.mwp.entity.CashOutReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.ErrorCodeUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CellEditView;
import com.xinyu.mwp.view.CheckCodeView;
import com.xinyu.mwp.view.SimpleTextWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    private boolean flag = true;
    private BankCardEntity bankCardEntity;
    private String bankName; //银行卡
    private String branchBankName;  //支行
    private String addressBank; //银行地址
    private String cardNumber; //卡号
    private String userName; //用户名

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
        cardNo.getEdit().setInputType(InputType.TYPE_CLASS_NUMBER);
        pwd.getEdit().setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码不可见
        money.getEdit().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        NumberUtils.setEditTextPoint(money.getEdit(), 2);  //设置输入 提现金额的小数位数
        bank.getEdit().setFocusable(false);
        bank.getEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(BindBankCardActivity.class);
            }
        });
        if (flag) {
            EventBus.getDefault().register(this);
            flag = false;
        }
    }

    /*
  *接收消息
   */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(BankCardEntity eventBusMessage) {
        if (eventBusMessage != null) {
            bankCardEntity = eventBusMessage;
            bank.setEditTextString(eventBusMessage.getBank());
            branch.setEditTextString(eventBusMessage.getBranchBank());
            cardNo.setEditTextString(eventBusMessage.getCardNo());
            cardName.setEditTextString(eventBusMessage.getName());
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(cash, bank, branch, cardNo, cardName, money);
        money.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (UserManager.getInstance().getUserEntity() != null) {
                    money.setEditTextString(NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()));
                    LogUtil.d("提现全部余额:" + NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()));
                }
            }
        });
    }

    @Event(value = {R.id.cash, R.id.rightText})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.cash:
                getInPutInfo();
                if (price > UserManager.getInstance().getUserEntity().getBalance()) {
                    ToastUtils.show(context, "余额不足");
                } else if (price == 0) {
                    ToastUtils.show(context, "输入金额有误");
                    return;
                } else {
                    requestCash();
                }
                break;
            case R.id.rightText:
                next(CashRecordActivity.class);
                break;
        }
    }

    private void getInPutInfo() {
        bankName = bank.getEditTextString();
        branchBankName = branch.getEditTextString();
        addressBank = address.getEditTextString();
        cardNumber = cardNo.getEditTextString();
        userName = cardName.getEditTextString();
        comment = cash_comments.getEditTextString();
        password = pwd.getEditTextString();
        price = Double.parseDouble(money.getEditTextString());
    }

    private void requestCash() {
        if (price < 2) {
            ToastUtils.show(context, "提现金额必须大于等于2元");
            return;
        } else if (price > 50000) {
            ToastUtils.show(context, "提现金额超出范围");
            return;
        }
        long bid = bankCardEntity.getBid();
        NetworkAPIFactoryImpl.getDealAPI().cashOut(bankCardEntity.getBid(),
                (long) price, bankName, branchBankName, cardNumber, userName,
                new OnAPIListener<CashOutReturnEntity>() {
                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                        ErrorCodeUtil.showEeorMsg(context, ex);
                        LogUtil.d("第三方提现失败");
                    }

                    @Override
                    public void onSuccess(CashOutReturnEntity cashOutReturnEntity) {
                        LogUtil.d("第三方提现成功:" + cashOutReturnEntity.toString());
                        cashOutReturnEntity.setBank(bankCardEntity.getBank());
//                        withDrawCashReturnEntity.setBank(bankCardEntity.getBank());
//                        withDrawCashReturnEntity.setAmount(price);
//                        UserManager.getInstance().getUserEntity().setBalance(withDrawCashReturnEntity.getBalance());
                        Intent intent = new Intent(CashActivity.this, CashResaultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("cash", cashOutReturnEntity);
                        intent.putExtra("tag", bundle);
                        startActivity(intent);
                        //刷新余额
                        TestDataUtil.requestBalance();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
