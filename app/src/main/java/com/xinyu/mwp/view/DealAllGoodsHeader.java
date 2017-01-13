package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.DealAllGoodsEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealAllGoodsHeader extends BaseDataFrameLayout<DealAllGoodsEntity> {
    @ViewInject(R.id.rootLayout)
    private View rootLayout;
    @ViewInject(R.id.buyUp)
    private TextView buyUp;
    @ViewInject(R.id.buyDown)
    private TextView buyDown;
    @ViewInject(R.id.createDepot)
    private TextView createDepot;
    @ViewInject(R.id.flatDepot)
    private TextView flatDepot;

    public DealAllGoodsHeader(Context context) {
        super(context);
    }

    public DealAllGoodsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(DealAllGoodsEntity data) {
        buyUp.setText(data.getBuyUp());
        buyDown.setText(data.getBuyDown());
        createDepot.setText(data.getCreateDepot());
        flatDepot.setText(data.getFlatDepot());
        rootLayout.setVisibility(VISIBLE);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_dealallgoods_header;
    }
}
