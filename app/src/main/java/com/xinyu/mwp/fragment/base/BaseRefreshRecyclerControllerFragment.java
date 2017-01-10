package com.xinyu.mwp.fragment.base;

import android.support.v7.widget.RecyclerView;

import com.xinyu.mwp.activity.base.RefreshListController;
import com.xinyu.mwp.activity.base.RefreshRecyclerController;

/**
 * 下拉与加载更多RecyclerView Fragment
 * Created by yaowang on 16/3/31.
 */
public abstract class BaseRefreshRecyclerControllerFragment<TModel> extends BaseRefreshListFragment<RecyclerView, TModel> {
    @Override
    protected RefreshListController<RecyclerView, TModel> createRefreshController() {
        return new RefreshRecyclerController<TModel>(context, createAdapter());
    }

    @Override
    public RefreshRecyclerController<TModel> getRefreshController() {
        return (RefreshRecyclerController<TModel>) super.getRefreshController();
    }
}
