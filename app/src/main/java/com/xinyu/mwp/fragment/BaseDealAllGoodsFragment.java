package com.xinyu.mwp.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.DealDetailActivity;
import com.xinyu.mwp.adapter.DealAllGoodsAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.DealAllGoodsEntity;
import com.xinyu.mwp.entity.DealAllGoodsItemEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.DealAllGoodsHeader;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 17/1/12.
 */

public class BaseDealAllGoodsFragment extends BaseRefreshAbsListControllerFragment<DealAllGoodsItemEntity> {
    @ViewInject(R.id.contentView)
    protected ListView contentView;
    protected DealAllGoodsHeader header;
    protected DealAllGoodsEntity entity;
    protected DealAllGoodsAdapter adapter;

    @Override
    protected IListAdapter<DealAllGoodsItemEntity> createAdapter() {
        return adapter = new DealAllGoodsAdapter(context);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.ly_listview;
    }

    @Override
    protected void initView() {
        super.initView();
        header = new DealAllGoodsHeader(context);
        contentView.addHeaderView(header);
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
                ToastUtils.show(context, "position = " + position + " | action = " + action);
                next(DealDetailActivity.class);
            }
        });
    }

    public void doRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                entity = new DealAllGoodsEntity();
                entity.setBuyDown("19999.00");
                entity.setBuyUp("19999.00");
                entity.setCreateDepot("19999.00");
                entity.setFlatDepot("19999.00");
                List<DealAllGoodsItemEntity> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    DealAllGoodsItemEntity entity = new DealAllGoodsItemEntity();
                    entity.setTime("Date - " + i);
                    entity.setBuyDown("19999.00");
                    entity.setBuyUp("19999.00");
                    entity.setCreateDepot("19999.00");
                    entity.setFlatDepot("19999.00");
                    list.add(entity);
                }
                entity.setGoodsInfo(list);
                header.update(entity);
                getRefreshController().refreshComplete(entity.getGoodsInfo());
            }
        }, 2000);
    }
}
