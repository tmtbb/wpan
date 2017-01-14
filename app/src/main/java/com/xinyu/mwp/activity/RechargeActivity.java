package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshActivity;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/13.
 */

public class RechargeActivity extends BaseRefreshActivity {
    @ViewInject(R.id.account)
    private CellView account;
    @ViewInject(R.id.money)
    private CellView money;
    @ViewInject(R.id.myBankCard)
    private CellView myBankCard;
    @ViewInject(R.id.rechargeMoney)
    private CellView rechargeMoney;
    @ViewInject(R.id.rechargeType)
    private CellView rechargeType;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Event(value = R.id.commit)
    private void click(View v) {
        showToast("Commit");
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        account.updateContentLeft("EW543YTRF3");
                        money.updateContentLeft("56.22元");
                        myBankCard.updateContentLeft("3");
                        rechargeMoney.updateContentLeft("999元");
                        rechargeType.updateContentLeft("AliPay");
                        getRefreshController().getContentView().setVisibility(View.VISIBLE);
                        getRefreshController().refreshComplete();
                    }
                }, 200);
            }
        });
    }
}
