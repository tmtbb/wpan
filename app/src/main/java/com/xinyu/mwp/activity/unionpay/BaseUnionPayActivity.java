package com.xinyu.mwp.activity.unionpay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseActivity;

import com.xinyu.mwp.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 银联-基类
 */
public abstract class BaseUnionPayActivity extends BaseActivity implements Callback, Runnable {
    private Handler mHandler = null;
    private ProgressDialog mLoadingDialog = null;

    public static final int PLUGIN_VALID = 0;
    public static final int PLUGIN_NOT_INSTALLED = -1;  //未安装
    public static final int PLUGIN_NEED_UPGRADE = 2; //需要升级

    /**
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
    */
    private final String mMode = "01";

    //模拟的从商户获取的tn    需要修改为商户的tn
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";

    public abstract void doStartUnionPayPlugin(Activity activity, String tn, String mode);

    @Override
    protected int getContentView() {
        return R.layout.activity_union_pay;
    }

    @Override
    protected void initView() {
        super.initView();
        mHandler = new Handler(this);
        //setTitle("订单支付");
        mLoadingDialog = ProgressDialog.show(this, // context
                "", // title
                "正在努力的获取tn中,请稍候...", // message
                true); // 进度是否是不确定的，这只和创建进度条有关

        /** 步骤1：从网络开始,获取交易流水号即TN*/
        new Thread(BaseUnionPayActivity.this).start();  //开启子线程,run中执行操作
    }

    @Override
    protected void initListener() {
        super.initListener();

    }


    @Override
    public boolean handleMessage(Message msg) {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.create().show();
        } else {
            tn = (String) msg.obj;
            /**步骤2：通过银联工具类启动支付插件*/
            doStartUnionPayPlugin(this, tn, mMode);
        }
        return false;
    }

    @Override
    public void run() {
        String tn = null;
        InputStream is;
        try {

            String url = TN_URL_01;
            URL myURL = new URL(url);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            tn = baos.toString();
            LogUtil.d("获取的tn是:" + tn);
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);   //发送handler ,传递msg
    }

    private int startpay(Activity act, String tn, int serverIdentifier) {
        return 0;
    }

    /**
     * 步骤3：处理银联手机支付控件返回的支付结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, mMode);
                    if (ret) {
                        // 验证通过后，显示支付结果
                        msg = "支付成功！";
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "支付成功！";
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签,根据验签的结果来返回boolean值
        return true;

    }
}
