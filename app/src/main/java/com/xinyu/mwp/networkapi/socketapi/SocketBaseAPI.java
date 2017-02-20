package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.entity.BaseEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPIRequestManage;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPIResponse;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.xinyu.mwp.util.JSONEntityUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketBaseAPI {

    /**
     * socket请求返回 JSONObject
     * @param socketDataPacket
     * @param listener
     */
    public void requestJsonObject(SocketDataPacket socketDataPacket, final OnAPIListener listener) {
        SocketAPIRequestManage.getInstance().startJsonRequest(socketDataPacket, new OnAPIListener<SocketAPIResponse>() {
            @Override
            public void onError(Throwable ex) {
                if( listener != null ) {
                    listener.onError(ex);
                }
            }

            @Override
            public void onSuccess(SocketAPIResponse socketAPIResponse) {
                if( listener != null ) {
                    listener.onSuccess(socketAPIResponse.jsonObject());
                }
            }
        });
    }

    /**
     * socket请求返回 cls Entity
     * @param socketDataPacket
     * @param cls
     * @param listener
     */
    public void requestEntity(SocketDataPacket socketDataPacket, final Class<? extends BaseEntity> cls, final OnAPIListener listener) {
        SocketAPIRequestManage.getInstance().startJsonRequest(socketDataPacket, new OnAPIListener<SocketAPIResponse>() {
            @Override
            public void onError(Throwable ex) {
                if( listener != null ) {
                    listener.onError(ex);
                }
            }

            @Override
            public void onSuccess(SocketAPIResponse socketAPIResponse) {
                if( listener != null ) {
                    Object object = JSONEntityUtil.JSONToEntity(cls,socketAPIResponse.jsonObject());
                    listener.onSuccess(object);
                }
            }
        });
    }

    /**
     * socket请求返回 List<cls Entity?
     * @param socketDataPacket
     * @param listName 列表字段名
     * @param cls
     * @param listener
     */
    public void requestEntitys(SocketDataPacket socketDataPacket, final String listName, final Class<? extends BaseEntity> cls, final OnAPIListener listener) {
        SocketAPIRequestManage.getInstance().startJsonRequest(socketDataPacket, new OnAPIListener<SocketAPIResponse>() {
            @Override
            public void onError(Throwable ex) {
                if( listener != null ) {
                    listener.onError(ex);
                }
            }

            @Override
            public void onSuccess(SocketAPIResponse socketAPIResponse) {
                if( listener != null ) {
                    try {
                        JSONArray jsonArray = socketAPIResponse.jsonObject().getJSONArray(listName);
                        List<BaseEntity> list = (List<BaseEntity>) JSONEntityUtil.JSONToEntitys(cls,jsonArray);
                        listener.onSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onError(e);
                    }
                }
            }
        });
    }
}
