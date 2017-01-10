package com.xinyu.mwp.fragment.base;

import android.widget.AbsListView;

import com.xinyu.mwp.activity.base.RefreshAbsListController;
import com.xinyu.mwp.activity.base.RefreshListController;

/**
 * 下拉与加载更多AbsListView Fragment
 * Created by yaowang on 16/3/31.
 */
public abstract class BaseRefreshAbsListControllerFragment<TModel> extends BaseRefreshListFragment<AbsListView, TModel> {

    @Override
    protected RefreshListController<AbsListView, TModel> createRefreshController() {
        return new RefreshAbsListController<TModel>(context, createAdapter());
    }

    @Override
    public RefreshAbsListController<TModel> getRefreshController() {
        return (RefreshAbsListController<TModel>) super.getRefreshController();
    }
}
