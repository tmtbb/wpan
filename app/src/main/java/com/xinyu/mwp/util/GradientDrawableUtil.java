package com.xinyu.mwp.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/9/5 0005
 * @describe : GradientDrawableUtil
 */

public class GradientDrawableUtil {


    public static Drawable getGradientDrawable(int strokeWidth, int strokeColor, int solidColor, float radius, int dashGap, int dashWidth) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    /**
     * 获取shape drawable
     *
     * @param strokeWidth
     * @param strokeColor
     * @param solidColor
     * @param radius
     * @return
     */
    public static Drawable getGradientDrawable(int strokeWidth, int strokeColor, int solidColor, float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    /**
     * 获取shape drawable
     *
     * @param solidColor
     * @param radius
     * @return
     */
    public static Drawable getGradientDrawable(int solidColor, float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    /**
     * 获取shape drawable
     *
     * @param strokeWidth
     * @param strokeColor
     * @param solidColor
     * @param radii
     * @return
     */
    public static Drawable getGradientDrawable(int strokeWidth, int strokeColor, int solidColor, float[] radii) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadii(radii);
        return gradientDrawable;
    }

}
