package com.xinyu.mwp.activity.base;

import android.widget.AbsListView;

/**
 * 下拉与加载更多AbsListView Activity
 * Created by yaowang on 16/3/31.
 */
public abstract class BaseRefreshAbsListControllerActivity<TModel> extends BaseRefreshListActivity<AbsListView, TModel> {

    @Override
    protected RefreshListController<AbsListView, TModel> createRefreshController() {
        return new RefreshAbsListController<TModel>(this, createAdapter());
    }

    @Override
    public RefreshAbsListController<TModel> getRefreshController() {
        return (RefreshAbsListController<TModel>) super.getRefreshController();
    }
}
