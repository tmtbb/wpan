package com.xinyu.mwp.view.refresh;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinyu.mwp.R;

/**
 * Created by Administrator on 2016/8/1.
 */
public class DefaultRefreshViewHolder extends RefreshViewHolder {

    public DefaultRefreshViewHolder(SuperSwipeRefreshLayout swipeRefresh) {
        super(swipeRefresh);
    }

    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    public View createHeaderView() {
        View headerView = LayoutInflater.from(swipeRefresh.getContext()).inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.mipmap.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    @Override
    public void onRefresh() {
        textView.setText("正在刷新");
        imageView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }

    @Override
    public void onPullDistance(int distance) {
        // pull distance
    }

    @Override
    public void onPullEnable(boolean enable) {
        textView.setText(enable ? "松开刷新" : "下拉刷新");
        imageView.setVisibility(View.VISIBLE);
        imageView.setRotation(enable ? 180 : 0);
    }

    private ProgressBar footerProgressBar;
    private ImageView footerImageView;
    private TextView footerTextView;

    @Override
    public View createFooterView() {
        View footerView = LayoutInflater.from(swipeRefresh.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView.findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView.findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.mipmap.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    @Override
    public void onLoadMore() {
        footerTextView.setText("正在加载...");
        footerImageView.setVisibility(View.GONE);
        footerProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipeRefresh.setLoadMore(false);
            }
        }, 5000);
    }

    @Override
    public void onPushDistance(int distance) {
    }

    @Override
    public void onPushEnable(boolean enable) {
        footerTextView.setText(enable ? "松开加载" : "上拉加载");
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setRotation(enable ? 0 : 180);
    }
}
