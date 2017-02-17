package com.xinyu.mwp.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.xinyu.mwp.R;
import com.xinyu.mwp.networkapi.NetworkAPIException;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.LoaderLayout;

import org.xutils.x;

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Context context;
    private LoaderLayout loaderLayout;

    protected abstract int getLayoutID();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutID(), container, false);
        onInit();
        initView();
        initStatusBar();  //设置状态栏颜色
        initListener();

        return rootView;
    }

    public void initStatusBar() {
    }

    protected void onInit() {
        x.view().inject(this, rootView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化view
     */
    protected void initView() {

    }

    /**
     * 初始化监听
     */
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 跳转到下一个Activity
     *
     * @param cls Activity class
     */
    protected void next(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }


    protected void onToastError(Throwable ex, String defError) {
        try {
            if (getActivity() != null && !getActivity().isFinishing()) {
                ToastUtils.show(context, NetworkAPIException.formatException(ex, defError), 2000);
            }
            LogUtil.showException((Exception) ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onToastError(Throwable ex) {
        onToastError(ex, null);
        closeLoader();
    }

    public void showLoader() {
        showLoader(null);
    }

    public void showLoader(String msgContent) {
        try {
            if (loaderLayout == null) {
                loaderLayout = new LoaderLayout(context);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                loaderLayout.setLayoutParams(params);
                if (rootView != null) {
                    ((FrameLayout) rootView.getParent()).addView(loaderLayout);
                }
            }
            loaderLayout.setVisibility(View.VISIBLE);
            if (msgContent != null) {
                loaderLayout.setMsgContent(msgContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoader(int resId) {
        showLoader(getString(resId));
    }

    public void closeLoader() {
        if (loaderLayout != null)
            loaderLayout.setVisibility(View.GONE);
    }

    @Override
    protected void finalize() throws Throwable {
        LogUtil.d("finalize() " + this.toString());
        super.finalize();
    }

    public void showToast(@NonNull CharSequence content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}