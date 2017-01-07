package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置交易密码界面
 * Created by Administrator on 2017/1/4.
 */
public class SetTradePwdActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.et_trade_pwd)
    EditText etTradePwd;
    @BindView(R.id.et_trade_pwd_again)
    EditText etTradePwdAgain;
    @BindView(R.id.btn_trade_pwd_sure)
    Button btnRegisterNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradepwd);
        ButterKnife.bind(this);
       //initToolbar(toolBar, null);

    }

    /*@Override
    public void initToolbar(Toolbar toolbar, DrawerLayout drawer) {
        super.initToolbar(toolbar, drawer);
        toolbarTitle.setText("设置交易密码");
        toolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);
    }*/

    @Override
    public void initView() {

    }

    @Override
    public void initData(Object data) {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.btn_trade_pwd_sure)
    public void onClick() {
        toActivity(SetPetNameActivity.class,false);  //修改昵称
    }
}
