package com.xinyu.mwp.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.xinyu.mwp.utils.DataKeeper;
import com.xinyu.mwp.utils.RealmHelper;
import com.xinyu.mwp.utils.SettingUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Don on 2016/11/11 13:37.
 * Describe：${TODO}
 * Modified：${TODO}
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static volatile BaseApplication instance;
    private static volatile boolean isReleased = SettingUtil.getBoolean(instance, "isReleased", false);

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 空参构造方法
     */
    public BaseApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    /**
     * 初始化方法
     *
     * @param application
     * @must 调用init方法且只能调用一次，如果extends BaseApplication会自动调用
     */
    private static void init(BaseApplication application) {
        instance = application;
        DataKeeper.init(instance);
        SettingUtil.init(instance);
        Realm.init(instance);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public static String getAppVersionName() {
        return getPackageInfo(getInstance()).versionName;
    }

    /**
     * 获取应用版本号（给用户看的）
     *
     * @return
     */
    public static int getAppVersion() {
        return getPackageInfo(getInstance()).versionCode;
    }

    /**
     * 获取app的相关信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            PackageManager manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 检查是否连接网络
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 获取设备imei
     *
     * @param context
     * @param imei
     * @return
     */
    public static String getImei(Context context, String imei) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
        }
        return imei;
    }
}
