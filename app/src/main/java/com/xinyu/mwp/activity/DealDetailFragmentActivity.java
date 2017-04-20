package com.xinyu.mwp.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseMultiFragmentActivity;
import com.xinyu.mwp.adapter.GalleryAdapter;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.fragment.BaseDealAllGoodsFragment;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealDetailFragmentActivity extends BaseMultiFragmentActivity {
    @ViewInject(R.id.money)
    private TextView money;
    @ViewInject(R.id.allHandsCount)
    private TextView allHandsCount;
    @ViewInject(R.id.allOrdersCount)
    private TextView allOrdersCount;
//
//    @ViewInject(R.id.bottomLayout)
//    private LinearLayout bottombar;

    @ViewInject(R.id.rv_title)
    private RecyclerView mRecyclerView;
    private long first;
    private List<ProductEntity> productList;
    private BaseDealAllGoodsFragment baseDealAllGoodsFragment;
    private List<String> mTitleList;
    private GalleryAdapter mAdapter;
    private List<String> mSymbolList;

    @Override
    public int getFragmentContainerId() {
        return R.id.contentContainer;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_dealdetail_fragment;
    }

    @Override
    public void createFragmentsToBackStack() {
        if (mSymbolList != null) {
            for (String s : mSymbolList) {
                baseDealAllGoodsFragment = new BaseDealAllGoodsFragment(mSymbolList);
                fragments.add(baseDealAllGoodsFragment);
            }
        }
        pushFragmentToBackStack(0);
    }

    @Override
    public void pushFragmentToBackStack(int selectIndex) {
        super.pushFragmentToBackStack(selectIndex);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("交易明细");
        requestProductList();//请求商品列表获取标题
    }

    private void requestProductList() {
        NetworkAPIFactoryImpl.getDealAPI().products(new OnAPIListener<List<ProductEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(List<ProductEntity> productEntities) {
                if (productList != null) {
                    productList.clear();
                }
                productList = productEntities;
                LogUtil.d("商品列表--请求成功的信息是:" + productEntities.toString());
                processData();
            }
        });
    }

    /**
     * 加载标题
     */
    private void processData() {
        mTitleList = new ArrayList<>();
        mSymbolList = new ArrayList<>();
        HashSet<String> setList = new HashSet<>();
        for (ProductEntity productEntity : productList) {
            if (setList.add(productEntity.getShowSymbol())) {
                mTitleList.add(productEntity.getShowSymbol());
                mSymbolList.add(productEntity.getSymbol());
            }
        }
        initProductName();//加载标题
        createFragmentsToBackStack();
    }

    private void initProductName() {
        if (mTitleList == null) {
            return;
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new GalleryAdapter(context, mTitleList);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setOnItemClick(new GalleryAdapter.OnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                baseDealAllGoodsFragment.updateFragment(position);
                pushFragmentToBackStack(position); //点击后切换不同的fragment
                mAdapter.setPosition(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean OnLongClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showLoader();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                money.setText(NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()));
//                allHandsCount.setText("---");
//                allOrdersCount.setText("---");
                closeLoader();
            }
        }, 1000);
    }
}
