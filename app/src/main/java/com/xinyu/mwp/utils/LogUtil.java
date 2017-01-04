
package com.xinyu.mwp.utils;

import android.util.Log;

import com.xinyu.mwp.configs.Constant;

/**
 * 日志工具类，支持日志的显示、日志文件的保存以及日志文件的上传
 */
public class LogUtil {

    /**
     * Verbose Log, 输出大于或等于Verbose日志级别的信息
     */
    public static void v(String msg) {
        if (Constant.DEBUG) {
            Log.v(Constant.TAG, StringUtil.nullToString(msg));
        }
    }

    /**
     * Debug Log, 输出大于或等于Debug日志级别的信息
     */
    public static void d(String msg) {
        if (Constant.DEBUG) {
            Log.d(Constant.TAG, StringUtil.nullToString(msg));
        }
    }

    /**
     * Debug Log, 输出大于或等于Debug日志级别的信息
     */
    public static void d(String tag, String msg) {
        if (Constant.DEBUG) {
            Log.d(tag, StringUtil.nullToString(msg));
        }
    }

    /**
     * Info Log,输出大于或等于Info日志级别的信息
     */
    public static void i(String msg) {
        if (Constant.DEBUG) {
            Log.i(Constant.TAG, StringUtil.nullToString(msg));
        }
    }

    /**
     * Warn Log,输出大于或等于Warn日志级别的信息
     */
    public static void w(String msg) {
        if (Constant.DEBUG) {
            Log.w(Constant.TAG, StringUtil.nullToString(msg));
        }
    }

    /**
     * Error Log, 仅输出Error日志级别的信息.
     */
    public static void e(String msg) {
        if (Constant.DEBUG) {
            Log.e(Constant.TAG, StringUtil.nullToString(msg));
        }
    }
}
