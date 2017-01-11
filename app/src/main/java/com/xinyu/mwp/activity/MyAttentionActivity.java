package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.MyAttentionAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.UnitEntity;
import com.xinyu.mwp.listener.OnItemChildViewClickListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.view.MyAttentionHeader;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Benjamin on 17/1/11.
 */

public class MyAttentionActivity extends BaseRefreshAbsListControllerActivity<UnitEntity> {
    @ViewInject(R.id.contentView)
    private ListView contentView;
    private MyAttentionAdapter adapter;
    private MyAttentionHeader headView;

    @Override
    protected IListAdapter<UnitEntity> createAdapter() {
        return adapter = new MyAttentionAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_listview;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的关注");
        headView = new MyAttentionHeader(context);
        contentView.addHeaderView(headView);
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
                UnitEntity item = adapter.getItem(position);
                showToast(item.getName());
            }
        });
    }

    private void doRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<UnitEntity> list = TestDataUtil.getUnitEntities();
                headView.update("关注的人数：" + list.size());
                getRefreshController().refreshComplete(list);
            }
        }, 2000);
    }
}
