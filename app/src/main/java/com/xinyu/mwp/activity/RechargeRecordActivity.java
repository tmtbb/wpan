package com.xinyu.mwp.activity;

import android.os.Handler;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.RechargeRecordAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.RechargeRecordEntity;
import com.xinyu.mwp.entity.RechargeRecordItemEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
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
    private int start = 1;
    private int count = 10;
    private List<RechargeRecordItemEntity> rechargeRecordList;

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
        requestRechargeRecord(start, count);
    }

    private void requestRechargeRecord(int startPos, int count) {
        NetworkAPIFactoryImpl.getDealAPI().rechargeList(startPos, count, new OnAPIListener<List<RechargeRecordItemEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(List<RechargeRecordItemEntity> rechargeRecordItemEntities) {
                rechargeRecordList = rechargeRecordItemEntities;
                getRefreshController().refreshComplete(rechargeRecordList);
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                if (pageIndex == 1) {
                    start = 1;
                    doRefresh(start);
                } else {
                    start = start + 10;
                    doRefresh(start);
                }
            }
        });

//        header.setOnChildViewClickListener(new OnChildViewClickListener() {
//            @Override
//            public void onChildViewClick(View childView, int action, Object obj) {
//                position = (int) obj;
//                header.updateTitle(entities.get(position).getTime());
//                adapter.setList(entities.get(position).getInfo());
//                adapter.notifyDataSetChanged();
//            }
//        });

//        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
//            @Override
//            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
//                showToast(adapter.getItem(position).getMoney());
//            }
//        });
//        setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                doRefresh();
//            }
//        });
    }

    private void doRefresh(int number) {
        requestRechargeRecord(number, count);
    }

}
