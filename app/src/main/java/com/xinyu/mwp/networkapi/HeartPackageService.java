package com.xinyu.mwp.networkapi;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.util.LogUtil;

/**
 * Created by Administrator on 2017/5/17.
 */
public class HeartPackageService extends Service {

    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendHeartPackage();
            handler.postDelayed(this, 3000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("服务------启动----onCreate() executed");
        handler.postDelayed(runnable, 10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("服务-----------onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("服务-------销毁onDestroy() executed");
        handler.removeCallbacks(runnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 发送心跳包
     */
    private void sendHeartPackage() {
        NetworkAPIFactoryImpl.getUserAPI().heart(new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("心跳包发送失败-----");
            }

            @Override
            public void onSuccess(Object o) {
                LogUtil.d("心跳包发送成功------:" + o.toString());
            }
        });
    }
}
