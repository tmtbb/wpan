package com.xinyu.mwp.util;

import android.view.View;

/**
 * Created by Benjamin on 16/5/24.
 */
public class ViewUtils {

    public static int getViewLocationScreenX(View view) {
        return getViewLocationScreenXY(view, 0);
    }

    public static int getViewLocationScreenY(View view) {
        return getViewLocationScreenXY(view, 1);
    }

    public static int getViewLocationWindowX(View view) {
        return getViewLocationWindowXY(view, 0);
    }

    public static int getViewLocationWindowY(View view) {
        return getViewLocationWindowXY(view, 1);
    }

    private static int getViewLocationScreenXY(View view, int index) {
        int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        return xy[index];
    }

    private static int getViewLocationWindowXY(View view, int index) {
        int[] xy = new int[2];
        view.getLocationInWindow(xy);
        return xy[index];
    }
}
