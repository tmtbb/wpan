package com.xinyu.mwp.constant;

/**
 * Created by yaowang on 2017/2/20.
 */

public interface SocketAPIConstant {

    interface ReqeutType {
        byte Error = 0;
        byte User = 3;
        byte Time = 4;
        byte Deal = 5;
        byte Verify = 1;
        byte History = 6;
    }

    interface OperateCode {
        Short Login = 3003;
        Short Products = 5001;
        Short VerifyCode = 1029;
        Short Register = 3001;
        Short DealPwd = 3005;
        Short Test = 3000;
        Short ProductList = 5005;
        Short TimeLine = 4003;
        Short CurrentPrice = 4001;
        Short KChart = 4005;
        Short Position = 5003;
        Short History = 6001;
        Short Balance = 3007;
        Short Total = 6003;
        Short WXPay = 7033;
        Short Cash = 6011;
        Short CashList = 6005;
    }
}
