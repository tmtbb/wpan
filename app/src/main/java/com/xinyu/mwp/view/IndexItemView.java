package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.IndexItemEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/13 0013
 * @describe : com.xinyu.mwp.view
 */
public class IndexItemView extends BaseDataFrameLayout<IndexItemEntity> {

    @ViewInject(R.id.titleView)
    private TextView titleView;

    @ViewInject(R.id.priceView)
    private TextView priceView;
    @ViewInject(R.id.percentView)
    private TextView percentView;
    @ViewInject(R.id.price_change)
    private TextView priceChange;

    @ViewInject(R.id.high_priceView)
    private TextView highPriceView;
    @ViewInject(R.id.todayPriceView)
    private TextView todayPriceView;
    @ViewInject(R.id.lowPriceView)
    private TextView lowPriceView;
    @ViewInject(R.id.yesterdayPriceView)
    private TextView yesterdayPriceView;

    public IndexItemView(Context context) {
        super(context);
    }

    public IndexItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_index_item;
    }

    @Override
    public void update(IndexItemEntity data) {
        if (data != null) {
            titleView.setText(data.getTitle());

            priceView.setText(data.getPrice());
            percentView.setText(data.getPercent());
            priceChange.setText("+" + data.getPriceChange());  //正+ 负-
            highPriceView.setText(data.getHighPrice());
            todayPriceView.setText(data.getTodayPrice());
            lowPriceView.setText(data.getLowPrice());
            yesterdayPriceView.setText(data.getYesterdayPrice());
        }
    }
}
