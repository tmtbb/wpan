package com.xinyu.mwp.activity.unionpay;

import android.app.Activity;
import android.widget.TextView;

import com.unionpay.UPPayAssistEx;

/**
 * 银联相关--jar接入
 */
public class JARActivity extends BaseUnionPayActivity {

    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
        UPPayAssistEx.startPay(activity, null, null, tn, mode);
    }
}
