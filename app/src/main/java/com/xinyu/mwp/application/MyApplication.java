package com.xinyu.mwp.application;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.CheckUpdateInfoEntity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.entity.IpAndPortEntity;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.UserEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.Host;
import com.xinyu.mwp.networkapi.NetworkAPIConfig;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;

import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPIRequestManage;
import com.xinyu.mwp.user.OnUserUpdateListener;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.FileCacheUtil;

import com.xinyu.mwp.util.JSONEntityUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.MD5Util;
import com.xinyu.mwp.util.SPUtils;
import com.xinyu.mwp.util.Utils;


import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import io.fabric.sdk.android.Fabric;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MyApplication extends MultiDexApplication implements OnUserUpdateListener {

    private static MyApplication application;
    public static List<Activity> activityList = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        Fabric.with(this, new Crashlytics());
        super.onCreate();
        application = this;
        initNetworkAPIConfig();
        requestServerIp();

        initImageLoader();
//        initUser();
        checkToken();
        registerToWx();   //注册微信
    }

    public static IWXAPI api;

    private void registerToWx() {
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.registerApp(Constant.APP_ID);
    }

    NetworkAPIConfig networkAPIConfig;

    private void initNetworkAPIConfig() {
        LogUtil.d("初始化网络配置-----");
        networkAPIConfig = new NetworkAPIConfig();
        networkAPIConfig.setContext(getApplicationContext());
        networkAPIConfig.setSocketServerIp(Host.getSocketServerIp());
        networkAPIConfig.setSocketServerPort(Host.getSocketServerPort());
        LogUtil.d("初始化网络配置----ip:" + Host.getSocketServerIp() + "端口:" + Host.getSocketServerPort());
        NetworkAPIFactoryImpl.initConfig(networkAPIConfig);
        x.Ext.init(this);
        x.Ext.setDebug(true);

        initUser();
    }

    /**
     * 初始化图片加载
     */
    private void initImageLoader() {
        File cacheDir = new File(FileCacheUtil.getInstance().getCacheDir(application, "images"));  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCache(new LimitedAgeDiskCache(cacheDir, 24 * 3600))
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置线程的优先级
                .denyCacheImageMultipleSizesInMemory()//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void initUser() {
        UserManager.getInstance().registerUserUpdateListener(this);
        UserManager.getInstance().initUser(this);
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public static MyApplication getApplication() {
        return application;
    }


    public synchronized void register(Activity activity) {
        activityList.add(activity);
    }

    public synchronized void unregister(Activity activity) {
        if (activityList != null && activityList.size() != 0) {
            activityList.remove(activity);

            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    @Override
    public void onUserUpdate(boolean isLogin) {
        String userToken = null;
        long id = 0;
        if (UserManager.getInstance().isLogin()) {
            userToken = UserManager.getInstance().getUserEntity().getToken();
            id = UserManager.getInstance().getUserEntity().getId();
        }
        NetworkAPIFactoryImpl.getConfig().setUserToken(userToken);
        NetworkAPIFactoryImpl.getConfig().setUserId(id);
    }

    public String getAndroidId() {
        return MD5Util.MD5(Utils.getUniquePsuedoID());
    }

    /**
     * 退出程序
     *
     * @param con
     */
    public synchronized void exitApp(Context con) {
        if (activityList != null) {
            for (Activity ac : activityList) {
                if (!ac.isFinishing()) {
                    ac.finish();
                }
            }
        }
        if (con != null) {
            // 清除通知栏
            ((NotificationManager) con
                    .getSystemService(Context.NOTIFICATION_SERVICE))
                    .cancelAll();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void checkToken() {
        SocketAPINettyBootstrap.getInstance().setOnConnectListener(new SocketAPINettyBootstrap.OnConnectListener() {
            @Override
            public void onExist() {
            }

            @Override
            public void onSuccess() {
                LogUtil.d("检测到连接成功-------------------");
                judgeIsLogin();
                checkUpdate();  //检查更新
            }

            @Override
            public void onFailure(boolean tag) {
                LogUtil.d("检测到连接失败--------------");
                if (tag) {
                    connectionError();
                }
            }
        });
    }

    private void connectionError() {
        UserManager.getInstance().logout();
        onUserUpdate(false);
    }

    private void judgeIsLogin() {
        if (UserManager.getInstance().isLogin()) {
            LogUtil.d("--已经登录,开始校验token");
            NetworkAPIFactoryImpl.getUserAPI().loginWithToken(new OnAPIListener<LoginReturnEntity>() {
                @Override
                public void onError(Throwable ex) {
                    ex.printStackTrace();
                    LogUtil.d("--登录失败.token已经失效");
                    UserManager.getInstance().logout();
                    onUserUpdate(false);
                }

                @Override
                public void onSuccess(LoginReturnEntity loginReturnEntity) {
                    UserEntity en = new UserEntity();
                    en.setBalance(loginReturnEntity.getUserinfo().getBalance());
                    en.setId(loginReturnEntity.getUserinfo().getId());
                    en.setToken(loginReturnEntity.getToken());
                    en.setMobile(loginReturnEntity.getUserinfo().getPhone());
                    en.setUserType(loginReturnEntity.getUserinfo().getType());
                    UserManager.getInstance().saveUserEntity(en);

                    onUserUpdate(true);
                }
            });
        }
    }

    private void checkUpdate() {
        LogUtil.d("检查更新----------");
        NetworkAPIFactoryImpl.getUserAPI().update(new OnAPIListener<CheckUpdateInfoEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(CheckUpdateInfoEntity checkUpdateInfoEntity) {
                SPUtils.putString("version", checkUpdateInfoEntity.getNewAppVersionName());
                LogUtil.d("checkUpdateInfoEntity:" + checkUpdateInfoEntity.toString());
                if (checkUpdateInfoEntity != null && checkUpdateInfoEntity.getNewAppVersionCode() > getVersionCode()) {
                    EventBusMessage msg = new EventBusMessage(-11);
                    msg.setCheckUpdateInfoEntity(checkUpdateInfoEntity);
                    EventBus.getDefault().postSticky(msg);
                } else {
                    LogUtil.d("--最新版本");
                }
            }
        });

    }

    /**
     * 获取当前应用版本号
     */
    private int getVersionCode() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //负载均衡
    private final OkHttpClient client = new OkHttpClient();

    private void requestServerIp() {
        LogUtil.d("post请求负载均衡--------------");
        FormBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url("http://139.224.34.22/cgi-bin/flight/router/v1/get_server.fcgi")
                .post(body)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.d("负载均衡请求失败----------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                SocketAPIRequestManage.getInstance().stop();
                ResponseBody body2 = response.body();
                String str2 = body2.string();
                IpAndPortEntity entity = JSONEntityUtil.JSONToEntity(IpAndPortEntity.class, str2);
                if (entity != null) {
//                    Host.setSocketServerIp("122.144.169.217");
//                    Host.setSocketServerPort((short) entity.getPort());
                    networkAPIConfig.setSocketServerIp(entity.getIp());
                    networkAPIConfig.setSocketServerPort((short) 16205);
                    LogUtil.d("负载均衡请求成功,设置ip和端口--------------");
                }
            }
        });
    }
}
