package com.xinyu.mwp.fragment.base;


import com.xinyu.mwp.activity.base.RefreshController;
import com.xinyu.mwp.listener.OnRefreshListener;

/**
 * 下拉与加载更多列表Fragment
 * Created by yaowang on 16/3/31.
 */
public abstract  class BaseRefreshFragment extends BaseControllerFragment {

    private static final String RefreshController = "RefreshController";

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        registerController(RefreshController,createRefreshController(),true);
    }

    protected RefreshController createRefreshController() {
        return new RefreshController(context);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        getRefreshController().setOnRefreshListener(onRefreshListener);
    }

    public RefreshController getRefreshController() {
       return (RefreshController)getController(RefreshController);
    }
}
