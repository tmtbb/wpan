package com.xinyu.mwp.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.MyPushOrderAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.annotation.FieldJsonKey;
import com.xinyu.mwp.entity.MyPushOrderEntity;
import com.xinyu.mwp.entity.MyPushOrderItemEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TestDataUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyPushOrderFragment extends BaseRefreshAbsListControllerFragment<MyPushOrderItemEntity> {

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

    private List<MyPushOrderItemEntity> orderInfoList; //好友推单信息 集合
    private List<MyPushOrderItemEntity> orderInfoAllsList = new ArrayList<>(); //所有信息的集合

    @Override
    protected IListAdapter<MyPushOrderItemEntity> createAdapter() {
        return adapter = new MyPushOrderAdapter(context);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_mypushorder;
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
        entity = TestDataUtil.getMyPushOrderEntity();  //本月,本周胜率数据
        orderInfoList = TestDataUtil.getMyPushOrderInfoEntity(); //好友推单 信息数据集合
        orderInfoAllsList.clear();
        orderInfoAllsList.addAll(entity.getPushOrders());
        orderInfoAllsList.addAll(orderInfoList);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                day.setText("今日" + entity.getDayCount());
                week.setText("本周" + entity.getWeekCount());
                month.setText("本月" + entity.getMonthCount());
                pushOrderCount.setText("推单总数：" + 9999);
                getRefreshController().refreshComplete(orderInfoAllsList);
                titleLayout.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }
}
