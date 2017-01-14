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
    @ViewInject(R.id.unitView)
    private TextView unitView;
    @ViewInject(R.id.priceView)
    private TextView priceView;
    @ViewInject(R.id.percentView)
    private TextView percentView;
    @ViewInject(R.id.valueView)
    private TextView valueView;
    @ViewInject(R.id.highPriceView)
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
        if(data != null){
            titleView.setText(data.getTitle());
            unitView.setText(data.getUnit());
            priceView.setText(data.getPrice());
            percentView.setText(data.getPercent());
            valueView.setText(data.getValue());
            highPriceView.setText("最高 " + data.getHighPrice());
            todayPriceView.setText("今开" + data.getTodayPrice());
            lowPriceView.setText("最低" + data.getLowPrice());
            yesterdayPriceView.setText("昨开" + data.getYesterdayPrice());
        }
    }
}
