package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Don on 2016/12/28.
 * Describe ${TODO}
 * Modified ${TODO}
 */
public class AActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActinbar(mToolBar, null);
    }

    @Override
    public void initActinbar(Toolbar toolbar, DrawerLayout drawer) {
        super.initActinbar(toolbar, drawer);
        mToolbarTitle.setText("我是新界面");
        mToolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);
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
}
