package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseMultiFragmentActivity;
import com.xinyu.mwp.entity.DealAllGoodsTopEntity;
import com.xinyu.mwp.fragment.BaseDealAllGoodsFragment;

import org.xutils.view.annotation.ViewInject;

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

    @ViewInject(R.id.bottomLayout)
    private LinearLayout bottombar;
    private long first;

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
        fragments.add(new BaseDealAllGoodsFragment());
        fragments.add(new BaseDealAllGoodsFragment());
        fragments.add(new BaseDealAllGoodsFragment());
        pushFragmentToBackStack(0);
    }

    @Override
    public void pushFragmentToBackStack(int selectIndex) {
        super.pushFragmentToBackStack(selectIndex);
        bottombar.getChildAt(selectIndex).setSelected(true);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("交易明细");
    }

    @Override
    protected void initData() {
        super.initData();
        showLoader();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DealAllGoodsTopEntity entity = new DealAllGoodsTopEntity();
                entity.setMoney("188888.99");
                entity.setHands("199999");
                entity.setOrders("1888888");

                money.setText(entity.getMoney());
                allHandsCount.setText(entity.getHands());
                allOrdersCount.setText(entity.getOrders());

                closeLoader();
            }
        }, 1000);
    }

    public void onClickSelect(View view) {
        if (System.currentTimeMillis() - first > 350) {
            if (selectIndex >= 0) {
                bottombar.getChildAt(selectIndex).setSelected(false);
            }
            try {
                for (int i = 0; i < bottombar.getChildCount(); ++i) {
                    View childView = bottombar.getChildAt(i);
                    if (view == childView) {
                        pushFragmentToBackStack(i);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        first = System.currentTimeMillis();
    }
}
