package com.xinyu.mwp.activity;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.exception.CheckException;
import com.xinyu.mwp.helper.CheckHelper;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.util.ActivityUtil;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.view.WPEditText;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import io.netty.channel.nio.NioEventLoopGroup;

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

    @ViewInject(R.id.userNameEditText)
    private WPEditText userNameEditText;
    @ViewInject(R.id.passwordEditText)
    private WPEditText passwordEditText;
    @ViewInject(R.id.loginButton)
    private Button loginButton;
    private CheckHelper checkHelper = new CheckHelper();


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("登录");
        userNameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(loginButton, userNameEditText, passwordEditText);

    }


    @Event(value = {R.id.registerText, R.id.loginButton, R.id.findPwd})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerText:
                ActivityUtil.nextRegister(context);
                break;
            case R.id.findPwd:
                ActivityUtil.nextResetUserPwd(context);
                break;
            case R.id.loginButton: {
                CheckException exception = new CheckException();
                if (checkHelper.checkMobile(userNameEditText.getEditTextString(), exception)
                        && checkHelper.checkPassword(passwordEditText.getEditTextString(), exception)) {
                    Utils.closeSoftKeyboard(view);
                    NetworkAPIFactoryImpl.getUserAPI().login(userNameEditText.getEditTextString(), passwordEditText.getEditTextString(), new OnAPIListener<LoginReturnEntity>() {
                        @Override
                        public void onError(Throwable ex) {

                        }

                        @Override
                        public void onSuccess(LoginReturnEntity loginReturnEntity) {

                        }
                    });
                } else {
                    showToast(exception.getErrorMsg());
                }
            }
            break;
        }
    }

    @Override
    public void back() {
        Utils.closeSoftKeyboard(rootView);
        super.back();
    }
}
