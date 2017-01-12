package com.xinyu.mwp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xinyu.mwp.activity.LoginActivity;
import com.xinyu.mwp.activity.RegisterActivity;
import com.xinyu.mwp.activity.ResetDealPwdActivity;
import com.xinyu.mwp.activity.ResetUserPwdActivity;

/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-06-24 08:44
 * @describe : Activity
 * @for your attention : none
 * @revise : none
 */
public class ActivityUtil {

    public static void next(Context context, Intent intent) {
        if (context != null && intent != null)
            context.startActivity(intent);
    }

    public static void next(Context context, Class<? extends Activity> actClas) {
        Intent intent = new Intent(context, actClas);
        next(context, intent);
    }

    public static void nextRegister(Context context) {
        next(context, RegisterActivity.class);
    }

    public static void nextLogin(Context context) {
        next(context, LoginActivity.class);
    }

    public static void nextResetUserPwd(Context context) {
        next(context, ResetUserPwdActivity.class);
    }


    public static void nextResetDealPwd(Context context) {
        next(context, ResetDealPwdActivity.class);
    }

}
