package com.xinyu.mwp.activity;

import android.view.View;

/**
 * Created by Benjamin on 17/1/17.
 */

public class ModifyLoginPasswordActivity extends ModifyPasswordActivity {
    @Override
    protected String getCosTitle() {
        return "修改登录密码";
    }

    @Override
    protected String getOldPswHint() {
        return "请输入旧的登录密码";
    }

    @Override
    protected String getNewPswHint() {
        return "请输入新的登录密码";
    }

    @Override
    protected void doConfirm(View v) {
        showToast("登录密码");
    }
}
