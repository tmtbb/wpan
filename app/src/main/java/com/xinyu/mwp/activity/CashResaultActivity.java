package com.xinyu.mwp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.entity.WithDrawCashReturnEntity;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.util.ToastUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/13.
 */

public class CashResaultActivity extends BaseControllerActivity {
    @ViewInject(R.id.bankIcon)
    private ImageView bankIcon;
    @ViewInject(R.id.bankName)
    private TextView bankName;
    @ViewInject(R.id.money)
    private TextView money;
    @ViewInject(R.id.status)
    private TextView status;
    private WithDrawCashReturnEntity entity;


    @Override
    protected int getContentView() {
        return R.layout.activity_cashresault;
    }

    @Override
    protected void initView() {
        super.initView();
        leftImage.setVisibility(View.GONE);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText("完成");

        Bundle bundle = getIntent().getBundleExtra("tag");
        entity = (WithDrawCashReturnEntity) bundle.getSerializable("cash");
    }

    @Event(value = R.id.rightText)
    private void click(View v) {
        back();
    }

    @Override
    protected void initData() {
        super.initData();

        switch (entity.getStatus()) {
            case 1: //处理中
                status.setText("提现申请已经提交");
                break;
            case 2://成功
                status.setText("提现成功");
                break;
            case 3://失败
                status.setText("提现失败");
                return;
        }
        bankIcon.setImageResource(R.mipmap.icon_bank_ccb);
        bankName.setText(entity.getBank());
        money.setText(entity.getAmount() + "");
    }
}
