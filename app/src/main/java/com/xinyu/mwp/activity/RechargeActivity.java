package com.xinyu.mwp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseActivity;
import com.xinyu.mwp.activity.base.BaseRefreshActivity;
import com.xinyu.mwp.activity.unionpay.APKActivity;
import com.xinyu.mwp.activity.unionpay.BaseUnionPayActivity;
import com.xinyu.mwp.activity.unionpay.JARActivity;
import com.xinyu.mwp.activity.wxapi.WXEntryActivity;
import com.xinyu.mwp.adapter.GalleryAdapter;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.BalanceInfoEntity;
import com.xinyu.mwp.entity.WXPayReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CellEditView;
import com.xinyu.mwp.view.CellView;
import com.xinyu.mwp.view.banner.IndexBannerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Benjamin on 17/1/13.
 */

public class RechargeActivity extends BaseRefreshActivity {
    @ViewInject(R.id.refreshFrameLayout)
    private PtrFrameLayout refreshFrameLayout;
    @ViewInject(R.id.account)
    private CellView account;
    @ViewInject(R.id.money)
    private CellView money;
    //    @ViewInject(R.id.myBankCard)
//    private CellView myBankCard;
    @ViewInject(R.id.rechargeMoney)
    private EditText rechargeMoney;
    @ViewInject(R.id.rechargeType)
    private CellView rechargeType;
    @ViewInject(R.id.bannerView)
    private IndexBannerView bannerView;
    private int choice = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("充值");
        bannerView.centerDot();
        bannerView.setRefreshLayout(refreshFrameLayout);
        bannerView.update(TestDataUtil.getIndexBanners(3));
//rechargeMoney.
//        requestBalance();
    }

    private void requestBalance() {
        NetworkAPIFactoryImpl.getUserAPI().balance(new OnAPIListener<BalanceInfoEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("请求余额失败");
            }

            @Override
            public void onSuccess(BalanceInfoEntity balanceInfoEntity) {
                LogUtil.d("请求余额信成功:" + balanceInfoEntity.toString());
                UserManager.getInstance().getUserEntity().setBalance(balanceInfoEntity.getBalance());
            }
        });
    }

    @Event(value = {R.id.commit, R.id.rechargeType, R.id.myBankCard})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.commit:
                commitPay();
                break;

            case R.id.rechargeType:
                ToastUtils.show(context, "充值方式");
                choice = 0;
                createDialog();
                break;

            case R.id.myBankCard:
                next(BindBankCardActivity.class);
                break;
        }
    }

    private void commitPay() {
        if (choice == 0 ) {
            ToastUtils.show(context, "微信支付");
            String title = "微盘-余额充值";
            if (TextUtils.isEmpty(rechargeMoney.getEditableText().toString().trim())){
                ToastUtils.show(context,"输入不能为空");
                return;
            }
            double price = Double.parseDouble(rechargeMoney.getEditableText().toString().trim());
            if (price <= 0) {
                ToastUtils.show(context, "输入的金额有误,请重新输入");
                return;
            }
            NetworkAPIFactoryImpl.getDealAPI().weixinPay(title, price, new OnAPIListener<WXPayReturnEntity>() {
                @Override
                public void onError(Throwable ex) {
                    ex.printStackTrace();
                    LogUtil.d("微信充值失败");
                }

                @Override
                public void onSuccess(WXPayReturnEntity wxPayReturnEntity) {
                    LogUtil.d("微信支付请求成功:" + wxPayReturnEntity.toString() + "包的参数:" + wxPayReturnEntity.getPackage());
                    PayReq request = new PayReq();
                    request.appId = wxPayReturnEntity.getAppid();
                    request.partnerId = wxPayReturnEntity.getPrepayid();
                    request.prepayId = wxPayReturnEntity.getPrepayid();
//                    request.packageValue = wxPayReturnEntity.getPackage();
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = wxPayReturnEntity.getNoncestr();
                    request.timeStamp = wxPayReturnEntity.getTimestamp();
                    request.sign = wxPayReturnEntity.getSign();
                    MyApplication.getApplication().api.sendReq(request);
                }
            });

        } else {
            showToast("银联支付");
            if (UPPayAssistEx.checkInstalled(this)) {
                //当判断用户手机上已安装银联Apk，商户客户端可以做相应个性化处理
                next(APKActivity.class);//APK接入
                LogUtil.d("已经安装了apk客户端");
            } else {
                next(JARActivity.class);//JAR接入
                LogUtil.d("没有安装apk客户端,jar接入");
            }
            //tn是交易流水号
//               UPPayAssistEx.startPay(activity, null, null, tn, mode);

            //处理支付结果
            //onActivityResult方法
        }
    }

    private void createDialog() {
        new AlertDialog.Builder(this).setTitle("选择支付方式").setIcon(
                R.mipmap.icon_rechargetype).setSingleChoiceItems(Constant.rechargeType, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                        dialog.dismiss();
                        LogUtil.d("点击的是第" + which + "个条目");
                        rechargeType.updateContentLeft(Constant.rechargeType[choice]);
                    }
                }).setNegativeButton("取消", null).show();

    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        account.updateContentLeft(UserManager.getInstance().getUserEntity().getName());
                        money.updateContentLeft(UserManager.getInstance().getUserEntity().getBalance() + "元");
//                        myBankCard.updateContentLeft("3");
//                        rechargeMoney.setEditTextString("999元");
                        rechargeType.updateContentLeft(Constant.rechargeType[choice]);
                        getRefreshController().getContentView().setVisibility(View.VISIBLE);
                        getRefreshController().refreshComplete();
                    }
                }, 200);
            }
        });
    }
}
