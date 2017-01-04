package com.xinyu.mwp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinyu.mwp.interfaces.IBaseView;
import com.xinyu.mwp.manager.WeakHandler;

import butterknife.ButterKnife;

/**
 * Created by Don on 2016/11/12 16:41.
 * Describe：${fragment基类}
 * Modified：${TODO}
 */

public class BaseFragment extends Fragment implements IBaseView {

    private static final String TAG = "BaseFragment";
    /**
     * 该Fragment在Activity添加的所有Fragment中的位置
     */
    static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";

    static final int RESULT_OK = Activity.RESULT_OK;
    static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
    /**
     * 该Fragment全局视图
     *
     * @must 非abstract子类的onCreateView中return view;
     * @warn 不能在子类中创建
     */
    protected View parentView = null;
    /**
     * 布局解释器
     *
     * @warn 不能在子类中创建
     */
    protected LayoutInflater inflater = null;
    /**
     * 添加这个Fragment视图的布局
     *
     * @warn 不能在子类中创建
     */
    @Nullable
    protected ViewGroup container = null;

    private boolean isAlive = false;
    private boolean isRunning = false;
    protected Context context;
    protected Activity activity;
    protected WeakHandler mHandler;
    protected String packageName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (Context) getActivity();
        activity = getActivity();
        mHandler = new WeakHandler(Looper.myLooper());//使用HandlerThread的looper对象创建Handler
        packageName = BaseApplication.getPackageInfo(context).packageName;
    }

    /**
     * @must 在非abstract子类的onCreateView中super.onCreateView且return view;
     */
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isAlive = true;
        this.inflater = inflater;
        this.container = container;
        return parentView;
    }

    /**
     * 设置界面布局
     *
     * @param layoutResID
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID, container, false));
        ButterKnife.bind(this, parentView);//绑定framgent
    }

    /**
     * 设置界面布局
     *
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(View v) {
        setContentView(v, null);
    }

    /**
     * 设置界面布局
     *
     * @param params
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(View v, ViewGroup.LayoutParams params) {
        parentView = v;
    }

    /**
     * 该Fragment在Activity添加的所有Fragment中的位置，通过ARGUMENT_POSITION设置
     *
     * @must 只使用getPosition方法来获取position，保证position正确
     */
    private int position = -1;

    /**
     * 获取该Fragment在Activity添加的所有Fragment中的位置
     */
    public int getPosition() {
        if (position < 0) {
            argument = getArguments();
            if (argument != null) {
                position = argument.getInt(ARGUMENT_POSITION, position);
            }
        }
        return position;
    }

    /**
     * 可用于 打开activity与fragment，fragment与fragment之间的通讯（传值）等
     */
    protected Bundle argument = null;
    /**
     * 可用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
     */
    protected Intent intent = null;

    /**
     * 通过id查找并获取控件，使用时不需要强转
     *
     * @param id
     * @return
     * @warn 调用前必须调用setContentView
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id) {
        return (V) parentView.findViewById(id);
    }

    /**
     * 通过id查找并获取控件，并setOnClickListener
     *
     * @param id
     * @param l
     * @return
     */
    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        V v = findViewById(id);
        v.setOnClickListener(l);
        return v;
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(final Intent intent) {
        toActivity(intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity(final Intent intent, final int requestCode) {
        toActivity(intent, requestCode, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    Log.w(TAG, "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
            }
        });
    }

    @Override
    public void initActinbar(Toolbar toolbar, DrawerLayout drawerLayout) {

    }

    @Override
    public void initView() {
    }

    @Override
    public void initData(Object data) {
    }

    @Override
    public void initListener() {
    }

    @Override
    public void endHttpRequest() {
    }

    public final boolean isAlive() {
        return isAlive && context != null;// & ! isRemoving();导致finish，onDestroy内runUiThread不可用
    }

    public final boolean isRunning() {
        return isRunning & isAlive();
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    /**
     * 销毁并回收内存
     *
     * @warn 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    public void onDestroy() {
        if (parentView != null) {
            try {
                parentView.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" +
                        " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }
        isAlive = false;
        isRunning = false;
        super.onDestroy();
        parentView = null;
        inflater = null;
        intent = null;
        argument = null;
    }
}
