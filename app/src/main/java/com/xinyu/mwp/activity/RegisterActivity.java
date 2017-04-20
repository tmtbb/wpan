package com.xinyu.mwp.activity;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.RegisterReturnEntity;
import com.xinyu.mwp.entity.UserEntity;
import com.xinyu.mwp.exception.CheckException;
import com.xinyu.mwp.helper.CheckHelper;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.SHA256Util;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.util.VerifyCodeUtils;
import com.xinyu.mwp.view.WPEditText;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-01-11 11:30
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class RegisterActivity extends BaseControllerActivity {
    @ViewInject(R.id.phoneEditText)
    private WPEditText phoneEditText;
    @ViewInject(R.id.msgEditText)
    private WPEditText msgEditText;
    //    @ViewInject(R.id.soundEditText)
    //    private WPEditText soundEditText;
    @ViewInject(R.id.pwdEditText)
    private WPEditText pwdEditText;
    @ViewInject(R.id.nextButton)
    private Button nextButton;
    private CheckHelper checkHelper = new CheckHelper();
    private RegisterReturnEntity registerEntity;
    private String phone;
    private String pwd;
    private String vCode;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("注册");
        phoneEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(nextButton, phoneEditText, msgEditText, pwdEditText);
        checkHelper.checkVerificationCode(msgEditText.getRightText(), phoneEditText);

    }

    @Override
    protected void initListener() {
        super.initListener();

        msgEditText.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
                    ToastUtils.show(context, "网络连接失败,请检查网络");
                    return;
                }
                int verifyType = 0;// 0-注册 1-登录 2-更新服务
                VerifyCodeUtils.getCode(msgEditText, verifyType, context, view, phoneEditText);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader("正在注册...");
                CheckException exception = new CheckException();
                phone = phoneEditText.getEditTextString();
                pwd = pwdEditText.getEditTextString();
                vCode = msgEditText.getEditTextString();

                if (checkHelper.checkMobile(phone, exception) && checkHelper.checkPassword(pwd, exception)
                        && checkHelper.checkVerifyCode(vCode, exception)) {
                    Utils.closeSoftKeyboard(v);
                    register();
                } else {
                    closeLoader();
                    showToast(exception.getErrorMsg());
                }
            }
        });
    }

    private void register() {
        final String newPwd = SHA256Util.shaEncrypt(SHA256Util.shaEncrypt(pwd + "t1@s#df!") + phone);

        NetworkAPIFactoryImpl.getUserAPI().register(phone, newPwd, vCode,
                new OnAPIListener<RegisterReturnEntity>() {
                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                        closeLoader();
                        ToastUtils.show(context, "用户名或验证码错误");
                    }

                    @Override
                    public void onSuccess(RegisterReturnEntity registerReturnEntity) {
                        registerEntity = registerReturnEntity;
                        LogUtil.d("注册请求网络成功" + registerEntity.toString());
                        closeLoader();
                        if (registerEntity.result == 0) {
                            ToastUtils.show(context, "用户已经注册,请直接登录");
                            next(LoginActivity.class);
                            finish();
                        } else if (registerEntity.result == 1) {
                            ToastUtils.show(context, "注册成功");
                            loginGetUserInfo(newPwd);  //登录请求数据
                            finish();
                        }

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                ActivityUtil.nextResetDealPwd(context);
//                            }
//                        }, 100);
                    }
                });
    }

    /**
     * 登录获取用户信息
     *
     * @param newPwd 加密后的pwd
     */
    private void loginGetUserInfo(String newPwd) {
        NetworkAPIFactoryImpl.getUserAPI().login(phone, newPwd, null,
                new OnAPIListener<LoginReturnEntity>() {
                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                    }

                    @Override
                    public void onSuccess(LoginReturnEntity loginReturnEntity) {
                        UserEntity en = new UserEntity();
                        en.setBalance(loginReturnEntity.getUserinfo().getBalance());
                        en.setId(loginReturnEntity.getUserinfo().getId());
                        en.setToken(loginReturnEntity.getToken());
                        en.setUserType(loginReturnEntity.getUserinfo().getType());
                        en.setMobile(loginReturnEntity.getUserinfo().getPhone());
                        UserManager.getInstance().saveUserEntity(en);
                        UserManager.getInstance().setLogin(true);
                        MyApplication.getApplication().onUserUpdate(true);
                        finish();
                    }
                });
    }
}

