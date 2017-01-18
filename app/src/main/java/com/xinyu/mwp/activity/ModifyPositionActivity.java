package com.xinyu.mwp.activity;


import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * 修改持仓参数
 */

public class ModifyPositionActivity extends BaseControllerActivity {

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
        return R.layout.activity_modify_position;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("修改持仓参数");
    }

    @Override
    protected void initData() {
        super.initData();

    }


    @Event(value = {
            R.id.rb_position_recharge, R.id.rb_stop_share_bill, R.id.btn_buy_cancel, R.id.btn_buy_minus,
            R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4, R.id.rb_5, R.id.rb_6, R.id.rb_7, R.id.rb_8, R.id.rb_9, R.id.rb_10})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.rb_position_recharge:
                showToast("立即充值");
                break;
            case R.id.rb_stop_share_bill:
                showToast("止盈晒单");
                break;
            case R.id.btn_modify_position_sure:
                showToast("确定");
                finish();
                break;
            default:
                for (int i = 0; i < radioGroup1.getChildCount(); i++) {
                    View childAt = radioGroup1.getChildAt(i);
                    if (childAt == v) {
                        profitLoss.setText(i + 1 + " 元");
                    }
                }
                break;
        }
    }
}
