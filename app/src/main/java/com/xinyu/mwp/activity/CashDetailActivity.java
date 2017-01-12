package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/13.
 */

public class CashDetailActivity extends BaseControllerActivity {
    @ViewInject(R.id.rootLayout)
    private View rootLayout;
    @ViewInject(R.id.cashBank)
    private CellView cashBank;
    @ViewInject(R.id.cashTime)
    private CellView cashTime;
    @ViewInject(R.id.maybeCashGetTime)
    private CellView maybeCashGetTime;
    @ViewInject(R.id.cashGetTime)
    private CellView cashGetTime;

    @Override
    protected int getContentView() {
        return R.layout.activity_cashdetail;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("提现详情");
    }

    @Override
    protected void initData() {
        super.initData();
        showLoader();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cashBank.updateContent("中国工商银行");
                cashTime.updateContent("2016.01.01 16:15:15");
                maybeCashGetTime.updateContent("2016.01.01 16:15:15");
                cashGetTime.updateContent("未完成");
                rootLayout.setVisibility(View.VISIBLE);
                closeLoader();
            }
        }, 2000);
    }
}
