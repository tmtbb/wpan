package com.xinyu.mwp.activity;


import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.CashRecordAdapter;
import com.xinyu.mwp.adapter.HistoryPositionAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.HistoryPositionEntity;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.view.SpaceView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * 仓位历史记录
 */

public class PositionHistoryActivity extends BaseRefreshAbsListControllerActivity<HistoryPositionEntity> {

    @ViewInject(R.id.contentView)
    private ListView contentView;
    private HistoryPositionAdapter adapter;

    @Override
    protected IListAdapter<HistoryPositionEntity> createAdapter() {
        return adapter = new HistoryPositionAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_listview;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("仓位历史记录");
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

    }

    private void doRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<HistoryPositionEntity> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    HistoryPositionEntity entity = new HistoryPositionEntity();
                    entity.setName("白银" + 3680 + i + "(元/千克)");
                    entity.setTime("2016.12.25 12:20:" + i);
                    entity.setPrice(i + "元/100克");
                    entity.setStop("止损无");
                    entity.setLimit("止盈" + i + "%");
                    list.add(entity);
                }

                getRefreshController().refreshComplete(list);
            }
        }, 1000);
    }
}
