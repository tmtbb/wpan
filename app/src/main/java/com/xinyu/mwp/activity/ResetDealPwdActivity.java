package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.exception.CheckException;
import com.xinyu.mwp.helper.CheckHelper;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.SHA256Util;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.view.WPEditText;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-01-11 11:32
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ResetDealPwdActivity extends BaseControllerActivity {

    @ViewInject(R.id.pwdEditText1)
    private WPEditText pwdEditText1;
    @ViewInject(R.id.pwdEditText2)
    private WPEditText pwdEditText2;
    @ViewInject(R.id.loginButton)
    private Button loginButton;
    private CheckHelper checkHelper = new CheckHelper();

    @Override
    protected int getContentView() {
        return R.layout.activity_reset_deal_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("设定交易密码");
        rightText.setText("跳过");
        rightText.setBackgroundDrawable(null);
        rightText.setVisibility(View.VISIBLE);
        checkHelper.checkButtonState(loginButton, pwdEditText1, pwdEditText2);
    }

    @Override
    protected void initListener() {
        super.initListener();
        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("跳过");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckException exception = new CheckException();
                if (checkHelper.checkPassword(pwdEditText1.getEditTextString(), exception)
                        && checkHelper.checkPassword2(pwdEditText1.getEditTextString(), pwdEditText2.getEditTextString(), exception)) {
                    // String pwd = SHA256Util.sha256(pwdEditText2.getEditTextString());
                    String pwd = SHA256Util.shaEncrypt(pwdEditText2.getEditTextString() + "t1@s#df!");
                    String newPwd = SHA256Util.shaEncrypt(pwd + UserManager.getInstance().getUserEntity().getMobile());
                    Utils.closeSoftKeyboard(v);
                    resetDealPwd(newPwd);
                    showLoader("提交中...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            closeLoader();
                            showToast("哈哈哈");
                        }
                    }, 2000);
                } else {
                    showToast(exception.getErrorMsg());
                }

            }
        });
    }

    private void resetDealPwd(String pwd) {
        String memberId = UserManager.getInstance().getUserEntity().getMobile();
        int type = 1;//0：登录密码 1：交易密码，提现密码
        NetworkAPIFactoryImpl.getUserAPI().resetDealPwd(memberId, pwd, null, type, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(Object object) {
                LogUtil.d("请求数据成功,交易密码,返回的数据为:" + object.toString());
            }
        });
    }
}
