package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.bean.ProductBean;
import com.xinyu.mwp.ui.activities.UserHomeActivity;
import com.xinyu.mwp.ui.adapter.RankingInfoAdapter;
import com.xinyu.mwp.ui.adapter.UserInfoAdapter;
import com.xinyu.mwp.utils.LogUtil;
import com.xinyu.mwp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 用户主页--好友晒单
 * Created by Administrator on 2017/1/6.
 */
public class UserFriendShareFragment extends BaseFragment {

    List<ProductBean> mList;
    @BindView(R.id.tv_share_bill_total_count)
    TextView tvShareBillTotalCount;
    @BindView(R.id.tv_share_bill_month_count)
    TextView tvShareBillMonthCount;
    @BindView(R.id.tv_share_bill_week_count)
    TextView tvShareBillWeekCount;
    @BindView(R.id.tv_share_bill_today_count)
    TextView tvShareBillTodayCount;
    @BindView(R.id.rv_user_friend_share)
    RecyclerView rvUserFriendShare;

    private UserInfoAdapter mUserInfoAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_user_friend_share);

        ButterKnife.bind(this, parentView);
        initView();
        initListener();

        return parentView;
    }

    @Override
    public void initView() {
        /**
         *假数据
         */
        mList = new ArrayList<>();
        List<String> products = Arrays.asList(getResources().getStringArray(R.array.products));

        ProductBean bean;
        int size = products.size();
        for (int i = 0; i < size; i++) {
            bean = new ProductBean();
            bean.setName(products.get(i));
            mList.add(bean);
        }
        rvUserFriendShare.setLayoutManager(new LinearLayoutManager(context));
        rvUserFriendShare.setHasFixedSize(true);

        mUserInfoAdapter = new UserInfoAdapter(context, mList, R.layout.item_friend_share_bill);
        rvUserFriendShare.setAdapter(mUserInfoAdapter);
    }

    @Override
    public void initListener() {
        //super.initListener();设置条目的点击事件
        mUserInfoAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                ToastUtil.showToast(mList.get(position).getName(), context);
                //跳转到用户首页
                LogUtil.d("跳转到用户首页............");

               // toActivity(UserHomeActivity.class);

            }

            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });
    }
}

