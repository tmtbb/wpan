package com.xinyu.mwp.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;



public class CountUtil {

    private CountDownTimer countDownTimer;
    private TextView button;

    public CountUtil(final TextView button) {

        this.button = button;
        countDownTimer = new CountDownTimer(60 * 1000, 1 * 1000 - 10) {

            @Override
            public void onTick(long time) {
                button.setVisibility(View.VISIBLE);
                button.setText(((time + 15) / 1000) + "S后重发");
                //button.setBackgroundResource(R.drawable.login_shape_unenable);
                button.setTextColor(Color.WHITE);
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText("发送验证码");
               // button.setBackgroundResource(R.drawable.login_selector);
                button.setTextColor(Color.WHITE);
            }
        };
    }


    public void start() {
        button.setEnabled(false);
        countDownTimer.start();
    }

}
