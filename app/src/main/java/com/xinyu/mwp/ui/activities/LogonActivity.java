package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 * Created by Administrator on 2017/1/4.
 */
public class LogonActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.et_logon_phone)
    EditText etLogonPhone;
    @BindView(R.id.et_logon_trade_pwd)
    EditText etLogonTradePwd;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.btn_register_next)
    Button btnRegisterNext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        ButterKnife.bind(this);
        //initToolbar(toolBar, null);

    }

    /*@Override
    public void initToolbar(Toolbar toolbar,  TextView titleView, String title) {
        super.initToolbar(toolbar, drawer);
        toolbarTitle.setText("登录");
        toolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);
        // toolbar.setOverflowIcon(R.mipmap.ic_launcher);
//        toolbar.setPointerIcon();
    }*/

    @Override
    public void initView() {

    }

    @Override
    public void initData(Object data) {

    }

    private static final String TAG = "RegisterActivity";

    @Override
    public void initListener() {

    }


    @OnClick({R.id.tv_toolbar_right, R.id.btn_register_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_right:  //进入注册
                toActivity(RegisterActivity.class,false);
                finish();
                break;
            case R.id.btn_register_next: //登录,
                toActivity(MainActivity.class,false);
                finish();
                break;
        }
    }
}
