package com.xinyu.mwp.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseActivity;
import com.xinyu.mwp.configs.Constant;
import com.xinyu.mwp.utils.CodeUtils;
import com.xinyu.mwp.utils.LogUtil;
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
    private CodeUtils codeUtils;
    private String codePic;  //图片显示的验证码


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //ButterKnife.bind(this);
        //initToolbar(toolBar, null);
        initView();

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
        initToolbar(toolBar, toolbarTitle, getString(R.string.et_register));
       toolBar.setNavigationIcon(R.mipmap.ic_toolbar_back);

        codeUtils = CodeUtils.getInstance();
        //生成一张验证码图片
        ivIdentifyingCode.setImageBitmap(codeUtils.createBitmap());
//        ViewGroup.LayoutParams params = new Toolbar.LayoutParams(getResources().getDimension(R.dimen.code_width),
//                getResources().getDimension(R.dimen.code_height));
       // ivIdentifyingCode.setLayoutParams(params);
        codePic = codeUtils.getCode();
        LogUtil.d("1,图片显示的验证码是"+codePic);

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

        String phoneNumber = etRegisterPhone.getText().toString().trim(); //获取用户输入的验证码

        String mVoiceCode = etIdentifyingVoiceCode.getText().toString().trim(); //语音验证码
        String mPhoneCode = etRegisterCode.getText().toString().trim();  //输入的图片验证码
        String pwd = etUserPwd.getText().toString().trim();   //设置的密码

        switch (view.getId()) {
            case R.id.iv_identifying_code: //切换图片验证码
                codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                ivIdentifyingCode.setImageBitmap(bitmap);
                codePic = codeUtils.getCode();
                LogUtil.d("2,图片显示的验证码是"+codePic);
                break;
            case R.id.btn_get_voice_code:  //获取语音验证码
                if (!phoneNumber.matches(Constant.REGEX_MOBILE)) {  //判断手机号
                    ToastUtil.showToast("手机号码输入有误,请重新输入", context);
                    return;
                }
                //判断图片验证码是否正确
                if (null == mPhoneCode || TextUtils.isEmpty(mPhoneCode)) {
                   ToastUtil.showToast("请输入图片验证码",context);
                    return;
                }
                //图片显示的验证码
                codePic = codeUtils.getCode();
                LogUtil.d("3,图片显示的验证码是"+codePic);
                if (codePic.equalsIgnoreCase(mPhoneCode)) {
                    LogUtil.d("图片验证码正确,执行操作,获取语音验证码");


                } else {
                    Toast.makeText(this, "图片验证码错误,请重新输入", 0).show();
                    return;
                }

                break;
            case R.id.btn_register_next: //下一步
                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(mVoiceCode)
                        || TextUtils.isEmpty(mPhoneCode) ||TextUtils.isEmpty(pwd)) {
                    ToastUtil.showToast("输入不能为空",context);
                    return;
                }
                //1,判断手机号
                if (!phoneNumber.matches(Constant.REGEX_MOBILE)) {
                    ToastUtil.showToast("手机号码输入有误,请重新输入", context);
                    return;
                }

                //2,判断图片验证码
                codePic = codeUtils.getCode();
                LogUtil.d("4,图片显示的验证码是"+codePic);
                if (codePic.equalsIgnoreCase(mPhoneCode)) {
                    LogUtil.d("图片验证码正确,执行操作,下一步");

                } else {
                    Toast.makeText(this, "图片验证码错误,请重新输入", 0).show();
                    return;
                }
                //3,判断语音验证码


                //设定交易密码
                toActivity(SetTradePwdActivity.class,false);
                break;
        }
    }
}
