package com.xinyu.mwp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.fragment.DealFragment;

import java.util.List;

/**
 * 交易界面recycleView的adapter
 * Created by Administrator on 2017/1/16.
 */

public class GalleryAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    public List<String> list;

    private OnClickListener mOnItemClickListener;

    //默认第0项被选中
    private int mPosition = 0;

    public void setPosition(int position) {
        mPosition = position;
    }

    /**
     * 回调
     */
    public interface OnClickListener {
        public void OnClick(View view, int position);

        public boolean OnLongClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

    public void setOnItemClick(OnClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;

    }


    public GalleryAdapter(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_recycleview_title, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textview.setText(list.get(position));


        if (mPosition == position) {
            viewHolder.textview.setSelected(true);
        } else {
            viewHolder.textview.setSelected(false);
        }

        //监听回调
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getPosition();
                    mOnItemClickListener.OnClick(viewHolder.itemView, pos);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHolder.getPosition();
                    mOnItemClickListener.OnLongClick(viewHolder, viewHolder.itemView, pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);

            textview = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}