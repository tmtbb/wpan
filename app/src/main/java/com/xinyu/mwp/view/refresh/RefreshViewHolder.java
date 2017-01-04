package com.xinyu.mwp.view.refresh;

import android.view.View;

public abstract class RefreshViewHolder {
    protected SuperSwipeRefreshLayout swipeRefresh;

    public RefreshViewHolder(SuperSwipeRefreshLayout swipeRefresh) {
        this.swipeRefresh = swipeRefresh;
    }

    /**
     * 下拉刷新
     */
    public abstract void onRefresh();

    public abstract void onPullDistance(int distance);

    public abstract void onPullEnable(boolean enable);

    public abstract View createFooterView();

    /**
     * 上拉加载更多
     */
    public abstract void onLoadMore();

    public abstract void onPushDistance(int distance);

    public abstract void onPushEnable(boolean enable);

    public abstract View createHeaderView();
}
