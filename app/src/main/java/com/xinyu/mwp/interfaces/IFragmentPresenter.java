package com.xinyu.mwp.interfaces;

import android.app.Activity;

/**
 * Created by Don on 2016/11/11 17:59.
 * Describe：${Fragment的逻辑接口}
 * Modified：${TODO}
 */

public interface IFragmentPresenter extends IBaseView {
    static final int RESULT_OK = Activity.RESULT_OK;
    static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
}
