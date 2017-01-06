package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.utils.LogUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 上周名人
 * Created by Administrator on 2017/1/6.
 */
public class WeekRankingFragment extends Fragment {


    private View view;
    private TextView tvRanking;  //排名名次
    private ListView lvWeek;  //listView
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_share_week, container, false);
        initView();
        initData();
        return view;
    }

    //初始化布局
    private void initView() {
        tvRanking = (TextView) view.findViewById(R.id.tv_share_ranking);
        lvWeek = (ListView) view.findViewById(R.id.lv_share_ranking_week);
        list = new ArrayList<>();
        list.add("猪八戒");
        list.add("沙和尚");
        list.add("唐僧");
        list.add("孙悟空");
    }

    //初始化数据  网络请求数据--javabean对象--填充到item里面
    private void initData() {
        LogUtil.d("上周名人--初始化");
        lvWeek.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {


                if (convertView == null){
                    convertView = View.inflate(getContext(),R.layout.item_share_ranking,null);
                }
                TextView userName = (TextView) convertView.findViewById(R.id.tv_user_name);
                userName.setText(list.get(position));

                AutoUtils.autoSize(convertView);
                return convertView;
            }
        });
    }
}
