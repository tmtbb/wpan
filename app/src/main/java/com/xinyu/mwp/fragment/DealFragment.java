package com.xinyu.mwp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.RechargeActivity;
import com.xinyu.mwp.adapter.GalleryAdapter;

import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.fragment.base.BaseFragment;

import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.http.NetworkHttpAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.LongFunction;

/**
 * 交易界面
 * Created by Benjamin on 17/1/10.
 */

public class DealFragment extends BaseFragment {

    @ViewInject(R.id.leftImage)
    private ImageButton leftImage; //左侧返回按钮

    @ViewInject(R.id.titleText)
    private TextView titleText; //正标题
    @ViewInject(R.id.tv_exchange_assets)
    private TextView tvExchangeAssets; //总资产数目
    @ViewInject(R.id.iv_exchange_assets_add)
    private ImageView ivAssetsAdd; //加号按钮

    @ViewInject(R.id.container_chart)
    FrameLayout containerChart;

    @ViewInject(R.id.rv_title)
    RecyclerView mRecyclerView;


    private List<String> mTitleList;

    private List<Fragment> fragmentList = new ArrayList<>();
    private GalleryAdapter mAdapter;
    private FragmentTransaction fragmentTransaction;
    private List<ProductEntity> productList;
    private DealProductPageFragment dealProductPageFragment;
    private List<List<ProductEntity>> products;
    private List<ProductEntity> productUnitList;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_deal;
    }

    @Override
    protected void initView() {
        super.initView();
        leftImage.setVisibility(View.INVISIBLE);
        titleText.setText("交易");
        reuqestData();

    }

    /**
     * 网络请求数据
     */
    private void reuqestData() {
        mTitleList = new ArrayList<>();
        LogUtil.d("此时网络的连接状态是:" + SocketAPINettyBootstrap.getInstance().isOpen());
        NetworkAPIFactoryImpl.getDealAPI().products(new OnAPIListener<List<ProductEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(List<ProductEntity> productEntities) {
                productList = productEntities;
                LogUtil.d("请求成功的信息是:" + productEntities.toString());
                processData();
            }
        });

    }

    private void processData() {
        HashSet<String> setList = new HashSet<>();
        for (ProductEntity productEntity : productList) {
            setList.add(productEntity.getShowSymbol());
        }
        LogUtil.d("SET集合长度:" + setList.size());

        List<ProductEntity> newProducts = null;
        products = new ArrayList<List<ProductEntity>>();
        for (String s : setList) {
            newProducts = new ArrayList<>();
            for (ProductEntity productEntity : productList) {
                if (productEntity.getShowSymbol().equals(s)) {
                    newProducts.add(productEntity);
                }
            }
            products.add(newProducts);
            mTitleList.add(s);
        }
        LogUtil.d("products长度是" + products.size());
        LogUtil.d("打印集合" + mTitleList.toString());

        //加载标题
        initProductName();

        //加载K线图等布局
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        dealProductPageFragment = DealProductPageFragment.newInstance(products);
        fragmentTransaction.add(dealProductPageFragment, "sss");
        fragmentTransaction.commit();
        fragmentTransaction.replace(R.id.container_chart, dealProductPageFragment);
    }

    private void initProductName() {
        if (mTitleList == null) {
            return;
        }

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置
        mAdapter = new GalleryAdapter(context, mTitleList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClick(new GalleryAdapter.OnClickListener() {
            @Override
            public void OnClick(View view, int position) {

                //通多点击的位置,获取不同unit的集合
                productUnitList = products.get(position);
                LogUtil.d("点击的位置是:" + position + "当前unit的集合长度是:" + productUnitList.size());

                ToastUtils.show(context, mTitleList.get(position) + "刷新数据");
                //调用方法,传递数据到dealProductPageFragment界面,更新数据
              //  dealProductPageFragment.setData(position);

              //  dealProductPageFragment.loadChartData(); //刷新数据
                //dealProductPageFragment.createAdapter().notifyDataSetChanged(); //刷新持仓

                mAdapter.setPosition(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean OnLongClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                return false;
            }
        });
    }

    @Event(value = {R.id.iv_exchange_assets_add, R.id.tv_exchange_assets})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.iv_exchange_assets_add:
                showToast("充值");
                // next(RechargeActivity.class);
                currentProductPrice();
                break;
            case R.id.tv_exchange_assets:
                showToast("个人资产");
                //  next(UserAssetsActivity.class);
                currentPositionList();
                break;

            default:
                break;
        }
    }

    //请求报价数据
    private void currentProductPrice() {
        LogUtil.d("充值变为获取当前分时数据");

//        List<SymbolInfosEntity> newList = new ArrayList<>();
//        List<ProductEntity> productEntities1 = products.get(0);
//        for (int i = 0; i < productEntities1.size(); i++) {
//
//        }
//        for (ProductEntity productEntity : productEntities1) {
//            SymbolInfosEntity en = new SymbolInfosEntity();
//            en.setExchangeName(productEntity.getExchangeName());
//            en.setPlatformName(productEntity.getPlatformName());
//            en.setSymbol(productEntity.getSymbol());
//            en.setAType(2);
//            newList.add(en);
//        }
//        LogUtil.d("newList的长度是:" + newList.size());
        List<SymbolInfosEntity> newList2 = new ArrayList<>();
        SymbolInfosEntity en2 = new SymbolInfosEntity();
        en2.setExchangeName("TJPME");
        en2.setPlatformName("JH");
        en2.setSymbol("AG");
        en2.setAType(2);
        newList2.add(en2);
        SymbolInfosEntity en3 = new SymbolInfosEntity();
        en3.setExchangeName("TJPME");
        en3.setPlatformName("JH");
        en3.setSymbol("AG");
        en3.setAType(2);
        newList2.add(en3);

        LogUtil.d("此时网络的连接状态是:" + SocketAPINettyBootstrap.getInstance().isOpen());
        NetworkAPIFactoryImpl.getDealAPI().currentPrice(newList2, new OnAPIListener<List<CurrentPriceReturnEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("请求网络错误");
            }

            @Override
            public void onSuccess(List<CurrentPriceReturnEntity> currentPriceReturnEntities) {
                LogUtil.d("请求网络成功:" + currentPriceReturnEntities.toString());
            }
        });
    }

    //请求分时数据
    private void currentPositionList() {
        LogUtil.d("充值变为获取当前分时数据");
        String exchangeName = null;
        String platformName = null;
        String symbol = null;
        for (List<ProductEntity> product : products) {
            for (ProductEntity productEntity : product) {
                exchangeName = productEntity.getExchangeName();
                platformName = productEntity.getPlatformName();
                symbol = productEntity.getSymbol();
            }
        }
        LogUtil.d("exchangeName结果是:" + exchangeName + "platformName结果是:" + platformName + "symbol结果是:" + symbol);
        //假数据
        NetworkAPIFactoryImpl.getDealAPI().timeline("TJPME", "JH", "AG", 2, new OnAPIListener<List<CurrentTimeLineReturnEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("请求网络失败-----");
            }

            @Override
            public void onSuccess(List<CurrentTimeLineReturnEntity> currentTimeLineReturnEntities) {
                LogUtil.d("请求网络成功:" + currentTimeLineReturnEntities.toString());
            }
        });
    }

    @Override
    public void initStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(getActivity(),
                (DrawerLayout) getActivity().findViewById(R.id.drawer),
                getResources().getColor(R.color.default_main_color), 0);
    }
}
