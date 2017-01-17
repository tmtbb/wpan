package com.xinyu.mwp.activity;


import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * 买跌
 */

public class BuyMinusActivity extends BaseControllerActivity {

    @ViewInject(R.id.tv_deal_product_name)
    TextView dealProductName;
    @ViewInject(R.id.tv_deal_product_price)
    TextView dealProductPrice;

    @ViewInject(R.id.tv_profit_loss)
    TextView profitLoss;  //波动盈亏
    @ViewInject(R.id.radiogroup1)
    RadioGroup radioGroup1;


    @Override
    protected int getContentView() {
        return R.layout.activity_buy_minus;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("买跌");
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Event(value = {R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4, R.id.rb_5, R.id.rb_6, R.id.rb_7, R.id.rb_8, R.id.rb_9, R.id.rb_10})
    private void click(View v) {

        for (int i = 0; i < radioGroup1.getChildCount(); i++) {
            View childAt = radioGroup1.getChildAt(i);
            if (childAt == v) {
                profitLoss.setText(i + 1 + " 元");
            }
        }

    }
}
