package com.xinyu.mwp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 2016/11/11 11:41.
 * Describe：${普通的万能Adapter/支持onItemClick/支持onLongItemClick}
 * Modified：${TODO}
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private List<T> mDatas = new ArrayList<>();
    private int mLayoutId;
    private LayoutInflater mInflater;

    public BaseAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mLayoutId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        convert(context, holder, mDatas.get(position));
        if (onItemClickListener != null) {
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });

            holder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected abstract void convert(Context context, BaseViewHolder holder, T t);

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(BaseAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClick(View itemView, int position);

        void onItemLongClick(View itemView, int position);
    }
}
