package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.constant.SocketAPIConstant;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.UserAPI;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketUserAPI extends SocketBaseAPI implements UserAPI {
    @Override
    public void login(String phone, String password, OnAPIListener<LoginReturnEntity> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("pwd",password);
            jsonObject.put("source",2);
            SocketDataPacket socketDataPacket = new SocketDataPacket(SocketAPIConstant.OperateCode.Login,
                    SocketAPIConstant.ReqeutType.User,jsonObject);
            requestEntity(socketDataPacket,LoginReturnEntity.class,listener);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
