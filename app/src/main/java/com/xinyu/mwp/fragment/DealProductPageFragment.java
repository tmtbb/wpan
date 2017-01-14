package com.xinyu.mwp.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.ModifyPositionActivity;
import com.xinyu.mwp.activity.PositionHistoryActivity;
import com.xinyu.mwp.adapter.DealProductPageAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.DealProductPageEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/11 0011
 * @describe : 交易界面 白银/原油/咖啡 详情
 */
public class DealProductPageFragment extends BaseRefreshAbsListControllerFragment<DealProductPageEntity> implements View.OnClickListener {

    private DealProductPageAdapter adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.ly_listview;
    }

    @Override
    protected IListAdapter<DealProductPageEntity> createAdapter() {
        return adapter = new DealProductPageAdapter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        View headView = LayoutInflater.from(context).inflate(R.layout.ly_tab_deal_order_head, null);


        ListView listView = (ListView) getRefreshController().getContentView();
        listView.addHeaderView(headView);

        LinearLayout dealInfoModify = (LinearLayout) headView.findViewById(R.id.ll_deal_info_modify); //修改持仓参数
        TextView exchangeHistory = (TextView) headView.findViewById(R.id.tv_exchange_history);//仓位历史记录
        dealInfoModify.setOnClickListener(this);
        exchangeHistory.setOnClickListener(this);
    }

    @Override
    protected void initListener() {
        super.initListener();

        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<DealProductPageEntity> list = new ArrayList<>();
                        for (int i = 0; i < 30; i++) {
                            DealProductPageEntity entity = new DealProductPageEntity();
                            entity.setProductName("石油" + (i + 1));
                            entity.setOpenPrice(6666);
                            entity.setGrossProfit(8888);
                            list.add(entity);
                        }
                        getRefreshController().refreshComplete(list);
                    }
                }, 500);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_deal_info_modify:   //修改持仓参数
                next(ModifyPositionActivity.class);
                break;
            case R.id.tv_exchange_history:  //仓位历史记录
                next(PositionHistoryActivity.class);
                break;
        }
    }
}
