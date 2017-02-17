package com.xinyu.mwp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.xinyu.mwp.R;
import com.xinyu.mwp.fragment.base.BaseFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 晒单页面--敬请期待!
 */

public class ShareOrderExpectFragment extends BaseFragment {

    @ViewInject(R.id.leftImage)
    ImageButton leftImage;
    @ViewInject(R.id.titleText)
    TextView titleText;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_shareorder_expect;
    }

    @Override
    protected void initView() {
        super.initView();
        titleText.setText("晒单");
        leftImage.setVisibility(View.INVISIBLE);
    }


    @Override
    public void initStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(getActivity(),
                (DrawerLayout) getActivity().findViewById(R.id.drawer),
                getResources().getColor(R.color.default_main_color), 0);
    }
}
