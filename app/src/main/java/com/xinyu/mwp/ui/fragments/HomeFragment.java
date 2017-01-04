package com.xinyu.mwp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Don on 2016/11/12 17:53.
 * Describe：${视频Fragment}
 * Modified：${TODO}
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.tv)
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        tv.setText(this.getClass().getSimpleName());
        return parentView;
    }
}
