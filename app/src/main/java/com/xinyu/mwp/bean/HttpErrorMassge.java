package com.xinyu.mwp.bean;

/**
 * Created by Administrator on 2016/7/12.
 */
public class HttpErrorMassge {
    public static final String ABHORMAL_SERVER_ERROR = "服务器又调皮了哦！...";//500以上
    public static final String ABHORMAL_TIME_OUT = "当前网络不可用，请检查网络";//连接超时
    public static final String ABHORMAL_LINKE_ERROR = "找不到数据了哦！...";//400以上
    public static final String ABHORMAL_LINKE_REDIRECT = "数据跑了哦！...";//300以上
    public static final String ABHORMAL_REQUEST_ERROR = "请求方式错误！...";//405以上
    public static final String ABHORMAL_JSON_ERROR = "解析数据失败";//解析错
}
