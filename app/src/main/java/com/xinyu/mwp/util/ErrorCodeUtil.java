package com.xinyu.mwp.util;

import android.content.Context;
import android.text.TextUtils;

import com.xinyu.mwp.networkapi.NetworkAPIException;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;

/**
 * Created by Administrator on 2017/4/13.
 */
public class ErrorCodeUtil {
    /**
     * 弹出错误信息
     */
    public static void showEeorMsg(Context context, Throwable ex) {
        if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
            ToastUtils.show(context, "网络连接失败,请检查网络");
            return;
        }

        String msg = "";
        switch (((NetworkAPIException) ex).getErrorCode()) {
            case -11011:
                msg = "当前网络不畅，请稍候再试";
                break;
            case -1:
                msg = "格式有误";
                break;
            case -2:
                msg = "解密异常";
                break;
            case -3:
                msg = "token错误或者不存在";
                break;
            case -4:
                msg = "sql执行错误";
                break;
            case -5:
                msg = "验证码已过期";
                break;
            case -6:
                msg = "验证码错误";
                break;
            case -101:
                msg = "没有此用户";
                break;
            case -102:
                msg = "微信下载失败";
                break;
            case -103:
                msg = "充值列表状态参数错误";
                break;
            case -301:
                msg = "当前手机号码已经被注册";
                break;
            case -302:
                msg = "账户或密码错误，请重新填写";
                break;
            case -303:
                msg = "当前手机号码已被注册";
                break;
            case -304:
                msg = "该用户存在";
                break;
            case -305:
                msg = "手机号或密码错误";
                break;
            case -306:
                msg = "会员ID不能为空";
                break;
            case -401:
                msg = "没有对应的报价";
                break;
            case -402:
                msg = "没有对应的行情数据";
                break;
            case -403:
                msg = "没有对应的K线数据";
                break;
            case -501:
                msg = "平台没有交易的商品";
                break;
            case -502:
                msg = "当前交易的商品不存在";
                break;
            case -503:
                msg = "当前无持仓数据";
                break;
            case -504:
                msg = "扣费失败";
                break;
            case -505:
                msg = "没有商品数据";
                break;
            case -506:
                msg = "持仓记录大于5";
                break;
            case -601:
                msg = "没有相关交易的历史数据";
                break;
            case -602:
                msg = " 处理交易结果失败";
                break;
            case -701:
                msg = "微信下单失败";
                break;
            case -702:
                msg = "存储订单失败";
                break;
            case -703:
                msg = "充值失败,请稍后再试";
                break;
            case -704:
                msg = "提现失败,请稍后再试";
                break;
            case -801:
                msg = "当前没有银行卡";
                break;
            case -802:
                msg = "绑定银行卡错误";
                break;
            case -803:
                msg = "此银行卡不存在";
                break;
            case -804:
                msg = "解绑银行卡失败";
                break;
            default:
                msg = "连接超时,请稍后重试";
                break;
        }
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.show(context, msg);
        }
    }
}
