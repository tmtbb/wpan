package com.xinyu.mwp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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

}
