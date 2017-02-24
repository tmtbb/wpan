package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.constant.SocketAPIConstant;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.DealAPI;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.xinyu.mwp.util.LogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketDealAPI extends SocketBaseAPI implements DealAPI {
    @Override
    public void products(OnAPIListener<List<ProductEntity>> listener) {
//        {
//            "id": 100002,
//                "token": "adc28ac69625652b46d5c00b"
//            "pid":1001
//        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        LogUtil.d("商品列表请求数据--------");
        map.put("pid", 1002);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Products,
                SocketAPIConstant.ReqeutType.Deal, map);
        requestEntitys(socketDataPacket, "goodsinfo", ProductEntity.class, listener);
    }

    //分时数据
    @Override
    public void timeline(String exchangeName, String platformName, String symbol, int aType,
                         OnAPIListener<List<CurrentTimeLineReturnEntity>> listener) {
        LogUtil.d("请求分时数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("exchangeName", exchangeName);
        map.put("platformName", platformName);
        map.put("symbol", symbol);
        map.put("aType", aType);  //2-现货
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.TimeLine,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "priceinfo", CurrentTimeLineReturnEntity.class, listener);
    }

    //当前报价
    @Override
    public void currentPrice(List<SymbolInfosEntity> symbolInfos
            , OnAPIListener<List<CurrentPriceReturnEntity>> listener) {
        LogUtil.d("请求当前报价数据"+"此时的symbolInfos是"+symbolInfos.toString());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("symbolInfos", symbolInfos);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CurrentPrice,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket,"priceinfo",CurrentPriceReturnEntity.class,listener);
    }

    @Override
    public void kchart(String exchangeName, String platformName, String symbol, int chartType, OnAPIListener<List<CurrentTimeLineReturnEntity>> listener) {
        LogUtil.d("请求K线数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("exchangeName", exchangeName);
        map.put("platformName", platformName);
        map.put("symbol", symbol);
        map.put("chartType", chartType);//K线类型	60-1分钟K线，300-5分K线，900-15分K线，1800-30分K线，3600-60分K线，5-日K线
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.KChart,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "priceinfo", CurrentTimeLineReturnEntity.class, listener);
    }

}
