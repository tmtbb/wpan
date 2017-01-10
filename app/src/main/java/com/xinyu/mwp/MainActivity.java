package com.xinyu.mwp;

import android.os.Handler;

import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.IListAdapter;
import com.xinyu.mwp.emptyview.EmptyViewEntityUtil;
import com.xinyu.mwp.listener.OnRefreshPageListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseRefreshAbsListControllerActivity<String> {

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected IListAdapter<String> createAdapter() {
        return new DemoAdapter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("测试页面");
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(final int pageIndex) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> datas = new ArrayList<String>();
                        for (int i = 0; i < 20; i++){
                            datas.add("This is the text content " + i);
                        }
                        getRefreshController().refreshComplete(datas);
                    }
                }, 500);
            }
        });
        getRefreshController().setEmptyViewEntityList(EmptyViewEntityUtil.getInstance().getDefaultEmptyList());
    }
}
