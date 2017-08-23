package com.xinyu.mwp.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.xinyu.mwp.R;
import com.xinyu.mwp.util.NumberUtils;


/**
 * Created by Administrator on 2017/3/20.
 */
public class NewMarkerView extends MarkerView {
    private Context context;
    private TextView time;
    private TextView openPrice;
    private TextView highestPrice;
    private TextView lowestPrice;
    private TextView closePrice;
    private final LinearLayout markerView;

    public NewMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        time = (TextView) findViewById(R.id.time);
        openPrice = (TextView) findViewById(R.id.openPrice);
        highestPrice = (TextView) findViewById(R.id.highestPrice);
        lowestPrice = (TextView) findViewById(R.id.lowestPrice);
        closePrice = (TextView) findViewById(R.id.closePrice);
        markerView = (LinearLayout) findViewById(R.id.ll_marker_view);
        this.context = context;
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        if (entry.getData() == null) {
            markerView.setVisibility(INVISIBLE);
        } else {
            markerView.setVisibility(VISIBLE);
            time.setText(entry.getData() + "");
            CandleEntry candleEntry = (CandleEntry) entry;
            highestPrice.setText(NumberUtils.halfAdjust6(candleEntry.getHigh()));
            lowestPrice.setText(NumberUtils.halfAdjust6(candleEntry.getLow()));
            openPrice.setText(NumberUtils.halfAdjust6(candleEntry.getOpen()));
            closePrice.setText(NumberUtils.halfAdjust6(candleEntry.getClose()));
        }
    }

    @Override
    public int getXOffset(float xpos) {
//        return -(getWidth() / 2);
        return -(getWidth() / 5);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }

//    @Override
//    public void draw(Canvas canvas, float posx, float posy) {
////        posx += getXOffset(posx);
////        posy += getYOffset(posy);
//        if (posx > DisplayUtil.getScreenWidth(context) / 2) {
//            canvas.translate(0, 0);
//        } else {
//            canvas.translate(DisplayUtil.getScreenWidth(context) - getWidth(), 0);
//        }
//        draw(canvas);
//    }
}
