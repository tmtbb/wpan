package com.xinyu.mwp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.entity.CheckUpdateInfoEntity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.UserEntity;
import com.xinyu.mwp.exception.CheckException;
import com.xinyu.mwp.helper.CheckHelper;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.ActivityUtil;
import com.xinyu.mwp.util.ErrorCodeUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.SHA256Util;
import com.xinyu.mwp.util.SPUtils;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.view.ForceUpdateDialog;
import com.xinyu.mwp.view.WPEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.xinyu.mwp.view.ForceUpdateDialog.FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE;
import static com.qiangxi.checkupdatelibrary.dialog.UpdateDialog.UPDATE_DIALOG_PERMISSION_REQUEST_CODE;


/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-01-11 11:29
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class LoginActivity extends BaseControllerActivity {

    @ViewInject(R.id.userNameEditText)
    private WPEditText userNameEditText;
    @ViewInject(R.id.passwordEditText)
    private WPEditText passwordEditText;
    @ViewInject(R.id.loginButton)
    private Button loginButton;
    @ViewInject(R.id.wxLoginButton)
    private Button wxLoginButton;
    private CheckHelper checkHelper = new CheckHelper();
    private long exitNow;
    private boolean flag = true;
    private ForceUpdateDialog mForceUpdateDialog;
    private UpdateDialog mUpdateDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("登录");
        leftImage.setVisibility(View.INVISIBLE);
        userNameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(loginButton, userNameEditText, passwordEditText);
        setSwipeBackEnable(false);
        if (flag) {
            EventBus.getDefault().register(this); // EventBus注册广播()
            flag = false;//更改标记,使其不会再进行多次注册
        }
    }

    @Event(value = {R.id.registerText, R.id.loginButton, R.id.findPwd, R.id.wxLoginButton})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerText:
                ActivityUtil.nextRegister(context);
                break;

            case R.id.wxLoginButton:
                if (!MyApplication.api.isWXAppInstalled()) {
                    ToastUtils.show(context, "您还未安装微信客户端");
                    return;
                }
                ToastUtils.show(context, "微信登录");
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                MyApplication.api.sendReq(req);
                break;
            case R.id.findPwd:    //忘记密码
                ActivityUtil.nextResetUserPwd(context);
                break;
            case R.id.loginButton:
                showLoader("正在登录...");
                CheckException exception = new CheckException();
                if (checkHelper.checkMobile(userNameEditText.getEditTextString(), exception)
                        && checkHelper.checkPassword(passwordEditText.getEditTextString(), exception)) {
                    Utils.closeSoftKeyboard(view);
                    String newPwd = SHA256Util.shaEncrypt(SHA256Util.shaEncrypt(passwordEditText.getEditTextString() + "t1@s#df!") + userNameEditText.getEditTextString());
                    NetworkAPIFactoryImpl.getUserAPI().login(userNameEditText.getEditTextString(), newPwd, null,
                            new OnAPIListener<LoginReturnEntity>() {
                                @Override
                                public void onError(Throwable ex) {
                                    ex.printStackTrace();
                                    closeLoader();
                                    ErrorCodeUtil.showEeorMsg(context, ex);
                                }

                                @Override
                                public void onSuccess(LoginReturnEntity loginReturnEntity) {
                                    closeLoader();
                                    ToastUtils.show(context, "登陆成功");
                                    UserEntity en = new UserEntity();
                                    en.setBalance(loginReturnEntity.getUserinfo().getBalance());
                                    en.setId(loginReturnEntity.getUserinfo().getId());
                                    en.setToken(loginReturnEntity.getToken());
                                    en.setUserType(loginReturnEntity.getUserinfo().getType());
                                    en.setMobile(loginReturnEntity.getUserinfo().getPhone());
                                    UserManager.getInstance().saveUserEntity(en);
                                    UserManager.getInstance().setLogin(true);
                                    MyApplication.getApplication().onUserUpdate(true);
                                    SPUtils.putString("phone", userNameEditText.getEditTextString());
                                    finish();
                                }
                            });
                } else {
                    closeLoader();
                    showToast(exception.getErrorMsg());
                }
                break;
        }
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

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case -6:  //成功
                LogUtil.d("登录--当前是接收到微信登录成功的消息,finish");
                finish();
                break;
            case -7:  //成功
                LogUtil.d("登录--当前是接收到绑定成功的消息,finish");
                finish();
                break;
            case -11:  //需要弹窗
                LogUtil.d("登录--当前是接收需要更新---的消息");
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
                mUpdateDialog.download();  //非
            } else if (requestCode == FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                mForceUpdateDialog.download();  //强制下载
            }
        } else {
            Toast.makeText(this, "请开启权限后重试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 强制更新
     */
    public void forceUpdateDialog(CheckUpdateInfoEntity mCheckUpdateInfo) {
        mForceUpdateDialog = new ForceUpdateDialog(LoginActivity.this);
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
    protected void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
