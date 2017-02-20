package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.constant.SocketAPIConstant;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.DealAPI;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketDealAPI  extends SocketBaseAPI implements DealAPI {
    @Override
    public void products(OnAPIListener<List<ProductEntity>> listener) {
//        {
//            "id": 100002,
//                "token": "adc28ac69625652b46d5c00b"
//            "pid":1001
//        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token",NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("pid",1002);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Products,
                SocketAPIConstant.ReqeutType.Deal,map);
        requestEntitys(socketDataPacket,"goodsinfo",ProductEntity.class,listener);
    }
}
