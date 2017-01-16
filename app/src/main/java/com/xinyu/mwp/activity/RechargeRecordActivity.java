package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.RechargeRecordAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.RechargeRecordEntity;
import com.xinyu.mwp.entity.RechargeRecordItemEntity;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.view.RechargeRecordHeader;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Benjamin on 17/1/16.
 */

public class RechargeRecordActivity extends BaseRefreshAbsListControllerActivity<RechargeRecordItemEntity> {
    @ViewInject(R.id.header)
    private RechargeRecordHeader header;
    private RechargeRecordAdapter adapter;
    private List<RechargeRecordEntity> entities;
    private int position;

    @Override
    protected IListAdapter<RechargeRecordItemEntity> createAdapter() {
        return adapter = new RechargeRecordAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rechargerecord;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("充值记录");
    }

    @Override
    protected void initListener() {
        super.initListener();
        header.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                position = (int) obj;
                header.updateTitle(entities.get(position).getTime());
                adapter.setList(entities.get(position).getInfo());
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                showToast(adapter.getItem(position).getMoney());
            }
        });
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
    }

    private void doRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                entities = TestDataUtil.getRechargeRecordEntities();
                header.update(entities);
                header.setVisibility(View.VISIBLE);
                getRefreshController().refreshComplete(entities.get(0).getInfo());
            }
        }, 2000);
    }

}
