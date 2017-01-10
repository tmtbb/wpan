package com.xinyu.mwp.activity.base;


import android.support.v7.widget.RecyclerView;

/**
 * 下拉与加载更多Recycler Activity
 * Created by yaowang on 16/3/31.
 */
public abstract class BaseRefreshRecyclerControllerActivity<TModel> extends BaseRefreshListActivity<RecyclerView, TModel> {
    @Override
    protected RefreshListController<RecyclerView, TModel> createRefreshController() {
        return new RefreshRecyclerController<TModel>(context, createAdapter());
    }

    @Override
    public RefreshRecyclerController<TModel> getRefreshController() {
        return (RefreshRecyclerController<TModel>) super.getRefreshController();
    }
}
