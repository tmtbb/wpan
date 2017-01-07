package com.xinyu.mwp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面
 * Created by Administrator on 2017/1/4.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;   //输入的手机号
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;  //输入验证码
    @BindView(R.id.iv_identifying_code)
    ImageView ivIdentifyingCode; //图片验证码
    @BindView(R.id.et_identifying_voice_code)
    EditText etIdentifyingVoiceCode; //输入声音验证码
    @BindView(R.id.btn_get_voice_code)
    Button btnGetVoiceCode;        //获取声音验证码
    @BindView(R.id.btn_register_next)
    Button btnRegisterNext;  //下一步
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //initToolbar(toolBar, null);

    }

    /*@Override
    public void initToolbar(Toolbar toolbar, DrawerLayout drawer) {
        super.initToolbar(toolbar, drawer);
        toolbarTitle.setText("注册");
//        toolbarTitle.setTextColor();
//        toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);
        toolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);
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

    @OnClick({R.id.iv_identifying_code, R.id.btn_get_voice_code, R.id.btn_register_next})
    public void onClick(View view) {

        String phoneNumber = etRegisterPhone.getText().toString().trim(); //获取输入的验证码
        String mVoiceCode = etIdentifyingVoiceCode.getText().toString().trim(); //语音验证码
        String mPhoneCode = etRegisterCode.getText().toString().trim();  //验证码
        String pwd = etUserPwd.getText().toString().trim();
        switch (view.getId()) {
            case R.id.iv_identifying_code: //切换图片验证码
                break;
            case R.id.btn_get_voice_code:  //获取语音验证码

                break;
            case R.id.btn_register_next: //进入
                // String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
                String regex = "[1][3578]\\d{9}"; //简单判断手机号码
                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(mVoiceCode)
                        || TextUtils.isEmpty(mPhoneCode) ||TextUtils.isEmpty(pwd)) {
                    ToastUtil.showToast("不能为空",context);
                    return;
                }
                if (!phoneNumber.matches(regex)) {
                    ToastUtil.showToast("手机号码输入有误,请重新输入", context);
                    return;
                }
                //设定交易密码
                toActivity(SetTradePwdActivity.class,false);
                break;
        }
    }
}
