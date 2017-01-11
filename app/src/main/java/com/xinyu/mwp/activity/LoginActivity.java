package com.xinyu.mwp.activity;

import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.util.ActivityUtil;

import org.xutils.view.annotation.Event;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-01-11 11:29
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class LoginActivity extends BaseControllerActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("登录");
    }

    @Event(value = {R.id.registerText})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerText:
                ActivityUtil.nextRegister(context);
                break;
        }
    }
}
