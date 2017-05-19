package com.xinyu.mwp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.unionpay.UPPayAssistEx;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshActivity;
import com.xinyu.mwp.activity.unionpay.APKActivity;
import com.xinyu.mwp.activity.unionpay.JARActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.entity.UnionPayReturnEntity;
import com.xinyu.mwp.entity.WXPayReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.ErrorCodeUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.view.CellView;
import com.xinyu.mwp.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    @ViewInject(R.id.iv_bannerview)
    private ImageView bannerView;
    @ViewInject(R.id.tv_warm_tip)
    private TextView warmTip;
    private int choice = 0;
    private WXPayReturnEntity wxPayEntity;
    private String payType = Constant.payType.WECHAT_QRCODE_PAY; //默认微信扫码
    private boolean flag = true;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("充值");
//        requestBalance();
        rightText.setText("充值记录");
        rightText.setVisibility(View.VISIBLE);
        Utils.closeSoftKeyboard(rechargeMoney);
        rechargeMoney.setInputType(InputType.TYPE_CLASS_NUMBER);

        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(getResources().getString(R.string.cash_service_charge));
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(getResources().getColor(R.color.red));
        ssbuilder.setSpan(yellowSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        warmTip.setText(ssbuilder);

        if (flag) {
            EventBus.getDefault().register(this);
            flag = false;
        }
    }

    @Event(value = {R.id.commit, R.id.rechargeType, R.id.myBankCard, R.id.rightText})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.commit:
                commitPay();
                break;

            case R.id.rechargeType:
//                choice = 0;
                createDialog();
                break;

            case R.id.myBankCard:
                next(BindBankCardActivity.class);
                break;

            case R.id.rightText:
                next(RechargeRecordActivity.class);
                break;
        }
    }

    private void commitPay() {
        String title = "微盘-余额充值";
        if (TextUtils.isEmpty(rechargeMoney.getEditableText().toString().trim())) {
            ToastUtils.show(context, "输入不能为空");
            return;
        }
        double price = Double.parseDouble(rechargeMoney.getEditableText().toString().trim());
        if (price < 100) {   //换成100
            ToastUtils.show(context, "充值金额必须大于等于100元");
            return;
        }
        if (price > 50000) {
            ToastUtils.show(context, "单笔充值金额最高为5万");
            return;
        }

        if (choice == 0) {
            payType = Constant.payType.WECHAT_QRCODE_PAY;
//            requestWXPay(title, price);
        } else {
            //requestUnionPay(title, price);
            payType = Constant.payType.ALIPAY_QRCODE_PAY;
        }
        requestPayMent((long) price);  //第三方 支付
    }

    /**
     * 请求微信支付
     *
     * @param title title
     * @param price 金额
     */
    private void requestWXPay(String title, double price) {
        NetworkAPIFactoryImpl.getDealAPI().weixinPay(title, price, new OnAPIListener<WXPayReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(WXPayReturnEntity wxPayReturnEntity) {
                wxPayEntity = wxPayReturnEntity;
                PayReq request = new PayReq();
                request.appId = wxPayReturnEntity.getAppid();
                request.partnerId = wxPayReturnEntity.getPartnerid();
                request.prepayId = wxPayReturnEntity.getPrepayid();
//                    request.packageValue = wxPayReturnEntity.getPackage();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = wxPayReturnEntity.getNoncestr();
                request.timeStamp = wxPayReturnEntity.getTimestamp();
                request.sign = wxPayReturnEntity.getSign();
                MyApplication.api.sendReq(request);
            }
        });
    }

    /**
     * 请求第三方支付
     *
     * @param price 金额
     */
    private void requestPayMent(long price) {
        String outTradeNo = ""; //订单号
        final String content = "";  //描述
        showLoader("正在处理...");
        NetworkAPIFactoryImpl.getDealAPI().payment(outTradeNo, price, content, payType,
                new OnAPIListener<UnionPayReturnEntity>() {
                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                        LogUtil.d("调用第三方失败");
                        closeLoader();
                        ErrorCodeUtil.showEeorMsg(context, ex);
                    }

                    @Override
                    public void onSuccess(UnionPayReturnEntity unionPayReturnEntity) {
//                Intent intent = new Intent(context, PayMentActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("payment", unionPayReturnEntity);
//                intent.putExtra("pay", bundle);
//                startActivity(intent);
                        closeLoader();
                        unionPayReturnEntity.setPayType(payType);
                        LogUtil.d("模拟进入二维码界面");
                        Intent intent = new Intent(context, PayORCodeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("payment", unionPayReturnEntity);
                        intent.putExtra("pay", bundle);
                        startActivity(intent);
                    }
                });
    }

    /**
     * 请求银联支付
     *
     * @param title title
     * @param price 金额
     */
    private void requestUnionPay(String title, double price) {
        NetworkAPIFactoryImpl.getDealAPI().unionPay(title, price, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("银联支付请求失败");
                ErrorCodeUtil.showEeorMsg(context, ex);
            }

            @Override
            public void onSuccess(Object o) {
                LogUtil.d("银联支付请求成功" + o.toString());
            }
        });
        if (UPPayAssistEx.checkInstalled(this)) {
            //当判断用户手机上已安装银联Apk，商户客户端可以做相应个性化处理
            next(APKActivity.class);//APK接入
            LogUtil.d("已经安装了apk客户端");
        } else {
            next(JARActivity.class);//JAR接入
            LogUtil.d("没有安装apk客户端,jar接入");
        }
        //tn是交易流水号
//     UPPayAssistEx.startPay(activity, null, null, tn, mode);
        //处理支付结果
        //onActivityResult方法
    }


    private void createDialog() {
        new AlertDialog.Builder(this).setTitle("选择支付方式").setSingleChoiceItems(Constant.rechargeType, choice,
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
                        if (UserManager.getInstance().getUserEntity() != null) {
                            LogUtil.d("充值界面当前的userInfo:"+UserManager.getInstance().getUserEntity());
                            account.updateContentLeft(UserManager.getInstance().getUserEntity().getMobile());
                            money.updateContentLeft(NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()) + "元");
                        }
                        rechargeType.updateContentLeft(Constant.rechargeType[choice]);
                        getRefreshController().getContentView().setVisibility(View.VISIBLE);
                        getRefreshController().refreshComplete();
                    }
                }, 200);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TestDataUtil.requestBalance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserManager.getInstance().getUserEntity() != null) {
                    money.updateContentLeft(NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()) + "元"); //更新余额
                }
            }
        }, 500);
    }

    /**
     * EventBus接收消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case 0:  //成功
                ToastUtils.show(context, "支付成功");  //1-成功 2-取消支付
                LogUtil.d("接收到成功是0");
                TestDataUtil.requestBalance();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (UserManager.getInstance().getUserEntity() != null) {
                            money.updateContentLeft(NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()) + "元");
                        }
                    }
                }, 500);
                next(RechargeRecordActivity.class);
                break;
            case -2:  //取消支付
                ToastUtils.show(context, "用户取消支付");
                LogUtil.d("接收到取消支付是-2");
                break;

            case -10:  //取消支付
                createCancelPayDialog();
                LogUtil.d("接收到取消支付是-10");
                break;
        }
    }

    /**
     * 取消支付的弹窗
     */
    private void createCancelPayDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(context, Constant.TYPE_INSUFFICIENT_BALANCE);
        CustomDialog customDialog = builder.setTitle(getResources().getString(R.string.pay_state))
                .setMessage(getResources().getString(R.string.cancel_pay_msg))
                .setPositiveButton(getResources().getString(R.string.pay_complete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        next(RechargeRecordActivity.class);
                    }
                }).setNegativeButton(getResources().getString(R.string.pay_problem), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create();
        customDialog.setOnKeyListener(keylistener);
        customDialog.show();
    }

    private DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    @Override
    protected void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
