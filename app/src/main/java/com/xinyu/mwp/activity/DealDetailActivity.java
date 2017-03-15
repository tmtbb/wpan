package com.xinyu.mwp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.ViewInject;

import java.text.NumberFormat;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealDetailActivity extends BaseControllerActivity {
    @ViewInject(R.id.rootLayout)
    private View rootLayout;
    @ViewInject(R.id.dealType)
    private CellView dealType;
    @ViewInject(R.id.dealKinds)
    private CellView dealKinds;
    @ViewInject(R.id.dealTime)
    private CellView dealTime;
    @ViewInject(R.id.dealMoney)
    private CellView dealMoney;
    @ViewInject(R.id.earnest_percent)
    private CellView earnestPercent;
    @ViewInject(R.id.dealCounter)
    private CellView dealCounter;
    private HistoryPositionListReturnEntity entity;

    @Override
    protected int getContentView() {
        return R.layout.activity_dealdetail;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("交易详情");
        Bundle bundle = getIntent().getBundleExtra("data");
        entity = (HistoryPositionListReturnEntity) bundle.getSerializable("historyData");
    }

    @Override
    protected void initData() {
        super.initData();
        showLoader();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String type = "";
                if (entity.getBuySell() == -1) {
                    type = "买跌";
                } else if (entity.getBuySell() == 1) {
                    type = "买涨";
                }
                dealType.updateContent(type);
                dealKinds.updateContent(entity.getName());
                dealTime.updateContent(TimeUtil.getDateAndTime(entity.getPositionTime() * 1000));
                dealMoney.updateContent(NumberUtils.halfAdjust2(entity.getOpenCost()));
                NumberFormat numberFormat = NumberFormat.getPercentInstance();
                numberFormat.setMinimumFractionDigits(2);
                dealCounter.updateContent(numberFormat.format(entity.getOpenCharge() * 100));
                earnestPercent.updateContent(numberFormat.format(entity.getOpenCharge() * 100));//定金比例
                rootLayout.setVisibility(View.VISIBLE);
                closeLoader();
            }
        }, 2000);
    }
}
