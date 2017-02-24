package com.xinyu.mwp.networkapi.socketapi.SocketReqeust;

import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIException;
import com.xinyu.mwp.networkapi.socketapi.SocketAPIFactoryImpl;
import com.xinyu.mwp.util.LogUtil;

import java.util.Date;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketAPIRequest {
    private long timestamp = new Date().getTime();
    private OnAPIListener<SocketAPIResponse> listener;

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isReqeustTimeout() {
        return  (new Date().getTime() -  timestamp) > SocketAPIFactoryImpl.getInstance().getConfig().getSocketTimeout();
    }


    public OnAPIListener<SocketAPIResponse> getListener() {
        return listener;
    }

    public void setListener(OnAPIListener<SocketAPIResponse> listener) {
        this.listener = listener;
    }

    public void onSuccess(SocketAPIResponse socketAPIResponse) {
        if( listener != null ) {
            listener.onSuccess(socketAPIResponse);
        }
    }

    public void  onError(Throwable ex) {
        if( listener != null ) {
            listener.onError(ex);
        }
    }


    public void  onErrorCode(int errorCode) {
        LogUtil.d("-------------------------errorCode:"+errorCode);
        onError(new NetworkAPIException(errorCode,"error"));
    }

}
