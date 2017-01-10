package com.xinyu.mwp.listener;

/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-07-14 17:18
 * @describe : OnLoadingListener
 * @for your attention : none
 * @revise : none
 */
public interface OnProgressListener {
    /**
     * 进度
     * @param total 总值
     * @param current 当前值
     */
    void onProgress(long total, long current);

    /**
     * 是否取消
     * @return
     */
    boolean isCancel();
}
