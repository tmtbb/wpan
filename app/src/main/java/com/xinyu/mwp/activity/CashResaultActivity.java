package com.xinyu.mwp.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;

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
    }

    @Event(value = R.id.rightText)
    private void click(View v) {
        back();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
