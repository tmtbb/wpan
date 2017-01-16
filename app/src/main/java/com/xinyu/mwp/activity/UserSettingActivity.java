package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshActivity;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.ImageUtil;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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
                showToast("修改头像");
                break;
            case R.id.nickName:
                showToast("修改昵称");
                break;
            case R.id.dealPsw:
                showToast("修改交易密码");
                break;
            case R.id.loginPsw:
                showToast("修改登录密码");
                break;
            case R.id.logout:
                showToast("退出登录");
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
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
}
