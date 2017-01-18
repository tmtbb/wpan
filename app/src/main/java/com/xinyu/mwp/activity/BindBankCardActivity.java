package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.BindBankCardAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.BankCardEntity;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.ImageUtil;

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
        setTitle("绑定银行卡");
        int paddingV = DisplayUtil.dip2px(10, context);
        int paddingH = DisplayUtil.dip2px(12, context);
        listView.setPadding(paddingH, paddingV, paddingH, paddingV);
        adapter.setPtrFrameLayout(refreshFrameLayout);
        View footerView = LayoutInflater.from(context).inflate(R.layout.ly_bind_bank_card_footer, null);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(AddBankInfoActivity.class);
            }
        });
        listView.addFooterView(footerView);
        listView.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setVisibility(View.VISIBLE);
                        List<BankCardEntity> bankCardEntities = new ArrayList<>();
                        for (int i = 0; i < 2; i++){
                            BankCardEntity bankCardEntity = new BankCardEntity();
                            bankCardEntity.setTitle(i == 0 ? "招商银行" : "农业银行");
                            bankCardEntity.setType("储蓄卡");
                            bankCardEntity.setIcon(ImageUtil.getRandomUrl());
                            bankCardEntity.setNumber("****  ****  ****  6364");
                            bankCardEntity.setBackground(i == 0 ? "#f98d8d" : "#408dc5");
                            bankCardEntities.add(bankCardEntity);
                        }
                        getRefreshController().refreshComplete(bankCardEntities);
                    }
                }, 500);
            }
        });
    }
}
