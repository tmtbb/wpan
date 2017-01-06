package com.xinyu.mwp.interfaces;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by Don on 2016/11/11 17:54.
 * Describe：${Activity和Fragment的公共逻辑接口}
 * Modified：${TODO}
 *
 * @use Activity或Fragment implements Presenter
 */

public interface IBaseView<T> {

    static final String INTENT_TITLE = "INTENT_TITLE";
    static final String INTENT_ID = "INTENT_ID";
    static final String RESULT_DATA = "RESULT_DATA";

    /**
     * actionBar
     */
    void initToolbar(Toolbar toolbar, TextView titleView, String title);

    /**
     * UI显示方法(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initView();

    /**
     * Data数据方法(存在数据获取或处理代码，但不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initData(T data);

    /**
     * Listener事件监听方法(只要存在事件监听代码就是)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initListener();

    /**
     * 结束请求
     */
    void endHttpRequest();

    /**
     * 是否存活(已启动且未被销毁)
     */
    boolean isAlive();

    /**
     * 是否在运行
     */
    boolean isRunning();
}
