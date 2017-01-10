package com.xinyu.mwp.activity.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.adapter.BaseListDataRecyclerViewAdapter;
import com.xinyu.mwp.adapter.IListAdapter;
import com.xinyu.mwp.emptyview.RecyclerEmptyAdapter;

/**
 * Created by yaowang on 16/3/30.
 */
public class RefreshRecyclerController<TModel> extends RefreshListController<RecyclerView, TModel> {
    protected RecyclerView.OnScrollListener onScrollListener;

    public RefreshRecyclerController(Context context) {
        super(context);
    }

    public RefreshRecyclerController(Context context, IListAdapter<TModel> listAdapter) {
        super(context, listAdapter);
    }

    @Override
    public BaseListDataRecyclerViewAdapter<TModel> getListAdapter() {
        return (BaseListDataRecyclerViewAdapter<TModel>) listAdapter;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        getContentView().setAdapter(getListAdapter());
        super.initData();
    }


    /**
     * 设置加载更多模式
     *
     * @param enableLoadMore
     */
    @Override
    protected void setLoadMoreEnable(boolean enableLoadMore) {
        super.setLoadMoreEnable(enableLoadMore);
        if (enableLoadMore) {
            getListAdapter().setLoadMoreView(moreViewController.getMoreView());
        } else {
            getListAdapter().setLoadMoreView(null);
        }
    }

    @Override
    public void initListener() {
        super.initListener();

//        final PauseOnScrollListener pauseOnScrollListener =  new PauseOnScrollListener(ImageLoader.getInstance(), true, true);
        getContentView().setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (onScrollListener != null)
                    onScrollListener.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        ImageLoader.getInstance().resume();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        if (true) {
                            ImageLoader.getInstance().pause();
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        if (true) {
                            ImageLoader.getInstance().pause();
                        }
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollListener != null)
                    onScrollListener.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void switchAdapter() {
        if ( hasEmptyView &&
                ( listAdapter.getList() == null || listAdapter.getList().size() == 0)  ) {
            RecyclerEmptyAdapter emptyAdapter = new RecyclerEmptyAdapter(context);
            emptyAdapter.setOnItemChildViewClickListener(onEmptyViewClickListener);
            emptyAdapter.setList(getEmptyViewEntityList());
            getContentView().setAdapter(emptyAdapter);
        }
        else if( getContentView().getAdapter() != listAdapter) {
            getContentView().setAdapter((RecyclerView.Adapter) getListAdapter());
        }
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
}
