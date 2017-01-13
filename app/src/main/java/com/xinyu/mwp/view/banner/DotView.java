package com.xinyu.mwp.view.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xinyu.mwp.R;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-10-06 14:15
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DotView extends LinearLayout implements PagerIndicator {

    public interface OnDotClickHandler {
        void onDotClick(int index);
    }

    //private int littleDotSize = 12;
    private int dotSpan = 4;
    private float dotRadius = 8f;

    private int current = 0;
    private int total = 0;

    private int selectedColor = R.mipmap.icon_index_banner_selected;
    private int unSelectedColor = R.mipmap.icon_index_banner_normal;
    private OnDotClickHandler onDotClickHandler;

    public void setOnDotClickHandler(OnDotClickHandler handler) {
        onDotClickHandler = handler;
    }

    private OnClickListener mDotClickHandler = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof LittleDot && null != onDotClickHandler) {
                //  onDotClickHandler.onDotClick(((LittleDot) v).getIndex());
            }
        }
    };

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }

    protected void initAttributeSet(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER_HORIZONTAL);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.DotView_dot_radius)) {
                dotRadius = arr.getDimension(R.styleable.DotView_dot_radius, dotRadius);
            }

            if (arr.hasValue(R.styleable.DotView_dot_span)) {
                dotSpan = (int) arr.getDimension(R.styleable.DotView_dot_span, dotSpan);
            }

            selectedColor = arr.getResourceId(R.styleable.DotView_dot_selected_color, selectedColor);
            unSelectedColor = arr.getResourceId(R.styleable.DotView_dot_unselected_color, unSelectedColor);
            arr.recycle();
        }
        //  littleDotSize = (int) (dotSpan / 2 + dotRadius * 2);
    }

    @Override
    public void setNum(int num, int index) {
        if (num < 0)
            return;
        total = num;
        removeAllViews();
        setOrientation(HORIZONTAL);
        for (int i = 0; i < num; i++) {
            LittleDot dot = new LittleDot(getContext());
            if (i == index) {
                dot.setColor(selectedColor);
            } else {
                dot.setColor(unSelectedColor);
            }
            //  dot.setLayoutParams(new LayoutParams((int) littleDotSize, (int) dotRadius * 2, 1));
            dot.setPadding(dotSpan,0,0,0);
            dot.setClickable(true);
            dot.setOnClickListener(mDotClickHandler);
            addView(dot);
        }
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public void setSelected(int index) {
        if (index >= getChildCount() || index < 0 || current == index) {
            return;
        }
        if (current < getChildCount() && current >= 0) {
            ((LittleDot) getChildAt(current)).setColor(unSelectedColor);
        }
        ((LittleDot) getChildAt(index)).setColor(selectedColor);
        current = index;
    }

    @Override
    public int getCurrentIndex() {
        return current;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.setVisibility(hidden?GONE:VISIBLE);
    }

    private class LittleDot extends ImageView {

        private int mColor;

        public LittleDot(Context context) {
            super(context);

            setColor(R.mipmap.icon_index_banner_normal);
        }


        public void setColor(int color) {
            if (color == mColor)
                return;
            mColor = color;

            setImageResource(mColor);
        }
    }
}
