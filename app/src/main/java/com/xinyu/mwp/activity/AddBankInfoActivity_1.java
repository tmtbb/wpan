package com.xinyu.mwp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseActivity;
import com.xinyu.mwp.entity.BankInfoEntity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.util.ActivityUtil;
import com.xinyu.mwp.util.ErrorCodeUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.StringUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CellEditView;
import com.xinyu.mwp.view.CellView;
import com.xinyu.mwp.view.SimpleTextWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/16.
 */

public class AddBankInfoActivity_1 extends BaseActivity {
    @ViewInject(R.id.person)
    private CellEditView person;
    @ViewInject(R.id.cardNumber)
    private CellEditView cardNumber;
    @ViewInject(R.id.next)
    private TextView next;
    private boolean flag = true;

    @Override
    protected int getContentView() {
        return R.layout.activity_addbankinfo_1;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("填写银行卡号信息");
        if (flag) {
            EventBus.getDefault().register(this);
            flag = false;
        }
    }

    @Event(value = {R.id.next})
    private void click(View v) {
        NetworkAPIFactoryImpl.getDealAPI().bankName(cardNumber.getEditTextString(), new OnAPIListener<BankInfoEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
//                ToastUtils.show(context,"请输入正确的银行卡号");
                ErrorCodeUtil.showEeorMsg(context,ex);
            }

            @Override
            public void onSuccess(BankInfoEntity bankInfoEntity) {
                LogUtil.d("获取银行卡信息成功:" + bankInfoEntity.toString());
                Intent intent = new Intent(context, AddBankInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bankInfo", bankInfoEntity);
                intent.putExtra("tag", bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();
        checkButtonState(next, cardNumber);
//        cardNumber.setOnChildViewClickListener(new OnChildViewClickListener() {
//            @Override
//            public void onChildViewClick(View childView, int action, Object obj) {
//                if (action == 98) {
//                    showToast("扫描银行卡");
//                }
//            }
//        });
    }

    /*
    *接收消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(EventBusMessage eventBusMessage) {
        if (eventBusMessage.Message == -3) {
            finish();
        }
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
//        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
