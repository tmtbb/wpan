package com.xinyu.mwp.activity;


import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.util.ToastUtils;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Random;


/**
 * 买跌
 */

public class BuyMinusActivity extends BaseControllerActivity {


    @ViewInject(R.id.tv_exchange_assets)
    TextView userAssets;  //资产
    @ViewInject(R.id.tv_trade_product_name)
    TextView productName;
    @ViewInject(R.id.tv_buy_countdown)
    TextView countdown; //倒计时
    @ViewInject(R.id.tv_earnest_money)
    TextView earnestMoney; //当前定金
    @ViewInject(R.id.tv_earnest_money_percent)
    TextView earnestMoneyPercent;
    @ViewInject(R.id.tv_turnover_money)
    TextView turnoverMoney; //成交额
    @ViewInject(R.id.tv_trade_service_charge)
    TextView serviceCharge;
    @ViewInject(R.id.rb_trade_freight)
    TextView freight;
    @ViewInject(R.id.rb_trade_return_double)
    TextView returnDouble;
    @ViewInject(R.id.rb_trade_recharge)
    TextView recharge;

    @ViewInject(R.id.btn_buy_cancel)
    Button cancel; //取消
    @ViewInject(R.id.btn_buy_minus)
    Button buyMinus; //买跌

    @ViewInject(R.id.radiogroup1)
    RadioGroup radioGroup1; //手数 父容器


    @Override
    protected int getContentView() {
        return R.layout.activity_buy_minus;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("买跌");

    }

    //第一个参数表示总时间，第二个参数表示间隔时间。
    private CountDownTimer timer = new CountDownTimer(10000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            countdown.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            countdown.setEnabled(true);
            countdown.setText("获取验证码");
        }
    };

    @Override
    protected void initData() {
        super.initData();
        //调用倒计时
        timer.start();
    }

    @Event(value = {
            R.id.rb_trade_recharge, R.id.rb_trade_return_double, R.id.rb_trade_freight, R.id.btn_buy_cancel, R.id.btn_buy_minus,
            R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4, R.id.rb_5, R.id.rb_6, R.id.rb_7, R.id.rb_8, R.id.rb_9, R.id.rb_10})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.rb_trade_recharge:
                ToastUtils.show(context, "立即充值");
                //showToast("");
                next(RechargeActivity.class);
                break;
            case R.id.rb_trade_return_double:
                //  showToast("双倍返还");
                ToastUtils.show(context, "双倍返还");
                break;
            case R.id.rb_trade_freight:
                showToast("货运");
                break;
            case R.id.btn_buy_cancel:
                showToast("取消");
                finish();
                break;
            case R.id.btn_buy_minus:
                showToast("买跌");
                break;
            default:
                for (int i = 0; i < radioGroup1.getChildCount(); i++) {
                    View childAt = radioGroup1.getChildAt(i);
                    if (childAt == v) {
                        //  showToast(i + 1 + "手数");
                        ToastUtils.show(context, i + 1 + "shoushu!");
                    }
                }
                break;
        }
    }
}
