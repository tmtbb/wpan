package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.exception.CheckException;
import com.xinyu.mwp.helper.CheckHelper;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.view.WPEditText;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-01-12 09:32
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class LoginSuccessActivity extends BaseControllerActivity {

    @ViewInject(R.id.phoneEditText)
    private WPEditText phoneEditText;
    @ViewInject(R.id.msgEditText)
    private WPEditText msgEditText;
    @ViewInject(R.id.soundEditText)
    private WPEditText soundEditText;
    @ViewInject(R.id.completeButton)
    private Button completeButton;

    private CheckHelper checkHelper = new CheckHelper();

    @Override
    protected int getContentView() {
        return R.layout.activity_login_success;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("登录成功");
        phoneEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(completeButton, phoneEditText, msgEditText, soundEditText);
        checkHelper.checkVerificationCode(msgEditText.getRightText(), phoneEditText);
        checkHelper.checkVerificationCode(soundEditText.getRightText(), phoneEditText);
    }

    @Override
    protected void initListener() {
        super.initListener();
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckException exception = new CheckException();
                if (checkHelper.checkMobile(phoneEditText.getEditTextString(), exception)) {
                    Utils.closeSoftKeyboard(v);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showToast("哈哈哈");
                        }
                    }, 100);
                } else {
                    showToast(exception.getErrorMsg());
                }
            }
        });
    }


}
