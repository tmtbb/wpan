package com.xinyu.mwp.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.HistoryPositionAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CustomDialog;
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
    private HistoryPositionListReturnEntity item;
    private View clickView;
    private int pos;

    @Override
    protected IListAdapter<HistoryPositionListReturnEntity> createAdapter() {
        return adapter = new HistoryPositionAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_listview;
    }

    private int start = 1;
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
//                LogUtil.d("历史记录请求网络成功" + historyPositionListReturnEntities.toString());
                historyPositionList = historyPositionListReturnEntities;
                getRefreshController().refreshComplete(historyPositionList);
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                clickView = childView;
                pos = position;
                item = adapter.getItem(position);

                if (item.getHandle() == Constant.ACTION_NONE) {  //未操作
                    //弹窗
                    if (item.getBuySell() == 1 && item.isResult()) {   //买入,上涨   双倍返还和货运
                        createDialog(Constant.handleText[1], Constant.handleText[2], Constant.ACTION_DOUBLE, Constant.ACTION_FREIGHT);
                    } else if (item.getBuySell() == 1 && !item.isResult()) { //买入,下跌  只能选择货运
                        createDialog(Constant.handleText[2], null, Constant.ACTION_FREIGHT, 0);
                    }

                    if (UserManager.getInstance().getUserEntity().getUserType() == 0) {   //普通会员
                        if (item.getBuySell() != 1) {  //卖出(买跌)
                            if (!item.isResult()) { //盈利  上涨  亏损 -->亏损 无操作

                            } else {
                                //买跌,上涨  -->
                                createDialog(Constant.handleText[1], null, Constant.ACTION_DOUBLE, 0);
                            }
                        }
                    } else {   //直营会员
                        if (item.getBuySell() != 1 && item.isResult()) {  //卖出,卖出去了-->盈利   货运
                            createDialog(Constant.handleText[2], null, Constant.ACTION_FREIGHT, 0);
                        }
                    }
                }


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

    private void createDialog(String type1, String type2, final int handle1, final int handle2) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context, Constant.TYPE_INSUFFICIENT_BALANCE);
        builder.setTitle(getResources().getString(R.string.congratulations_profit))
                .setMessage(getResources().getString(R.string.choose_profit_type))
                .setPositiveButton(type1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPosition(handle1);  //请求网络,选择盈利方式
                    }
                }).setNegativeButton(type2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestPosition(handle2);  //请求网络,选择盈利方式
            }
        }).create().show();
    }

    private void requestPosition(int handle) {
        NetworkAPIFactoryImpl.getDealAPI().profit(item.getPositionId(), handle, new OnAPIListener<HistoryPositionListReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("请求盈利错误");
                ToastUtils.show(context, "请求盈利失败,当前不可交易");
            }

            @Override
            public void onSuccess(HistoryPositionListReturnEntity profitEntity) {
                LogUtil.d("请求盈利成功:" + profitEntity.toString());
                adapter.notifyPartsData(clickView, pos, profitEntity.getHandle());
            }
        });
    }

    private void doRefresh(int number) {
        requestNetData(number, count);
    }

}
