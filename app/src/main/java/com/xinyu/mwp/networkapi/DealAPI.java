package com.xinyu.mwp.networkapi;

import com.xinyu.mwp.entity.CurrentPositionListReturnEntity;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.HistoryPositionEntity;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.entity.OpenPositionReturnEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.listener.OnAPIListener;

import java.util.List;

/**
 * Created by yaowang on 2017/2/20.
 * 交易 行情相关接口
 */

public interface DealAPI {
    void products(OnAPIListener<List<ProductEntity>> listener);

    //当前分时数据
    void timeline(String exchangeName, String platformName, String symbol, int aType, OnAPIListener<List<CurrentTimeLineReturnEntity>> listener);
//    void timeline(String exchangeName, String platformName, String symbol, int aType, OnAPIListener<CurrentTimeLineReturnEntity> listener);

    //当前报价
    void currentPrice(List<SymbolInfosEntity> symbolInfos, OnAPIListener<List<CurrentPriceReturnEntity>> listener);

    //加载Kchart
    void kchart(String exchangeName, String platformName, String symbol, int chartType, OnAPIListener<List<CurrentTimeLineReturnEntity>> listener);

    //建仓
    void openPosition(int codeId, int buySell, double amount, int isDeferred, OnAPIListener<OpenPositionReturnEntity> listener);

    //当前仓位列表
    void currentPositionList(OnAPIListener<List<CurrentPositionListReturnEntity>> listener);

    //当前仓位列表
    void historyPositionList(int start, int count, OnAPIListener<List<HistoryPositionListReturnEntity>> listener);
}
