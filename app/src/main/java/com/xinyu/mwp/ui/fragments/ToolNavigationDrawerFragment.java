package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseAdapter;
import com.xinyu.mwp.base.BaseFragment;
import com.xinyu.mwp.bean.DrawerSettingBean;
import com.xinyu.mwp.ui.adapter.DrawerSettingAdapter;
import com.xinyu.mwp.utils.SpaceItemDecoration;
import com.xinyu.mwp.utils.ToastUtil;
import com.xinyu.mwp.view.CircleImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * 左侧侧滑菜单的fragment
 * Created by cg on 2015/8/26.
 */
public class ToolNavigationDrawerFragment extends BaseFragment {

    @BindView(R.id.tv_assets)
    TextView mAssets;
    @BindView(R.id.tv_grade)
    TextView mGrade;
    @BindView(R.id.rcv_drawer_list)
    RecyclerView mDrawerList;
    @BindView(R.id.view_head)
    CircleImageView mHead;

    private int[] settingImgs = new int[]{R.mipmap.ic_drawer_attention, R.mipmap.ic_drawer_push_bill, R.mipmap.ic_drawer_share_bill,
            R.mipmap.ic_drawer_exchange_detail, R.mipmap.ic_drawer_comments, R.mipmap.ic_drawer_products_grade, R.mipmap.ic_drawer_focus};
    private DrawerSettingAdapter mSettingAdapter;
    private List<String> mSettings;
    private List<DrawerSettingBean> mDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_drawer);
        initView();
        initListener();
        return parentView;
    }

    @Override
    public void initView() {
        /**
         * 侧滑菜单栏
         */
        mAssets.setText(String.format(getString(R.string.my_assets_num), "￥16202.00"));
        mGrade.setText(String.format(getString(R.string.my_grade_num), "123"));
        mDatas = new ArrayList<>();
        mSettings = Arrays.asList(getResources().getStringArray(R.array.drawer_setting));
        int size = mSettings.size();
        for (int i = 0; i < size; i++) {
            DrawerSettingBean settingBean = new DrawerSettingBean();
            settingBean.setIcon(settingImgs[i]);
            settingBean.setText(mSettings.get(i));
            mDatas.add(settingBean);
        }
        mDrawerList.setLayoutManager(new LinearLayoutManager(context));
        mDrawerList.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        mDrawerList.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mSettingAdapter = new DrawerSettingAdapter(context, mDatas, R.layout.item_drawer_setting);
        mDrawerList.setAdapter(mSettingAdapter);
    }

    @Override
    public void initListener() {
        mSettingAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // TODO: 2017/1/5 跳转到详情界面待定。。。
                ToastUtil.showToast(mSettings.get(position), context);
                if (activity instanceof menuItemClickListener) {
                    ((menuItemClickListener) activity).menuItemClick(position, mDatas.get(position).getText());
                }
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
            }
        });
    }

    /**
     * 设置菜单点击接口，以方便外部Activity调用
     */
    public interface menuItemClickListener {
        void menuItemClick(int position, String menuName);
    }
}
