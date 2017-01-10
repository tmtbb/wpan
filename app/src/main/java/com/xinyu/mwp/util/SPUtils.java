package com.xinyu.mwp.util;

/**
 * SPsave
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xinyu.mwp.application.MyApplication;

/**
 * SharedPreferences工具类
 * 
 * @author wh
 * 
 */
public class SPUtils {
    /**
     * 文件名
     */
    private static final String CONFIG = "userinfo";

    /**
     * 私有构造
     */
    private SPUtils() {

    }

    /**
     * 保存布尔值
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public static synchronized void putBoolean(String key, boolean value) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static synchronized void remove(String key) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 保存字符串
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public static synchronized void putString(String key, String value) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();

    }

    /**
     * 保存int数字
     *
     * @param key
     *            key
     * @param value
     *            value
     */
    public static synchronized void putInt(String key, int value) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    /**
     * 保存long数字
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public static synchronized void putLong(String key, long value) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取指定key的int
     *
     * @param key
     *            key
     * @param defValue
     *            defValue
     * @return 返回值
     */
    public static synchronized int getInt(String key, int defValue) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取指定key的boolean
     * 
     * @param key
     *            key
     * @param defValue
     *            defValue
     * @return 返回值
     */
    public static synchronized boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 获取指定key的String
     * @param key
     *            key
     * @param defValue
     *            defValue
     * @return 返回值
     */
    public static synchronized String getString(String key, String defValue) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        return sp.getString(key, defValue);
    }
    /**
     * 获取指定key的long
     * 
     * @param key
     *            key
     * @param defValue
     *            defValue
     * @return 返回值
     */
    public static synchronized long getLong(String key, long defValue) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG,
                Context.MODE_MULTI_PROCESS);
        return sp.getLong(key, defValue);
    }
    
    /**
	 *
	 * @return 返回值
	 */
	public static synchronized boolean clean()
	{
		SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(CONFIG, Context.MODE_MULTI_PROCESS);
		return sp.edit().clear().commit();
	}


}
