package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.constant.SocketAPIConstant;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.UserAPI;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketUserAPI extends SocketBaseAPI implements UserAPI {
    @Override
    public void login(String phone, String password, OnAPIListener<LoginReturnEntity> listener) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",password);
        map.put("source",2);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Login,
                    SocketAPIConstant.ReqeutType.User,map);
        requestEntity(socketDataPacket,LoginReturnEntity.class,listener);
    }
}
