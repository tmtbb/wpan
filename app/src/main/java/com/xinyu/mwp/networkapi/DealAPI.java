package com.xinyu.mwp.networkapi;

import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.listener.OnAPIListener;

import java.util.List;

/**
 * Created by yaowang on 2017/2/20.
 * 交易 行情相关接口
 */

public interface DealAPI {
    void products(OnAPIListener<List<ProductEntity>> listener);
}
