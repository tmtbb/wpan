package com.xinyu.mwp.activity;


import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.UserEntity;
import com.xinyu.mwp.exception.CheckException;
import com.xinyu.mwp.helper.CheckHelper;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.ActivityUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.SHA256Util;
import com.xinyu.mwp.util.SPUtils;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.view.WPEditText;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


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
    private long exitNow;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("登录");
        leftImage.setVisibility(View.INVISIBLE);
        userNameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(loginButton, userNameEditText, passwordEditText);
        setSwipeBackEnable(false);

    }


    @Event(value = {R.id.registerText, R.id.loginButton, R.id.findPwd})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerText:
                ActivityUtil.nextRegister(context);
                break;
//            case R.id.findPwd:    //去掉修改登录密码功能
//                ActivityUtil.nextResetUserPwd(context);
//                break;
            case R.id.loginButton:
                showLoader("正在登录...");
                LogUtil.d("登录,此时的网络链接状态是:"+SocketAPINettyBootstrap.getInstance().isOpen());
                CheckException exception = new CheckException();
                if (checkHelper.checkMobile(userNameEditText.getEditTextString(), exception)
                        && checkHelper.checkPassword(passwordEditText.getEditTextString(), exception)) {
                    Utils.closeSoftKeyboard(view);
                    String newPwd = SHA256Util.shaEncrypt(SHA256Util.shaEncrypt(passwordEditText.getEditTextString() + "t1@s#df!") + userNameEditText.getEditTextString());
                    NetworkAPIFactoryImpl.getUserAPI().login(userNameEditText.getEditTextString(), newPwd, null,
                            new OnAPIListener<LoginReturnEntity>() {
                                @Override
                                public void onError(Throwable ex) {
                                    ex.printStackTrace();
                                    closeLoader();
                                    if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
                                        ToastUtils.show(context, "网络连接失败,请检查网络连接");
                                    } else {
                                        ToastUtils.show(context, "登录失败");
                                    }
                                }

                                @Override
                                public void onSuccess(LoginReturnEntity loginReturnEntity) {
                                    closeLoader();
                                    ToastUtils.show(context, "登陆成功");
                                    LogUtil.d("登陆成功:" + loginReturnEntity.toString());
                                    UserEntity en = new UserEntity();
                                    en.setBalance(loginReturnEntity.getUserinfo().getBalance());
                                    en.setId(loginReturnEntity.getUserinfo().getId());
                                    en.setToken(loginReturnEntity.getToken());
                                    en.setName(loginReturnEntity.getUserinfo().getMemberName());
                                    en.setNickname(loginReturnEntity.getUserinfo().getScreenName());
                                    en.setLevelsName(loginReturnEntity.getUserinfo().getMemberName());

                                    en.setMobile(userNameEditText.getEditTextString());
                                    UserManager.getInstance().saveUserEntity(en);
                                    UserManager.getInstance().setLogin(true);
                                    MyApplication.getApplication().onUserUpdate(true);
                                    SPUtils.putString("phone", userNameEditText.getEditTextString());
                                    finish();
                                }
                            });
                } else {
                    closeLoader();
                    showToast(exception.getErrorMsg());
                }
            break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)) {

            if ((System.currentTimeMillis() - exitNow) > 2000) {
                Toast.makeText(this, String.format(getString(R.string.confirm_exit_app), getString(R.string.app_name)), Toast.LENGTH_SHORT).show();
                exitNow = System.currentTimeMillis();
            } else if ((System.currentTimeMillis() - exitNow) > 0) {
                MyApplication.getApplication().exitApp(this);
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
