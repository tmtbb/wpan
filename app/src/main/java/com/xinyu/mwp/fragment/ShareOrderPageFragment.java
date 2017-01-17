package com.xinyu.mwp.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.BindBankCardActivity;
import com.xinyu.mwp.adapter.ShareOrderPageAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.ShareOrderPageEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/11 0011
 * @describe : com.xinyu.mwp.fragment
 */
public class ShareOrderPageFragment extends BaseRefreshAbsListControllerFragment<ShareOrderPageEntity> {

    private ShareOrderPageAdapter adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.ly_listview;
    }

    @Override
    protected IListAdapter<ShareOrderPageEntity> createAdapter() {
        return adapter = new ShareOrderPageAdapter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        View headView = LayoutInflater.from(context).inflate(R.layout.ly_tab_share_order_head, null);
        ListView listView = (ListView) getRefreshController().getContentView();
        listView.addHeaderView(headView);
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
                        List<ShareOrderPageEntity> list = new ArrayList<>();
                        for (int i = 0;i < 30; i++){
                            ShareOrderPageEntity entity = new ShareOrderPageEntity();
                            entity.setName("若忆流年 number" + (i + 1));
                            entity.setContent("+1686");
                            entity.setHeadUrl(ImageUtil.getRandomUrl());
                            list.add(entity);
                        }
                        getRefreshController().refreshComplete(list);
                    }
                }, 500);
            }
        });
        adapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                next(BindBankCardActivity.class);
            }
        });
    }
}
