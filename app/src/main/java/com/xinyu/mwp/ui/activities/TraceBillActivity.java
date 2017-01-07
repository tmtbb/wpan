package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.bean.TraceBillBean;
import com.xinyu.mwp.ui.adapter.TraceBillAdapter;
import com.xinyu.mwp.utils.SpaceItemDecoration;
import com.xinyu.mwp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
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
    private TraceBillAdapter mTraceBillAdapter;

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
        names = Arrays.asList(getResources().getStringArray(R.array.trace_bill));
        overTimes = Arrays.asList(getResources().getStringArray(R.array.trace_bill_over_time));
        createTime = Arrays.asList(getResources().getStringArray(R.array.trace_bill_create_time));
        int size = names.size();
        SpannableString ss = new SpannableString("白银空");
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.trace_bill_product)), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE/*前后都不包含*/);
        for (int i = 0; i < size; i++) {
            TraceBillBean billBean = new TraceBillBean();
            billBean.setImgRes(i % 2 == 0 ? R.mipmap.ic_person_left : R.mipmap.ic_person_right);
            billBean.setPersonName(names.get(i));
            billBean.setProduct(ss);
            billBean.setProductOverTime(overTimes.get(i));
            billBean.setProductCreateTime(createTime.get(i));
            billBean.setProductPrice(4980.0f + 20 * i);
            billBean.setNumPersonAttention(20 + i);
            mDatas.add(billBean);
        }
        mTraceBill.setLayoutManager(new LinearLayoutManager(context));
        mTraceBill.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        mTraceBill.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mTraceBillAdapter = new TraceBillAdapter(context, mDatas, R.layout.item_trace_bill);
        mTraceBill.setAdapter(mTraceBillAdapter);
    }

    @Override
    public void initData(Object data) {
    }

    @Override
    public void initListener() {
        mTraceBillAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // TODO: 2017/1/6
                ToastUtil.showToast("跟单详情界面待定。。。", context);
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
            }
        });
    }
}
