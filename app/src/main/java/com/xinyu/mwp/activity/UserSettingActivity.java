package com.xinyu.mwp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.ActivityUtil;
import com.xinyu.mwp.util.CropImageUtil;
import com.xinyu.mwp.util.FileCacheUtil;
import com.xinyu.mwp.util.ImageUtil;
import com.xinyu.mwp.util.PermissionManagerUtil;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.UUID;

/**
 * Created by Benjamin on 17/1/16.
 */

public class UserSettingActivity extends BaseRefreshActivity {
    @ViewInject(R.id.head)
    private CellView head;
    @ViewInject(R.id.phoneNumber)
    private CellView phoneNumber;
    @ViewInject(R.id.nickName)
    private CellView nickName;
    @ViewInject(R.id.dealPsw)
    private CellView dealPsw;
    @ViewInject(R.id.loginPsw)
    private CellView loginPsw;

    private PermissionManagerUtil permissionManagerUtil;

    @Override
    protected int getContentView() {
        return R.layout.activity_usersetting;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("个人设置");
    }

    @Event(value = {R.id.head, R.id.nickName, R.id.dealPsw, R.id.loginPsw, R.id.logout})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.head:
                permissionManagerUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.nickName:
                showToast("修改昵称");
                break;
            case R.id.dealPsw:
                next(ModifyDealPasswordActivity.class);
                break;
            case R.id.loginPsw:
                next(ModifyLoginPasswordActivity.class);
                break;
            case R.id.logout:
                showToast("退出登录");
                UserManager.getInstance().logout();
                finish();
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        permissionManagerUtil = new PermissionManagerUtil(new PermissionManagerUtil.OnPermissionListener() {
            @Override
            public void granted(String permission) {
                ActivityUtil.nextUploadImgsForResult(UserSettingActivity.this);
            }

            @Override
            public void denied(String permission) {

            }
        });
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
    }

    private void doRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                head.updateContentRightImage(ImageUtil.getRandomUrl());
                phoneNumber.updateContent("133****9999");
                nickName.updateContent("Benjamin");
                getRefreshController().getContentView().setVisibility(View.VISIBLE);
                getRefreshController().refreshComplete();
            }
        }, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            showLoader("上传头像中...");
            String path = data.getStringExtra(Constant.IntentKey.CHOOSE_IMGS_RES);
            new CropImageTask().execute(path);

        } else if (requestCode == 0 && resultCode == RESULT_OK) {
            initData();
        }
    }

    class CropImageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String filePath = FileCacheUtil.getInstance().getCacheDir(MyApplication.getApplication(), null) + "/" + UUID.randomUUID().toString() + ".jpg";
            CropImageUtil cropImageUtil = new CropImageUtil();
            cropImageUtil.cropImage(params[0], filePath);
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            uploadFile(s);
            super.onPostExecute(s);
        }
    }

    private void uploadFile(final String path) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeLoader();
                new File(path).delete();
                showToast("头像上传成功");
//                //清除头像缓存
//                String uri = NetworkAPIConstant.User.USER_HEADER_URL + userEntity.getId();
//                MemoryCacheUtils.removeFromCache(uri, ImageLoader.getInstance().getMemoryCache());
//                DiskCacheUtils.removeFromCache(uri, ImageLoader.getInstance().getDiskCache());
//
//                head.updateContentRightImage(s);
//                ImageLoader.getInstance().displayImage(s, header, DisplayImageOptionsUtil.getInstance().getUserHeaderOptions());
//                UserManager.getInstance().getUserEntity().setIcon(s);
//                UserManager.getInstance().saveUserEntity(UserManager.getInstance().getUserEntity());
//                sendBroadcast(new Intent(Constant.IntentKey.USER_UPDATE));
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManagerUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
