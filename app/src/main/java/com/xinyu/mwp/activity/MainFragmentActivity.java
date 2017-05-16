package com.xinyu.mwp.activity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseMultiFragmentActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.entity.CheckUpdateInfoEntity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.fragment.DealFragment;
import com.xinyu.mwp.fragment.IndexFragment;
import com.xinyu.mwp.fragment.LeftFragment;
import com.xinyu.mwp.fragment.ShareOrderExpectFragment;
import com.xinyu.mwp.user.OnUserUpdateListener;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.ActivityUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.ForceUpdateDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;
import static com.xinyu.mwp.view.ForceUpdateDialog.FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE;
import static com.qiangxi.checkupdatelibrary.dialog.UpdateDialog.UPDATE_DIALOG_PERMISSION_REQUEST_CODE;


/**
 * @author : Created by Benjamin
 * @email : samwong.greatstone@gmail.com
 */
public class MainFragmentActivity extends BaseMultiFragmentActivity implements OnUserUpdateListener {
    private static final String[] TITLES = {"首页", "交易", "晒单"};

    @ViewInject(R.id.drawer)
    protected DrawerLayout drawer;
    @ViewInject(R.id.bottomLayout)
    private LinearLayout bottombar;
    protected LeftFragment leftFragment;
    private long exitNow;
    private long first = 0;
    private boolean flag = true;
    private UpdateDialog mUpdateDialog;
    private ForceUpdateDialog mForceUpdateDialog;


    @Override
    public int getFragmentContainerId() {
        return R.id.contentContainer;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_mainfragment;
    }

    @Override
    public void createFragmentsToBackStack() {
        fragments.add(new IndexFragment());
        fragments.add(new DealFragment());
        // fragments.add(new ShareOrderFragment());  //隐藏晒单界面
        fragments.add(new ShareOrderExpectFragment());
        leftFragment = new LeftFragment();

        pushFragmentToContainer(R.id.leftContainer, leftFragment);
        pushFragmentToBackStack(0);
    }

    @Override
    public void pushFragmentToBackStack(int selectIndex) {
        super.pushFragmentToBackStack(selectIndex);
        bottombar.getChildAt(selectIndex).setSelected(true);
    }

    @Override
    protected void initView() {
        super.initView();
        UserManager.getInstance().registerUserUpdateListener(this);
        setSwipeBackEnable(false);
        judgeIsLogin();
    }

    @Override
    protected void initListener() {
        super.initListener();
        leftFragment.setLeftClickListener(new LeftFragment.LeftClickListener() {
            @Override
            public void click(View v, int action, Object obj) {
                toggleDrawer(false);
                switch (action) {
//                    case R.id.icon:
//                        next(UserSettingActivity.class);
//                        break;
                    case R.id.login:
                        ActivityUtil.nextLogin(context);
                        break;
                    case R.id.register:
                        ActivityUtil.nextRegister(context);
                        break;
//                    case R.id.user_balance:
                    //  next(UserAssetsActivity.class);
//                        break;
//                    case R.id.myScoreLayout:
//                        break;
//                    case R.id.myAttention:
//                        next(MyAttentionActivity.class);
//                        break;
//                    case R.id.myPushOrder:
//                        next(MyPushOrderActivity.class);
//                        break;
//                    case R.id.myShareOrder:
//                        next(MyShareOrderActivity.class);
//                        break;
                    case R.id.dealDetail:
                        if (UserManager.getInstance().isLogin()) {
                            next(DealDetailFragmentActivity.class);
                        } else {
                            next(LoginActivity.class);
                        }

                        break;
//                    case R.id.feedback:
//                        next(RechargeRecordActivity.class);
//                        break;
//                    case R.id.score:
//                        next(CheckPhoneNumberActivity.class);
//                        break;
//                    case R.id.about:
//                        next(AddBankInfoActivity.class);
//                        break;
                    case R.id.myCashOut:
                        next(CashActivity.class);
                        break;
                    case R.id.myRecharge:
                        next(RechargeActivity.class);
                        break;
                    case R.id.logout:
                        UserManager.getInstance().logout();
                        ToastUtils.show(context, "退出登录");
                        next(LoginActivity.class);
                        break;
                }
            }
        });

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
                leftFragment.update();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    public void postNext(final Class clazz) {
        drawer.closeDrawers();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                next(clazz);
            }
        }, 300);
    }

    public void toggleDrawer(boolean open) {
        if (open) {
            drawer.openDrawer(Gravity.LEFT);
        } else {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void onUserUpdate(boolean isLogin) {
        if (isLogin) {
            leftFragment.userUpdate();
        }
    }

    public void onClickSelect(View view) {
        if (System.currentTimeMillis() - first > 350) {
            if (selectIndex >= 0) {
                bottombar.getChildAt(selectIndex).setSelected(false);
            }
            try {
                for (int i = 0; i < bottombar.getChildCount(); ++i) {
                    View childView = bottombar.getChildAt(i);
                    if (view == childView) {
                        pushFragmentToBackStack(i);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        first = System.currentTimeMillis();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)) {

            if ((System.currentTimeMillis() - exitNow) > 2000) {
                Toast.makeText(this, String.format(getString(R.string.confirm_exit_app), getString(R.string.app_name)), Toast.LENGTH_SHORT).show();
                exitNow = System.currentTimeMillis();
            } else if ((System.currentTimeMillis() - exitNow) > 0) {
                MyApplication.getApplication().exitApp(this);
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserManager.getInstance().unregisterUserUpdateListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("执行onResume");
        if (UserManager.getInstance().isLogin()) {
            leftFragment.userUpdate();
        }
    }

    private void judgeIsLogin() {
        if (!UserManager.getInstance().isLogin()) {  //退出登录或者断网的状态下,直接跳转到登录界面
            next(LoginActivity.class);
        } else {
            if (flag) {
                EventBus.getDefault().register(context);
                flag = false;
            }
        }
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case -11:
                if (eventBusMessage.getCheckUpdateInfoEntity().getIsForceUpdate() == 0) {
                    forceUpdateDialog(eventBusMessage.getCheckUpdateInfoEntity());
                } else {  //非强制更新
                    updateDialog(eventBusMessage.getCheckUpdateInfoEntity());
                }
                break;
        }
    }

    /**
     * 6.0系统需要重写此方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                mUpdateDialog.download();
            } else if (requestCode == FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                mForceUpdateDialog.download();
            }
        } else {
            Toast.makeText(this, "请开启权限后重试", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * 强制更新
     */
    public void forceUpdateDialog(CheckUpdateInfoEntity mCheckUpdateInfo) {
        mForceUpdateDialog = new ForceUpdateDialog(this);
        mForceUpdateDialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                .setFileName(getResources().getString(R.string.app_name))
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/HangTouBao").show();
    }

    /**
     * 非强制更新
     */
    public void updateDialog(CheckUpdateInfoEntity mCheckUpdateInfo) {
        mUpdateDialog = new UpdateDialog(this);
        mUpdateDialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                .setFileName(getResources().getString(R.string.app_name))
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/HangTouBao")
                .setShowProgress(true)
                .setIconResId(R.mipmap.ic_launcher)
                .setAppName(mCheckUpdateInfo.getAppName()).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
