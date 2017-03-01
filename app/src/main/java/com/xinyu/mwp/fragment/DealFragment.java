package com.xinyu.mwp.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.jaeger.library.StatusBarUtil;
import com.xinyu.mwp.R;
import com.xinyu.mwp.fragment.base.BaseFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 交易界面
 * Created by Benjamin on 17/1/10.
 */

public class DealFragment extends BaseFragment {

    @ViewInject(R.id.leftImage)
    private ImageButton leftImage;

    @ViewInject(R.id.titleText)
    private TextView titleText;


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_deal;
    }

    @Override
    protected void initView() {
        super.initView();
        leftImage.setVisibility(View.INVISIBLE);
        titleText.setText("交易");

        //加载K线图等布局
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        DealProductPageFragment dealProductPageFragment = new DealProductPageFragment();
        fragmentTransaction.add(dealProductPageFragment, "sss");
        fragmentTransaction.commit();
        fragmentTransaction.replace(R.id.container_product, dealProductPageFragment);
    }

    @Override
    public void initStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(getActivity(),
                (DrawerLayout) getActivity().findViewById(R.id.drawer),
                getResources().getColor(R.color.default_main_color), 0);
    }
}
