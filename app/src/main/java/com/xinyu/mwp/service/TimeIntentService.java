package com.xinyu.mwp.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xinyu.mwp.utils.SharePerferenceUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Don on 2016/11/12 15:37.
 * Describe：${TODO}
 * Modified：${TODO}
 */

public class TimeIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.whale.wealth.ui.service.action.FOO";
    private static final String ACTION_BAZ = "com.whale.wealth.ui.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.whale.wealth.ui.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.whale.wealth.ui.service.extra.PARAM2";
    private Timer mForegroundTimer;
    private final long mTime = 6000;
    private Context context;
    private TimerTask mCycleTask = new TimerTask() {
        @Override
        public void run() {
            SharePerferenceUtil.saveTimeOutLock(true);
        }
    };

    public TimeIntentService() {
        super("TimeIntentService");
        this.context = this;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context) {
        Intent intent = new Intent(context, TimeIntentService.class);
        intent.setAction(ACTION_FOO);
        context.startService(intent);
    }

    private void startTimeTask() {
        String token = SharePerferenceUtil.getToken();
        if (!SharePerferenceUtil.isOutLock() && !TextUtils.isEmpty(token)) {
            if (mForegroundTimer == null) {
                mForegroundTimer = new Timer();
            }
            mForegroundTimer.schedule(mCycleTask, mTime);
        }
    }

    private void stopTimeTask() {
        /*String token = SharePerferenceUtil.getToken();
        if (SharePerferenceUtil.isOutLock() && !TextUtils.isEmpty(token)) {
            startActivity(LockActivity.newTaskInstance(context));
        }*/
        if (mForegroundTimer != null) {
            mForegroundTimer.cancel();
            mForegroundTimer = null;
        }
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context) {
        Intent intent = new Intent(context, TimeIntentService.class);
        intent.setAction(ACTION_BAZ);
        context.startService(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                startTimeTask();
            } else if (ACTION_BAZ.equals(action)) {
                stopTimeTask();
            }
        }
    }
}
