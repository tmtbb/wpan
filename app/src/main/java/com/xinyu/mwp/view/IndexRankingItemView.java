package com.xinyu.mwp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/13 0013
 * @describe : com.xinyu.mwp.view
 */
public class IndexRankingItemView extends BaseFrameLayout {

    @ViewInject(R.id.titleView)
    private TextView titleView;
    @ViewInject(R.id.descriptionView)
    private TextView descriptionView;
    @ViewInject(R.id.imageView)
    private ImageView imageView;

    public IndexRankingItemView(Context context) {
        super(context);
    }

    public IndexRankingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }

    protected void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.IndexRankingItem, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.IndexRankingItem_index_ranking_item_title)) {
                titleView.setText(arr.getString(R.styleable.IndexRankingItem_index_ranking_item_title));
            }
            if (arr.hasValue(R.styleable.IndexRankingItem_index_ranking_item_description)) {
                descriptionView.setText(arr.getString(R.styleable.IndexRankingItem_index_ranking_item_description));
            }
            if (arr.hasValue(R.styleable.IndexRankingItem_index_ranking_item_icon)) {
                imageView.setImageResource(arr.getResourceId(R.styleable.IndexRankingItem_index_ranking_item_icon, 0));
            }
            arr.recycle();
            arr = null;
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_index_ranking_item;
    }
}
