package com.xinyu.mwp.activity;

import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CellEditView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class CashActivity extends BaseControllerActivity {
    @ViewInject(R.id.bank)
    private CellEditView back;
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
        money.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                ToastUtils.show(context, "allCash");
            }
        });
    }

    @Event(value = {R.id.cash, R.id.rightText})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.cash:
                ToastUtils.show(context, "Cash");
                break;
            case R.id.rightText:
                next(CashRecordActivity.class);
                break;
        }
    }
}
