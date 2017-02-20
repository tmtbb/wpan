package com.xinyu.mwp.constant;

/**
 * Created by yaowang on 2017/2/20.
 */

public interface SocketAPIConstant {

    interface ReqeutType {
        byte Error  = 0;
        byte User   = 3;
        byte Time   = 4;
        byte Deal   = 5;
    }

    interface OperateCode {
        Short Login = 3003;


        Short Products = 5001;
    }
}
