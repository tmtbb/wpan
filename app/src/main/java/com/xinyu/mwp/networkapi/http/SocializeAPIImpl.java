package com.xinyu.mwp.networkapi.http;


import com.xinyu.mwp.constant.SocializeConstant;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIConstant;
import com.xinyu.mwp.networkapi.SocializeAPI;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-12-14 11:02
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class SocializeAPIImpl extends XUtilsHttpReqeustImpl implements SocializeAPI {


    @Override
    public void getWxUserInfo(String token, String openId, OnAPIListener<JSONObject> listener) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", token);
        map.put("openid", openId);
        getRequest(NetworkAPIConstant.Socialize.USER_INFO, map, listener);
    }

    @Override
    public void getWxToken(String code, OnAPIListener<JSONObject> listener) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appid", SocializeConstant.WEIXIN_APP_KEY);
        map.put("secret", SocializeConstant.WEIXIN_APP_SECRET);
        map.put("code", code);
        map.put("grant_type", "authorization_code");
        getRequest(NetworkAPIConstant.Socialize.TOKEN, map, listener);
    }

}
