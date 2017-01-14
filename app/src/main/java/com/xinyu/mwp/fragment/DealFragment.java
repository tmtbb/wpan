package com.xinyu.mwp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.fragment.base.BaseControllerFragment;
import com.xinyu.mwp.fragment.base.BaseFragment;
import com.xinyu.mwp.util.LogUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
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
    @ViewInject(R.id.rg_deal_product)
    private RadioGroup radioGroup;
    @ViewInject(R.id.rb_deal_product1)
    private RadioButton rbDealProduct1;   //白银
    @ViewInject(R.id.rb_deal_product2)
    private RadioButton rbDealProduct2; //原油
    @ViewInject(R.id.rb_deal_product3)
    private RadioButton rbDealProduct3;//咖啡
    @ViewInject(R.id.vp_deal_product_info)
    private ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_deal;
    }


    @Override
    protected void initView() {
        super.initView();

        leftImage.setVisibility(View.INVISIBLE);
        titleText.setText("交易");
        viewPager.setOffscreenPageLimit(radioGroup.getChildCount());
        fragmentList.add(new DealProductPageFragment());
        fragmentList.add(new DealProductPageFragment());
        fragmentList.add(new DealProductPageFragment());
        radioGroup.getChildAt(0).setSelected(true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList != null ? fragmentList.size() : 0;
            }

            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // viewPager.setCurrentItem(position);
                LogUtil.d("当前选中的position:" + position);

                if (position == 0) {
                    rbDealProduct1.setChecked(true);
                } else if (position == 1) {
                    rbDealProduct2.setChecked(true);
                } else {
                    rbDealProduct3.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event({R.id.rb_deal_product1, R.id.rb_deal_product2, R.id.rb_deal_product3})
    private void onClick(View view) {
        viewPager.setCurrentItem(radioGroup.indexOfChild(view));
        LogUtil.d("点击的条目是:" + radioGroup.indexOfChild(view));
    }
}
