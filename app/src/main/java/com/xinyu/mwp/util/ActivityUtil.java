package com.xinyu.mwp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.xinyu.mwp.activity.ChooseUploadImgsActivity;
import com.xinyu.mwp.activity.LoginActivity;
import com.xinyu.mwp.activity.RegisterActivity;
import com.xinyu.mwp.activity.ResetDealPwdActivity;
import com.xinyu.mwp.activity.ResetUserPwdActivity;
import com.xinyu.mwp.constant.ActionConstant;

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

//    public static void nextLoginSuccess(Context context) {
//        next(context, LoginSuccessActivity.class);
//    }

    public static void nextImageCropForResult(Activity activity, String imagePath) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.parse("file://" + imagePath), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, ActionConstant.Code.IMAGE_CROP);
    }

    public static void nextUploadImgsForResult(Activity activity) {
        Intent intent = new Intent(activity, ChooseUploadImgsActivity.class);
        activity.startActivityForResult(intent, 99);
    }
}
