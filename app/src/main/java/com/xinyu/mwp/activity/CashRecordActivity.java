package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.CashRecordAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.CashRecordEntity;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.view.SpaceView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Benjamin on 17/1/13.
 */

public class CashRecordActivity extends BaseRefreshAbsListControllerActivity<CashRecordEntity> {
    @ViewInject(R.id.contentView)
    private ListView contentView;
    private CashRecordAdapter adapter;

    @Override
    protected IListAdapter<CashRecordEntity> createAdapter() {
        return adapter = new CashRecordAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_listview;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("提现记录");
        contentView.addHeaderView(new SpaceView(context));
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

        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                next(CashDetailActivity.class);
            }
        });
    }

    private void doRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<CashRecordEntity> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    CashRecordEntity entity = new CashRecordEntity();
                    entity.setDate("16-16-" + i);
                    entity.setTime("16:16:" + i);
                    entity.setStatus(String.valueOf(new Random().nextInt(2)));
                    entity.setType(String.valueOf(new Random().nextInt(2)));
                    entity.setMoney("2000.00");
                    list.add(entity);
                }

                getRefreshController().refreshComplete(list);
            }
        }, 2000);
    }
}
