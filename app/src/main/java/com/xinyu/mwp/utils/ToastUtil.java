package com.xinyu.mwp.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Don on 2016/11/12 9:27.
 * Describe：${Toast工具类}
 * Modified：${TODO}
 */

public class ToastUtil {

    private static Timer mTimer;
    private static TimerTask mCycleTask;
    private static Toast mFaileToast;
    private static Toast mSuccessToast;
    private static Toast mToast;

    public static void showToast(String str, Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        }
        mToast.setText(str);
        mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 500);
        mToast.show();
    }

    public static void showToastResources(int id, Context context) {
        showToast(context.getResources().getString(id), context);
    }

    public static void showToastFailPic(String message) {
        if (mFaileToast == null) {
            mFaileToast = Toast.makeText(BaseApplication.getInstance(), "", Toast.LENGTH_SHORT);
            mFaileToast.setText(message);
            mFaileToast.setGravity(Gravity.CENTER, 0, -0);
            LinearLayout layout = (LinearLayout) mFaileToast.getView();
            ImageView image = new ImageView(BaseApplication.getInstance());
            image.setImageResource(R.mipmap.tips_failed);
            layout.addView(image, 0);
        } else {
            mFaileToast.setText(message);
            mFaileToast.setDuration(Toast.LENGTH_SHORT);
        }
        mFaileToast.show();
    }

    public static void showToastFailPic(int message) {
        if (mFaileToast == null) {
            mFaileToast = Toast.makeText(BaseApplication.getInstance(), "", Toast.LENGTH_SHORT);
            mFaileToast.setText(message);
            mFaileToast.setGravity(Gravity.CENTER, 0, -0);
            LinearLayout layout = (LinearLayout) mFaileToast.getView();
            ImageView image = new ImageView(BaseApplication.getInstance());
            image.setImageResource(R.mipmap.tips_failed);
            layout.addView(image, 0);
        } else {
            mFaileToast.setText(message);
            mFaileToast.setDuration(Toast.LENGTH_SHORT);
        }
        mFaileToast.show();
    }

    public static void showToastOkPic(String message) {
        if (mSuccessToast == null) {
            mSuccessToast = Toast.makeText(BaseApplication.getInstance(), "", Toast.LENGTH_SHORT);
            mSuccessToast.setText(message);
            mSuccessToast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout layout = (LinearLayout) mSuccessToast.getView();
            ImageView image = new ImageView(BaseApplication.getInstance());
            image.setImageResource(R.mipmap.tips_ok);
            layout.addView(image, 0);
        } else {
            mSuccessToast.setText(message);
            mSuccessToast.setDuration(Toast.LENGTH_SHORT);
        }
        mSuccessToast.show();
    }

    private static void startShowToast(final String str) {
        releaseTimer();
        mTimer = new Timer();
        mCycleTask = new TimerTask() {
            @Override
            public void run() {
                Message mag = new Message();
                mag.obj = str;
                mag.what = 0;
                edthandler.sendMessage(mag);
            }
        };
        mTimer.schedule(mCycleTask, 200);
    }

    public void showToast(String msg) {
        if (mSuccessToast == null) {
            mSuccessToast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
            mSuccessToast.setGravity(Gravity.CENTER, 0, -0);
            LinearLayout layout = (LinearLayout) mSuccessToast.getView();
            ImageView image = new ImageView(BaseApplication.getInstance());
            image.setImageResource(R.mipmap.tips_failed);
            layout.addView(image, 0);
        } else {
            mSuccessToast.setText(msg);
            mSuccessToast.setDuration(Toast.LENGTH_SHORT);
        }
        mSuccessToast.show();
    }

    public void cancelToast() {
        if (mFaileToast != null) {
            mFaileToast.cancel();
        }
        if (mSuccessToast != null) {
            mSuccessToast.cancel();
        }
        if (mToast != null) {
            mToast.cancel();
        }
    }

    static Handler edthandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private static void releaseTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mCycleTask != null) {
            mCycleTask.cancel();
        }
    }
}
