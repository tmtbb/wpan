package com.xinyu.mwp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    //弹出软件盘
    public static void  showKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
        //imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }
    //隐藏软件盘
    public static void  hideKeyboard(Context context, View view){
        InputMethodManager soft = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        soft.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
