package com.xinyu.mwp.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.xinyu.mwp.application.MyApplication;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/10/27 0027
 * @describe : com.yaowang.magicbean.util
 */
public class PermissionManagerUtil {

    private OnPermissionListener onPermissionListener;

    public PermissionManagerUtil(OnPermissionListener onPermissionListener) {
        this.onPermissionListener = onPermissionListener;
    }

    /**
     * 检查权限
     *
     * @param activity
     * @param permission
     */
    public void checkPermission(Activity activity, String permission) {
        //1. 检查我们是否有权限
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ToastUtils.show(MyApplication.getApplication(), "请开启相关权限");
            }
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 100);
        } else {
            if (onPermissionListener != null) {
                onPermissionListener.granted(permission);
            }
        }
    }

    /**
     * 权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100://request code 不超过255
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (onPermissionListener != null) {
                        String permission = permissions.length > 0 ? permissions[0] : "";
                        onPermissionListener.granted(permission);
                    }
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (onPermissionListener != null) {
                        String permission = permissions.length > 0 ? permissions[0] : "";
                        onPermissionListener.denied(permission);
                    }
                }
        }

    }

    public interface OnPermissionListener {
        void granted(String permission);

        void denied(String permission);
    }

}
