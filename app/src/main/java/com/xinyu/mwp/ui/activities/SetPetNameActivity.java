package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
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
 * 修改昵称界面
 * Created by Administrator on 2017/1/4.
 */
public class SetPetNameActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.et_pet_name)
    EditText etPetName;
    @BindView(R.id.btn_random)
    TextView btnRandom;
    @BindView(R.id.btn_modify_name)
    Button btnModifyName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_name);
        ButterKnife.bind(this);
        initActinbar(toolBar, null);

    }

    @Override
    public void initActinbar(Toolbar toolbar, DrawerLayout drawer) {
        super.initActinbar(toolbar, drawer);
        toolbarTitle.setText("修改昵称");
        toolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Object data) {

    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.btn_random, R.id.btn_modify_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_random: //随机获取昵称
                break;
            case R.id.btn_modify_name:  //确认修改
                toActivity(MainActivity.class,false);
                finish();
                break;
        }
    }
}
