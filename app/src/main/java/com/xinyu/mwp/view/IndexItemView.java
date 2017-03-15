package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.util.NumberUtils;

import org.xutils.view.annotation.ViewInject;

import java.text.NumberFormat;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/13 0013
 * @describe : com.xinyu.mwp.view
 */
public class IndexItemView extends BaseDataFrameLayout<CurrentPriceReturnEntity> {

    @ViewInject(R.id.ll_item_price)
    private LinearLayout itemPrice;
    @ViewInject(R.id.titleView)
    private TextView titleView;
    @ViewInject(R.id.iv_arrow)
    private ImageView arrow;

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
    public void update(CurrentPriceReturnEntity data) {
        if (data != null) {
            itemPrice.setVisibility(VISIBLE);

            if (data.getChange() > 0) {
                priceChange.setTextColor(getResources().getColor(R.color.default_red));
                arrow.setImageResource(R.mipmap.icon_arrow_up);
                priceView.setTextColor(getResources().getColor(R.color.default_red));
            } else {
                priceChange.setTextColor(getResources().getColor(R.color.default_green));
                arrow.setImageResource(R.mipmap.icon_arrow_fall);
                priceView.setTextColor(getResources().getColor(R.color.default_green));
            }

            Animation alpha = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
            arrow.startAnimation(alpha);
            priceView.setText(NumberUtils.halfAdjust4(data.getCurrentPrice()));
            titleView.setText(data.getShowSymbol());
            priceChange.setText(NumberUtils.halfAdjust5(data.getChange()));  //正+ 负-
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(2);
            String resultPercent = numberFormat.format(data.getChange() / data.getCurrentPrice() * 100);
            percentView.setText(resultPercent);
            highPriceView.setText(NumberUtils.halfAdjust5(data.getHighPrice()));
            todayPriceView.setText(NumberUtils.halfAdjust5(data.getOpeningTodayPrice()));
            lowPriceView.setText(NumberUtils.halfAdjust5(data.getLowPrice()));
            yesterdayPriceView.setText(NumberUtils.halfAdjust5(data.getClosedYesterdayPrice()));
        }
    }
}
