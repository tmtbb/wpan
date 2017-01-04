package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by Don on 2016/12/1 15:57.
 * Describe：${针对5.x以上scrollview对recyclerview滑动卡顿问题解决}
 * Modified：${TODO}
 */

public class ScrollView5x extends ScrollView {

    private int downY;
    private int mTouchSlop;
    private GestureDetector mGestureDetector;
    private OnTouchListener mTouchListener;

    public ScrollView5x(Context context) {
        this(context, null);
    }

    public ScrollView5x(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollView5x(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e) && mGestureDetector.onTouchEvent(e);
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                return true;
            }
            return false;
        }
    }
}
