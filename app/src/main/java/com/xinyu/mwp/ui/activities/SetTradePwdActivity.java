package com.xinyu.mwp.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.configs.Constant;
import com.xinyu.mwp.utils.LogUtil;
import com.xinyu.mwp.utils.ToastUtil;

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
    private String mTradePwd1;
    private String mTradePwd2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradepwd);
        ButterKnife.bind(this);
        initActinbar(toolBar, null);

        //触摸输入框的时候,清除所有格式
        etTradePwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });
        etTradePwdAgain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });
    }

    @Override
    public void initActinbar(Toolbar toolbar, DrawerLayout drawer) {
        super.initActinbar(toolbar, drawer);
        toolbarTitle.setText("设置交易密码");
        toolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);
    }

    @Override
    public void initView() {
        LogUtil.d("inITvIEW被调用; 测试");
        // mTradePwd2 =
    }

    @Override
    public void initData(Object data) {
        LogUtil.d("inItData被调用; 测试");
    }

    //对输入进行判断
    private boolean judgeInPut() {

        //1,判断密码是否合乎规则

        LogUtil.d("输入的密码是:" + mTradePwd1);
        if (!mTradePwd1.matches(Constant.REGEX_PASSWORD)) {
            //如果不符合规范,提示重新输入
            etTradePwd.setText("");
            requstFocus(etTradePwd, "密码不符合规范,请重新输入", Color.RED, true);
            return false;
        }

        //获取输入的密码
        LogUtil.d("输入的密码是重新是:" + mTradePwd2);
        if (!mTradePwd2.matches(Constant.REGEX_PASSWORD)) {
            //如果不符合规范,提示重新输入
            etTradePwdAgain.setText("");
            requstFocus(etTradePwdAgain, "密码不符合规范,请重新输入", Color.RED, true);
            return false;
        }
        return true;
    }

    @Override
    public void initListener() {
        LogUtil.d("监听被调用");
    }

    @OnClick(R.id.btn_trade_pwd_sure)
    public void onClick() {
        mTradePwd1 = etTradePwd.getText().toString();
        mTradePwd2 = etTradePwdAgain.getText().toString();
        if (TextUtils.isEmpty(mTradePwd1) || TextUtils.isEmpty(mTradePwd2)) {
            ToastUtil.showToast("输入不能为空", context);
            return;
        }

        if (!judgeInPut()) { //判断输入
            return;
        }
        //2,判断是否相等
        LogUtil.d("第一次密码:" + mTradePwd1 + "第二次密码:" + mTradePwd2);
        if (!mTradePwd1.equals(mTradePwd2)) {
            etTradePwdAgain.setText("");
            // requstFocus(etTradePwdAgain, "两次密码不一致,请重新输入", Color.RED, true);
            ToastUtil.showToast("两次密码不一致,请重新输入", context);
            return;
        }
        toActivity(SetPetNameActivity.class, false);  //修改昵称
    }

    //检查
    public void requstFocus(EditText et, String hint, int hintColor, boolean needFocus) {
        if (hint == null) {
            hint = "请输入6-16位密码(字母,数字)";
        }
        et.setHint(hint);
        et.setHintTextColor(hintColor);
        if (needFocus) {
            et.requestFocus();
        }
    }

    public void clearAll() {
        requstFocus(etTradePwd, null, Color.GRAY, false);
        requstFocus(etTradePwdAgain, null, Color.GRAY, false);
    }
}

