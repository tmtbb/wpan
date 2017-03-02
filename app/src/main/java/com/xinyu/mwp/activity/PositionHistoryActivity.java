package com.xinyu.mwp.activity;

import android.os.Handler;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.HistoryPositionAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.SpaceView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * 仓位历史记录
 */

public class PositionHistoryActivity extends BaseRefreshAbsListControllerActivity<HistoryPositionListReturnEntity> {

    @ViewInject(R.id.contentView)
    private ListView contentView;
    private HistoryPositionAdapter adapter;
    private List<HistoryPositionListReturnEntity> historyPositionList;

    @Override
    protected IListAdapter<HistoryPositionListReturnEntity> createAdapter() {
        return adapter = new HistoryPositionAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_listview;
    }

    private int start = 0;
    private int count = 10;

    @Override
    protected void initView() {
        super.initView();
        setTitle("仓位历史记录");
        contentView.addHeaderView(new SpaceView(context));
        requestNetData(start, count);
    }

    private void requestNetData(int num, int count) {
        NetworkAPIFactoryImpl.getDealAPI().historyPositionList(num, count, new OnAPIListener<List<HistoryPositionListReturnEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(List<HistoryPositionListReturnEntity> historyPositionListReturnEntities) {
                LogUtil.d("历史记录请求网络成功" + historyPositionListReturnEntities.toString());
                historyPositionList = historyPositionListReturnEntities;
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
                    LogUtil.d("可能下拉,page:" + pageIndex);
                    start = 0;
                    doRefresh(start);
                } else {
                    start = start + 10;
                    LogUtil.d("可能上拉,page:" + pageIndex + ",start是:" + start);
                    doRefresh(start);
                }
            }
        });
    }

    private void doRefresh(int number) {
        requestNetData(number, count);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (historyPositionList == null) {
                    ToastUtils.show(context, "网络链接失败-----数据为空");
                }
                getRefreshController().refreshComplete(historyPositionList);
            }
        }, 500);
    }

}
