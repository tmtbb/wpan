package com.xinyu.mwp.constant;

public interface Constant {

    String[] rechargeType = new String[]{"微信支付", "银联支付"};
    String USER_ENTITY = "user_entity";

    interface IntentKey {
        String CHOOSE_IMGS_SCAN = "CHOOSE_IMGS_SCAN";
        String CHOOSE_IMGS_RES = "CHOOSE_IMGS_RES";
        String IMGS_BIG = "IMGS_BIG";
        String IMGS_LIST = "IMGS_LIST";
        String IMGS_POSITION = "IMGS_POSITION";
    }

    int TYPE_BUY_MINUS = 0; //买跌
    int TYPE_BUY_PLUS = 1; //买跌
}
