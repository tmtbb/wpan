package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ListView;

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
    @ViewInject(R.id.contentView)
    private ListView contentView;
    private RechargeRecordHeader header;
    private RechargeRecordAdapter adapter;
    private List<RechargeRecordEntity> entities;

    @Override
    protected IListAdapter<RechargeRecordItemEntity> createAdapter() {
        return adapter = new RechargeRecordAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_listview;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("充值记录");
        header = new RechargeRecordHeader(context);
        contentView.addHeaderView(header);
    }

    @Override
    protected void initListener() {
        super.initListener();
        header.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                int position = (int) obj;
                header.updateTitle(entities.get(position).getTime());
                adapter.setList(entities.get(position).getInfo());
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {

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
                getRefreshController().refreshComplete(entities.get(0).getInfo());
            }
        }, 2000);
    }

}
