package com.xinyu.mwp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * Created by Administrator on 2017/2/27.
 */
public class NumberUtils {
    /**
     * 四舍五入,保留2位小数
     */
    public static String halfAdjust2(double number) {
        return String.format("%.2f", number);
    }
    /**
     * 四舍五入,保留4位小数
     */
    public static String halfAdjust4(double number) {
        return String.format("%.4f", number);
    }

    /**
     * 四舍五入,保留5位小数
     */
    public static String halfAdjust5(double number) {
        return String.format("%.5f", number);
    }


}
