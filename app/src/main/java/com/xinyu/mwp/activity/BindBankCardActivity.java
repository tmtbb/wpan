package com.xinyu.mwp.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.BindBankCardAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.BankCardEntity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.ErrorCodeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/16 0016
 * @describe : com.xinyu.mwp.activity
 */
public class BindBankCardActivity extends BaseRefreshAbsListControllerActivity<BankCardEntity> {

    @ViewInject(R.id.refreshFrameLayout)
    private PtrFrameLayout refreshFrameLayout;
    @ViewInject(R.id.contentView)
    private ListView listView;
    private BindBankCardAdapter adapter;
    private List<BankCardEntity> bankCardList = new ArrayList<BankCardEntity>();
    private boolean flag = true;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    protected IListAdapter<BankCardEntity> createAdapter() {
        return adapter = new BindBankCardAdapter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("选择银行卡");
        int paddingV = DisplayUtil.dip2px(10, context);
        int paddingH = DisplayUtil.dip2px(12, context);
        listView.setPadding(paddingH, paddingV, paddingH, paddingV);
        adapter.setPtrFrameLayout(refreshFrameLayout);
        View footerView = LayoutInflater.from(context).inflate(R.layout.ly_bind_bank_card_footer, null);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(AddBankInfoActivity_1.class);
                EventBus.getDefault().removeAllStickyEvents();
            }
        });
        listView.addFooterView(footerView);
        listView.setVisibility(View.GONE);
        getBankCardList();//获取银行卡列表
        if (flag) {
            EventBus.getDefault().register(this);
            flag = false;
        }
    }

    /*
    *接收消息
    */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(EventBusMessage eventBusMessage) {
        if (eventBusMessage.Message == -3) {
            refresh();
        }
    }

    /**
     * 获取银行卡列表
     */
    private void getBankCardList() {
        NetworkAPIFactoryImpl.getDealAPI().bankCardList(new OnAPIListener<List<BankCardEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                ErrorCodeUtil.showEeorMsg(context,ex);
                listView.setVisibility(View.VISIBLE);
                getRefreshController().refreshComplete();
            }

            @Override
            public void onSuccess(List<BankCardEntity> bankCardEntities) {
                bankCardList = bankCardEntities;

                listView.setVisibility(View.VISIBLE);
                if (bankCardList.size() > 0) {
                    for (int i = 0; i < bankCardList.size(); i++) {
                        bankCardList.get(i).setBackGround(i % 2 == 0 ? "#f98d8d" : "#408dc5");
                    }
                }
                getRefreshController().refreshComplete(bankCardList);
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                if (action == 99) {
                    EventBus.getDefault().postSticky(bankCardList.get(position));
                    finish();
                }
            }
        });
    }

    private void refresh() {
        getBankCardList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
