package com.xinyu.mwp.util;


import com.xinyu.mwp.R;

/**
 * 获取银行卡信息,图标,名称,
 * Created by Administrator on 2017/4/10.
 */
public class BankInfoUtil {
    public static int getIcon(String bank) {
        int iconId;
        switch (bank) {
            case "中国建设银行":
                iconId = R.mipmap.icon_bank_ccb;
                break;
            case "中国农业银行":
                iconId = R.mipmap.icon_bank_abc;
                break;
            case "中国工商银行":
                iconId = R.mipmap.icon_bank_boc;
                break;
            case "招商银行":
                iconId = R.mipmap.icon_bank_cmb;
                break;
            default:
                iconId = R.mipmap.icon_bank_union_pay;
                break;
        }
        return iconId;
    }


}
