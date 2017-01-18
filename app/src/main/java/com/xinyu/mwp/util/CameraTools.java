package com.xinyu.mwp.util;

import android.hardware.Camera;

public class CameraTools {
    /**
     * 判断照相机是否可用
     *
     * @param @return 参数
     * @return boolean    返回类型
     * @throws
     * @Title: isCameraCanUse
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            // TODO camera驱动挂掉,处理??
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse && mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

        return canUse;
    }
}
