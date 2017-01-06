package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.bean.TraceBillBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Don on 2017/1/6.
 * Describe ${TODO}
 * Modified ${TODO}
 */
public class TraceBillActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.rcv_trace_bill)
    RecyclerView mTraceBill;

    private List<TraceBillBean> mDatas;
    private List<String> names;
    private List<String> overTimes;
    private List<String> createTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_bill);
        initToolbar(mToolBar, mToolbarTitle, getString(R.string.trace_bill));
        mToolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        mDatas = new ArrayList<>();

        TraceBillBean traceBillBean = new TraceBillBean();
        //traceBillBean.setImgRes();

    }

    @Override
    public void initData(Object data) {
    }

    @Override
    public void initListener() {

    }
}
