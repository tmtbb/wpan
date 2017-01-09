package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.bean.ProductBean;
import com.xinyu.mwp.ui.adapter.UserInfoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 用户主页--好友推单
 * Created by Administrator on 2017/1/6.
 */
public class UserFriendPushBillFragment extends BaseFragment {

    List<ProductBean> mList;
    @BindView(R.id.tv_share_bill_total_count)
    TextView tvShareBillTotalCount;
    @BindView(R.id.tv_share_bill_month_count)
    TextView tvShareBillMonthCount;
    @BindView(R.id.tv_share_bill_week_count)
    TextView tvShareBillWeekCount;
    @BindView(R.id.tv_share_bill_today_count)
    TextView tvShareBillTodayCount;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_user_friend_push_bill);

        ButterKnife.bind(this, parentView);
        // initView();
        //initListener();

        return parentView;
    }

}

