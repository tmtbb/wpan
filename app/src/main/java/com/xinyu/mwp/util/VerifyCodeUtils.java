package com.xinyu.mwp.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.entity.VerifyCodeReturnEntry;
import com.xinyu.mwp.exception.CheckException;
import com.xinyu.mwp.helper.CheckHelper;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.view.WPEditText;

/**
 * Created by Administrator on 2017/2/22.
 */
public class VerifyCodeUtils {

    private static String vToken;
    private static String timestamp;

    public static void getCode(WPEditText msgEditText, int verifyType,Context context, View view, WPEditText phoneEditText) {

        String text = (String) msgEditText.getRightText().getTag();
        if (StringUtil.isEmpty(text)) {
            ToastUtils.show(context, "请输入手机号码");
        } else {
            if (Utils.isMobile(text))
                new CountUtil((TextView) msgEditText.getRightText()).start();
            else
                ToastUtils.show(context, "请输入正确的手机号码");
        }

        LogUtil.d("请求网络获取短信验证码------------------------------");
        CheckException exception = new CheckException();
        String phoneEdit = phoneEditText.getEditTextString();
        if (new CheckHelper().checkMobile(phoneEdit, exception)) {
            Utils.closeSoftKeyboard(view);
            obtainAuthCode(phoneEdit,verifyType);//获取验证码
        } else {
            ToastUtils.show(context, exception.getErrorMsg());
        }
    }

    private static void obtainAuthCode(String phoneEdit,int verifyType) {
        LogUtil.d("获取短信验证码的手机号码:" + phoneEdit);
        NetworkAPIFactoryImpl.getUserAPI().verifyCode(phoneEdit, verifyType,new OnAPIListener<VerifyCodeReturnEntry>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("请求网络错误------------------");
            }

            @Override
            public void onSuccess(VerifyCodeReturnEntry verifyCodeReturnEntry) {
                VerifyCodeReturnEntry vn =  verifyCodeReturnEntry;

                LogUtil.d("成功获取到了短信验证码,时间戳是:" + verifyCodeReturnEntry.getTimestamp()+"en:"+ vn.toString());
                //验证验证码的正确性
                vToken = verifyCodeReturnEntry.getvToken();
                //时间戳,有效性验证
                timestamp = verifyCodeReturnEntry.getTimestamp();
            }
        });
    }

    public static String getVToken() {
        if (vToken != null) {
            return vToken;
        }
        return null;
    }

    public static String getTimestamp() {
        if (timestamp != null) {
            return timestamp;
        }
        return null;
    }

}
