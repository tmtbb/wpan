package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshActivity;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.view.CellView;
import com.xinyu.mwp.view.banner.IndexBannerView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Benjamin on 17/1/13.
 */

public class RechargeActivity extends BaseRefreshActivity {
    @ViewInject(R.id.refreshFrameLayout)
    private PtrFrameLayout refreshFrameLayout;
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
    @ViewInject(R.id.bannerView)
    private IndexBannerView bannerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("充值");
        bannerView.centerDot();
        bannerView.setRefreshLayout(refreshFrameLayout);
        bannerView.update(TestDataUtil.getIndexBanners(3));
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
