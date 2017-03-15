package com.xinyu.mwp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.DealDetailActivity;
import com.xinyu.mwp.adapter.DealAllGoodsAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.DealAllGoodsEntity;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.util.ToastUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by Benjamin on 17/1/12.
 */

public class BaseDealAllGoodsFragment extends BaseRefreshAbsListControllerFragment<HistoryPositionListReturnEntity> {
    @ViewInject(R.id.contentView)
    protected ListView contentView;
    //    protected DealAllGoodsHeader header;
    protected DealAllGoodsEntity entity;
    protected DealAllGoodsAdapter adapter;
    private List<List<HistoryPositionListReturnEntity>> products;
    private List<HistoryPositionListReturnEntity> historyPositionList;  //总集合


    private List<String> mSymbolList;
    private static int start = 1;
    private int count = 10;

    public BaseDealAllGoodsFragment() {
    }

    @SuppressLint("ValidFragment")
    public BaseDealAllGoodsFragment(List<String> mSymbolList) {
        this.mSymbolList = mSymbolList;
    }

    @Override
    protected IListAdapter<HistoryPositionListReturnEntity> createAdapter() {
        return adapter = new DealAllGoodsAdapter(context);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.ly_listview;
    }

    @Override
    protected void initView() {
        super.initView();
//        header = new DealAllGoodsHeader(context);
//        contentView.addHeaderView(header);
    }


    //请求历史数据
    private void requestNetData(int num, int count) {
        String symbol = mSymbolList.get(index);
        NetworkAPIFactoryImpl.getDealAPI().historyDealList(num, count, symbol, new OnAPIListener<List<HistoryPositionListReturnEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(List<HistoryPositionListReturnEntity> historyPositionListReturnEntities) {
                historyPositionList = historyPositionListReturnEntities;
                refreshAdapter();
            }
        });
    }

    private static int index = 0;

    public void updateFragment(int i) {
        index = i;
        requestNetData(1, 10);
    }

    private void refreshAdapter() {
        if (adapter == null) {
            adapter = new DealAllGoodsAdapter(context);
            adapter.setProductDealList(historyPositionList);
            adapter.notifyDataSetChanged();
        } else {
            adapter.setProductDealList(historyPositionList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                Intent intent = new Intent(context, DealDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("historyData", (HistoryPositionListReturnEntity) obj);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

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
    }

    public void doRefresh(int num) {
        requestNetData(num, count);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (historyPositionList == null) {
                    ToastUtils.show(context, "数据为空,上拉加载更多");
                }
                getRefreshController().refreshComplete(historyPositionList);
            }
        }, 1000);
    }
}
