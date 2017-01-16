package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.RechargeRecordEntity;
import com.xinyu.mwp.listener.OnChildViewClickListener;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Benjamin on 17/1/16.
 */

public class RechargeRecordHeader extends BaseDataFrameLayout<List<RechargeRecordEntity>> {
    @ViewInject(R.id.title)
    private TextView title;
    private List<RechargeRecordEntity> entities;

    public RechargeRecordHeader(Context context) {
        super(context);
    }

    public RechargeRecordHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(List<RechargeRecordEntity> data) {
        entities = data;
        updateTitle(data.get(0).getTime());
    }

    public void updateTitle(String title) {
        this.title.setText(title);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_rechargerecord_header;
    }

    @Event(value = R.id.date)
    private void click(View v) {
        String[] strs = new String[entities.size()];
        for (int i = 0; i < entities.size(); i++) {
            strs[i] = entities.get(i).getTime();
        }
        MyPopupMenu popupMenu = new MyPopupMenu(getContext(), MyPopupMenu.toEntity(strs));
        popupMenu.show1(v);
        popupMenu.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                RechargeRecordHeader.this.onChildViewClick(childView, 99, action);
            }
        });
    }
}
