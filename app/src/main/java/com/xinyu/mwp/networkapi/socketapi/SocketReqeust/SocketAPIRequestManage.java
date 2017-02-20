package com.xinyu.mwp.networkapi.socketapi.SocketReqeust;


import com.xinyu.mwp.listener.OnAPIListener;

import java.util.HashMap;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketAPIRequestManage {

    private static SocketAPIRequestManage socketAPIRequestManage = null;
    private HashMap<Long, SocketAPIRequest> socketAPIRequestHashMap = new HashMap<Long, SocketAPIRequest>();
    private long sessionId = 10000;

    private synchronized long getSessionId() {
        if (sessionId > 2000000000) {
            sessionId = 10000;
        }
        sessionId += 1;
        return sessionId;
    }

    public static synchronized SocketAPIRequestManage getInstance() {
        if (socketAPIRequestManage == null) {
            socketAPIRequestManage = new SocketAPIRequestManage();
        }
        return socketAPIRequestManage;
    }

    public synchronized void  notifyResponsePacket(SocketDataPacket socketDataPacket) {
        if (socketDataPacket != null) {
            SocketAPIRequest socketAPIRequest = socketAPIRequestHashMap.get(socketDataPacket.getSessionId());
            if( socketAPIRequest != null && socketAPIRequest.getListener() != null ) {
                socketAPIRequestHashMap.remove(socketDataPacket.getSessionId());
                SocketAPIResponse socketAPIResponse = new SocketAPIResponse(socketDataPacket);
                int statusCode = socketAPIResponse.statusCode();
                if( statusCode == 0 ) {
                    socketAPIRequest.onSuccess(socketAPIResponse);
                }
                else {
                    socketAPIRequest.onErrorCode(statusCode);
                }
            }
        }
    }

    public void startJsonRequest(SocketDataPacket socketDataPacket, OnAPIListener<SocketAPIResponse> listener) {
        if (socketDataPacket != null && listener != null) {
            SocketAPIRequest socketAPIRequest = new SocketAPIRequest();
            socketAPIRequest.setListener(listener);
            long sessionId = getSessionId();
            socketDataPacket.setSessionId(sessionId);
            socketDataPacket.setTimestamp((int)(socketAPIRequest.getTimestamp()/1000));
            socketAPIRequestHashMap.put(sessionId, socketAPIRequest);
            SocketAPINettyBootstrap.getInstance().writeAndFlush(socketDataPacket);
        }
    }
}
