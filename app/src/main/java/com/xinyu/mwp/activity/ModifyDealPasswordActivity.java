package com.xinyu.mwp.activity;

import android.view.View;

/**
 * Created by Benjamin on 17/1/17.
 */

public class ModifyDealPasswordActivity extends ModifyPasswordActivity {
    @Override
    protected String getCosTitle() {
        return "修改交易密码";
    }

    @Override
    protected String getOldPswHint() {
        return "请输入旧的交易密码";
    }

    @Override
    protected String getNewPswHint() {
        return "请输入新的交易密码";
    }

    @Override
    protected void doConfirm(View v) {
        showToast("交易密码");
    }
}
