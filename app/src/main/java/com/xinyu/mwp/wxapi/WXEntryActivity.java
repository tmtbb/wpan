package com.xinyu.mwp.wxapi;

import android.app.Activity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xinyu.mwp.util.LogUtil;

/**
 * Created by Administrator on 2017/3/13.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.d("执行此处的回调:" + baseReq.getType());
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法  接受结果-支付结果
    @Override
    public void onResp(BaseResp baseResp) {
        int result = 0;
        LogUtil.d("baseResp回调喽");
        Toast.makeText(this, "baseresp.getType = " + baseResp.getType(), Toast.LENGTH_SHORT).show();
    }
}
