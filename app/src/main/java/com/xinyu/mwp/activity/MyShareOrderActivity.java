package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.MyShareOrderAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.MyShareOrderEntity;
import com.xinyu.mwp.entity.MyShareOrderItemEntity;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.TestDataUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyShareOrderActivity extends BaseRefreshAbsListControllerActivity<MyShareOrderItemEntity> {
    @ViewInject(R.id.titleLayout)
    private View titleLayout;
    @ViewInject(R.id.pushOrderCount)
    private TextView pushOrderCount;
    @ViewInject(R.id.day)
    private TextView day;
    @ViewInject(R.id.week)
    private TextView week;
    @ViewInject(R.id.month)
    private TextView month;

    private MyShareOrderEntity entity;
    private MyShareOrderAdapter adapter;


    @Override
    protected IListAdapter<MyShareOrderItemEntity> createAdapter() {
        return adapter = new MyShareOrderAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_myshareorder;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的晒单");
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
    }

    public void doRefresh() {
        entity = TestDataUtil.getMyShareOrderEntity();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                day.setText("今日" + entity.getDayCount());
                week.setText("本周" + entity.getWeekCount());
                month.setText("本月" + entity.getMonthCount());
                pushOrderCount.setText("推单总数：" + 9999);
                getRefreshController().refreshComplete(entity.getShareOrders());
                titleLayout.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }
}
