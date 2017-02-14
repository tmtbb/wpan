package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.MyPushOrderAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.MyPushOrderEntity;
import com.xinyu.mwp.entity.MyPushOrderItemEntity;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.TestDataUtil;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyPushOrderActivity extends BaseRefreshAbsListControllerActivity<MyPushOrderItemEntity> {
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

    private MyPushOrderEntity entity;
    private MyPushOrderAdapter adapter;

    @Override
    protected IListAdapter<MyPushOrderItemEntity> createAdapter() {
        return adapter = new MyPushOrderAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mypushorder;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的推单");
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
        entity = TestDataUtil.getMyPushOrderEntity();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                day.setText("今日" + entity.getDayCount());
                week.setText("本周" + entity.getWeekCount());
                month.setText("本月" + entity.getMonthCount());
                pushOrderCount.setText("推单总数：" + 9999);
                getRefreshController().refreshComplete(entity.getPushOrders());
                titleLayout.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }
}
