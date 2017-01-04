package com.xinyu.mwp.base;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.interfaces.IBaseView;
import com.xinyu.mwp.manager.LocalBroadcastManager;
import com.xinyu.mwp.manager.ThreadManager;
import com.xinyu.mwp.manager.WeakHandler;
import com.xinyu.mwp.service.TimeIntentService;
import com.xinyu.mwp.utils.BroadCastReceiverUtil;
import com.xinyu.mwp.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by Don on 2016/11/12 15:41.
 * Describe：${Activity基类}
 * Modified：${TODO}
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView, FragmentManager.OnBackStackChangedListener {
    private static final String TAG = "BaseActivity";

    protected List<BroadCastReceiverUtil.OnReceiveBroadcast> onReceiveBroadcastList;
    private BroadcastReceiver mBaseReceiver;
    private Set<String> mIntentFilters;
    protected BasePresenter basePresenter;
    /**
     * 该Activity实例，命名为context是因为大部分方法都只需要context，写成context使用更方便
     *
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;
    /**
     * 该Activity的界面，即contentView
     *
     * @warn 不能在子类中创建
     */
    protected View view = null;
    //protected Toolbar mToolbar;
    /**
     * 布局解释器
     *
     * @warn 不能在子类中创建
     */
    protected LayoutInflater inflater = null;
    /**
     * Fragment管理器
     *
     * @warn 不能在子类中创建
     */
    protected FragmentManager fragmentManager = null;
    /**
     * 是否存活
     */
    private boolean isAlive = false;
    /**
     * 是否在运行
     */
    private boolean isRunning = false;
    protected Fragment mCurrentFragment;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    /**
     * 是否在前台运行
     */
    private boolean isActive = true;
    /**
     * 用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
     */
    protected Intent intent = null;
    /**
     * 用于activity，fragment等之前的intent传值
     */
    protected Bundle bundle = null;
    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.right_push_in;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;

    /**
     * 初始化方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        isAlive = true;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        inflater = getLayoutInflater();
        basePresenter = new BasePresenter(this, context);
    }

    /**
     * 设置该Activity界面布局，并设置底部左右滑动手势监听
     *
     * @param
     * @use 在子类中
     * *1.onCreate中super.onCreate后setContentView(activityLayoutId, this);
     * *2.重写onDragBottom方法并实现滑动事件处理
     * *3.在导航栏左右按钮的onClick事件中调用onDragBottom方法
     */
    public void setContentView(int layoutId) {
        super.setContentView(layoutId);
        ButterKnife.bind(this);
        view = inflater.inflate(layoutId, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        onFront();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        onBehind();
    }

    /**
     * 进入前台
     */
    protected void onFront() {
        //app 从后台唤醒，进入前台
        if (!isActive) {
            TimeIntentService.startActionBaz(context);
            isActive = true;
        }
    }

    /*
    进入后台
     */
    protected void onBehind() {
        if (!isAppOnForeground()) {
            //app 进入后台
            //全局变量isActive = false 记录当前已经进入后台
            isActive = false;
            TimeIntentService.startActionFoo(context);
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
    }

    @Override
    protected void onDestroy() {
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" + " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }
        if (mBaseReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mBaseReceiver);
            onReceiveBroadcastList.clear();
            mIntentFilters.clear();
        }
        isAlive = false;
        isRunning = false;
        super.onDestroy();
        inflater = null;
        view = null;
        fragmentManager = null;
        intent = null;
        bundle = null;
        context = null;
    }

    @Override
    public void onBackStackChanged() {
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>显示fragment方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    protected void showFragment(Fragment targetFragment, String tag) {
        FragmentTransaction tr = fragmentManager.beginTransaction();
        if (targetFragment != null && !targetFragment.isAdded()) {
            tr.add(R.id.container, targetFragment, tag);
        }
        if (mCurrentFragment != null && !mCurrentFragment.isHidden()) {
            tr.hide(mCurrentFragment);
            tr.addToBackStack(tag);
        }
        mCurrentFragment = targetFragment;
        tr.show(targetFragment).commitAllowingStateLoss();
    }

    protected void showFragment(Fragment fragment) {
        FragmentTransaction tr = fragmentManager.beginTransaction();
        if (fragment != null) {
            if (!fragment.isAdded()) {
                tr.add(R.id.container, fragment);
            }
            if (mCurrentFragment != null && !mCurrentFragment.isHidden()) {
                tr.hide(mCurrentFragment);
                tr.addToBackStack(null);
            }
            mCurrentFragment = fragment;
            tr.show(fragment);
            tr.commitAllowingStateLoss();
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>隐藏fragment方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    protected void hideFragment(Fragment fragment) {
        FragmentTransaction tr = fragmentManager.beginTransaction();
        if (fragment != null && fragment.isAdded()) {
            tr.hide(fragment);
        }
        tr.commitAllowingStateLoss();
    }

    protected void backFragment(Fragment fragment) {
        fragmentManager.popBackStack();
    }

    public final void registerBroadcastReceiver(String[] filterActions, BroadCastReceiverUtil.OnReceiveBroadcast onReceiveBroadcast) {
        if (filterActions == null || onReceiveBroadcast == null) {
            return;
        }
        initBaseReceiver();
        registerReceiver(filterActions, onReceiveBroadcast);
    }

    private void registerReceiver(String[] filterActions, BroadCastReceiverUtil.OnReceiveBroadcast onReceiveBroadcast) {
        onReceiveBroadcastList.add(onReceiveBroadcast);
        for (String filterStr : filterActions) {
            if (!mIntentFilters.contains(filterStr)) {
                mIntentFilters.add(filterStr);
            }
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mBaseReceiver, getIntentFilter());
    }

    protected void initBaseReceiver() {
        if (mBaseReceiver == null) {
            onReceiveBroadcastList = new ArrayList<BroadCastReceiverUtil.OnReceiveBroadcast>();
            mIntentFilters = new HashSet<String>();
            mBaseReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (onReceiveBroadcastList != null) {
                        for (BroadCastReceiverUtil.OnReceiveBroadcast onreceive : onReceiveBroadcastList) {
                            onreceive.onReceive(BaseActivity.this, intent);
                        }
                    }
                }
            };
        }
    }

    public IntentFilter getIntentFilter() {
        IntentFilter filters = new IntentFilter();
        for (String s : mIntentFilters) {
            if (!TextUtils.isEmpty(s)) {
                filters.addAction(s);
            }
        }
        return filters;
    }

    @Override
    public void initActinbar(Toolbar toolbar, final DrawerLayout drawer) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer == null) {
                    finish();
                } else {
                    if (drawer.isDrawerVisible(Gravity.START)) {
                        drawer.closeDrawer(Gravity.START);
                    } else {
                        drawer.openDrawer(Gravity.START);
                    }
                }
            }
        });
    }

    /**
     * 打开新的Activity
     *
     * @param clazz
     */
    public void toActivity(Class<? extends BaseActivity> clazz) {
        toActivity(clazz, -1);
    }

    public void toActivity(Class<? extends BaseActivity> clazz, boolean showAnimation) {
        toActivity(clazz, -1, showAnimation);
    }

    public void toActivity(Class<? extends BaseActivity> clazz, int requestCode) {
        toActivity(clazz, requestCode, true);
    }

    public void toActivity(Class<? extends BaseActivity> clazz, int requestCode, boolean showAnimation) {
        if (clazz == null) {
            return;
        }
        //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
        if (requestCode < 0) {
            startActivity(new Intent(context, clazz));
        } else {
            startActivityForResult(new Intent(context, clazz), requestCode);
        }
        if (showAnimation) {
            overridePendingTransition(enterAnim, exitAnim);
        } else {
            overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
        }
    }

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            return;
        }
        runOnUiThread(action);
    }

    /**
     * 运行工作线程
     *
     * @param name
     * @param runnable
     * @return
     */
    public final WeakHandler runThread(String name, Runnable runnable) {
        if (isAlive() == false) {
            return null;
        }
        name = StringUtil.getTrimedString(name);
        WeakHandler handler = ThreadManager.getInstance().runThread(name, runnable);
        if (handler == null) {
            return null;
        }
        return handler;
    }

    @Override
    public void endHttpRequest() {
    }

    @Override
    public boolean isAlive() {
        return isAlive && context != null;// & ! isFinishing();导致finish，onDestroy内runUiThread不可用
    }

    @Override
    public boolean isRunning() {
        return isRunning & isAlive();
    }

    protected BaseApplication getApp() {
        return (BaseApplication) getApplication();
    }
}
