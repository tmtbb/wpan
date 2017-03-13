package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.constant.SocketAPIConstant;
import com.xinyu.mwp.entity.CurrentPositionListReturnEntity;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.entity.OpenPositionReturnEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.entity.TotalDealInfoEntity;
import com.xinyu.mwp.entity.WXPayReturnEntity;
import com.xinyu.mwp.entity.WithDrawCashReturnEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.DealAPI;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.xinyu.mwp.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketDealAPI extends SocketBaseAPI implements DealAPI {
    @Override
    public void products(OnAPIListener<List<ProductEntity>> listener) {
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
        LogUtil.d("----------------请求分时数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("exchangeName", exchangeName);
        map.put("platformName", platformName);
        map.put("symbol", symbol);
        map.put("aType", aType);  //4
//        double b = 1488363500;
//        map.put("startTime", b);  //加时间戳 历史数据
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.TimeLine,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "priceinfo", CurrentTimeLineReturnEntity.class, listener);
    }

    //当前报价
    @Override
    public void currentPrice(List<SymbolInfosEntity> symbolInfos
            , OnAPIListener<List<CurrentPriceReturnEntity>> listener) {
        LogUtil.d("----------请求当前报价数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("id", 100002);
//        map.put("token", "adc28ac69625652b46d5c00b");
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(symbolInfos.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("symbolInfos", jsonArray);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CurrentPrice,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "priceinfo", CurrentPriceReturnEntity.class, listener);
//        requestEntity(socketDataPacket, CurrentPriceReturnEntity.class, listener);
    }

    @Override
    public void kchart(String exchangeName, String platformName, String symbol, int aType, int chartType, OnAPIListener<List<CurrentTimeLineReturnEntity>> listener) {
        LogUtil.d("请求K线数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("exchangeName", exchangeName);
        map.put("platformName", platformName);
        map.put("symbol", symbol);
        map.put("aType", aType);  //4
        map.put("chartType", chartType);//K线类型	60-1分钟K线，300-5分K线，900-15分K线，1800-30分K线，3600-60分K线，5-日K线
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.KChart,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "priceinfo", CurrentTimeLineReturnEntity.class, listener);
    }


    @Override
    public void openPosition(int codeId, int buySell, double amount, int isDeferred, OnAPIListener<OpenPositionReturnEntity> listener) {
        LogUtil.d("请求建仓数据");
        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("codeId", codeId);
        map.put("buySell", buySell);  //建仓方向
        map.put("amount", amount);
        map.put("deferred", isDeferred);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Position,
                SocketAPIConstant.ReqeutType.Deal, map);
        requestEntity(socketDataPacket, OpenPositionReturnEntity.class, listener);
    }

    @Override
    public void currentPositionList(OnAPIListener<List<CurrentPositionListReturnEntity>> listener) {
        LogUtil.d("当前仓位列表请求数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("start", );
//        map.put("count", 5);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.ProductList,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "positioninfo", CurrentPositionListReturnEntity.class, listener);
    }

    @Override
    public void historyPositionList(int start, int count, OnAPIListener<List<HistoryPositionListReturnEntity>> listener) {
        LogUtil.d("仓位历史记录请求数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("start", start);
        map.put("count", count);
//        map.put("page", page);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.History,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "positioninfo", HistoryPositionListReturnEntity.class, listener);
    }

    @Override
    public void historyDealList(int start, int count, String symbol, OnAPIListener<List<HistoryPositionListReturnEntity>> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("start", start);
        map.put("count", count);
        map.put("symbol", symbol);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.History,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntitys(socketDataPacket, "positioninfo", HistoryPositionListReturnEntity.class, listener);
    }

    @Override
    public void historyPositionDetail(long positionId, OnAPIListener<HistoryPositionListReturnEntity> listener) {
        LogUtil.d("请求历史交易详情");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("positionId", positionId);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.History,
                SocketAPIConstant.ReqeutType.Time, map);
        requestEntity(socketDataPacket, HistoryPositionListReturnEntity.class, listener);
    }

    @Override
    public void totalDealInfo(OnAPIListener<TotalDealInfoEntity> listener) {
        LogUtil.d("请求交易总概况数据");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("id", 32);
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Total,
                SocketAPIConstant.ReqeutType.History, map);
        requestEntity(socketDataPacket, TotalDealInfoEntity.class, listener);
    }

    @Override
    public void weixinPay(String title, double price, OnAPIListener<WXPayReturnEntity> listener) {
        LogUtil.d("请求微信支付");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("id", 32);
        map.put("title", title);
        map.put("price", price);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.WXPay,
                SocketAPIConstant.ReqeutType.Verify, map);
        requestEntity(socketDataPacket, WXPayReturnEntity.class, listener);
    }

    @Override
    public void cash(double money, int bankId, String password, String comment, OnAPIListener<WithDrawCashReturnEntity> listener) {
        LogUtil.d("请求提现--");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("price", money);
        map.put("bankId", bankId);
        map.put("password", password);
        map.put("comment", comment);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Cash,
                SocketAPIConstant.ReqeutType.History, map);
        requestEntity(socketDataPacket, WithDrawCashReturnEntity.class, listener);
    }

    @Override
    public void cashList(String status, int startPos, int count, OnAPIListener<List<WithDrawCashReturnEntity>> listener) {
        LogUtil.d("提现列表请求网络");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("start", startPos);
        map.put("count", count);
        map.put("status", status);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CashList,
                SocketAPIConstant.ReqeutType.Verify, map);
        requestEntitys(socketDataPacket, "withdrawList", WithDrawCashReturnEntity.class, listener);
    }
}
