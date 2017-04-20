package com.xinyu.mwp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshActivity;
import com.xinyu.mwp.activity.unionpay.APKActivity;
import com.xinyu.mwp.activity.unionpay.JARActivity;

import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.BalanceInfoEntity;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.entity.WXPayResultEntity;
import com.xinyu.mwp.entity.WXPayReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.listener.OnSuccessListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyHandler;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPIResponse;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.util.Utils;
import com.xinyu.mwp.view.CellView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import in.srain.cube.views.ptr.PtrFrameLayout;
import io.netty.channel.ChannelHandlerContext;

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
    private int choice = 0;
    private IWXAPI api;
    private WXPayReturnEntity wxPayEntity;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("充值");
//        bannerView.centerDot();
//        bannerView.setRefreshLayout(refreshFrameLayout);
//        bannerView.update(TestDataUtil.getIndexBanners(3));
//        requestBalance();
        rightText.setText("充值记录");
        rightText.setVisibility(View.VISIBLE);
        Utils.closeSoftKeyboard(rechargeMoney);
        api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(Constant.APP_ID);

        if (flag) {
            EventBus.getDefault().register(this); // EventBus注册广播()
            flag = false;//更改标记,使其不会再进行多次注册
        }
    }

    @Event(value = {R.id.commit, R.id.rechargeType, R.id.myBankCard, R.id.rightText})
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

            case R.id.rightText:
                next(RechargeRecordActivity.class);
                break;
        }
    }

    private void commitPay() {
        if (choice == 0) {
            ToastUtils.show(context, "微信支付");
            String title = "微盘-余额充值";
            if (TextUtils.isEmpty(rechargeMoney.getEditableText().toString().trim())) {
                ToastUtils.show(context, "输入不能为空");
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
                }

                @Override
                public void onSuccess(WXPayReturnEntity wxPayReturnEntity) {
//                    LogUtil.d("微信支付成功" + wxPayReturnEntity.toString());
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
                    api.sendReq(request);

                    //模拟请求回调
//                    requestResult();
                    next(RechargeRecordActivity.class);
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

    private void requestResult() {
        NetworkAPIFactoryImpl.getDealAPI().wxpayResult(wxPayEntity.getRid(), 1, new OnAPIListener<WXPayResultEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("接收支付回调失败了");
            }

            @Override
            public void onSuccess(WXPayResultEntity wxPayResultEntity) {
                LogUtil.d("接收到了支付成功的消息:" + wxPayResultEntity.toString());

            }
        });
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
                        account.updateContentLeft(UserManager.getInstance().getUserEntity().getMobile());
                        money.updateContentLeft(NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()) + "元");
//                        myBankCard.updateContentLeft("3");
//                        rechargeMoney.setEditTextString("999元");
                        rechargeType.updateContentLeft(Constant.rechargeType[choice]);
                        getRefreshController().getContentView().setVisibility(View.VISIBLE);
                        getRefreshController().refreshComplete();
                    }
                }, 200);
            }
        });

        OnSuccessListener listener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
//                ToastUtils.show(context, "接收到成功的信息" + o.toString());
                LogUtil.d("接收到成功的信息" + o.toString());
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        TestDataUtil.requestBalance();
    }

    private boolean flag = true;

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case 0:  //成功
                ToastUtils.show(context, "支付成功");  //1-成功 2-取消支付
                LogUtil.d("接收到成功是0");
                break;
            case -2:  //取消支付
                ToastUtils.show(context, "用户取消支付");
                LogUtil.d("接收到取消支付是-2");
                break;
        }
    }

    private SocketAPINettyHandler handler = new SocketAPINettyHandler() {
        @Override
        protected void messageReceived(ChannelHandlerContext ctx, SocketDataPacket socketDataPacket) throws Exception {
            super.messageReceived(ctx, socketDataPacket);
            ToastUtils.show(context, "接收到了message的消息了");
            LogUtil.d("接收到了message的消息了");
            if (socketDataPacket != null) {

                SocketAPIResponse socketAPIResponse = new SocketAPIResponse(socketDataPacket);
                int statusCode = socketAPIResponse.statusCode();
                if (statusCode == 0) {
//                        socketAPIRequest.onSuccess(socketAPIResponse);
                    LogUtil.d("jsonResponse:" + socketAPIResponse.jsonObject());
                    ToastUtils.show(context, "接收到了消息ssss:" + socketAPIResponse.jsonObject());
                }
//                    else {
////                        socketAPIRequest.onErrorCode(statusCode);
//                    }
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
