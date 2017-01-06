package com.xinyu.mwp.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xinyu.mwp.R;

import java.util.List;

/**
 * Created by Don on 2017/1/5.
 * Describe ${TODO}
 * Modified ${TODO}
 */

public class AutoScrollViewPager extends ViewPager {

    private List<ImageView> mImageViews;//图片集合，在实际项目开发中，图片需要网络加载，会用到Picasso/Glide/Fresco等，那个时候的图片集合将不再是ImageView，而是url了。
    private List<ImageView> mDots;//指示点集合
    private AutoAdapter mAdapter;
    private int previousPosition;//指示点的前一个位置
    private long downTime;//按下时的系统时间
    private int mStartX;//按下时的X坐标
    private int mStartY;//按下时的Y坐标
    private static final int HANDLE_MESSAGE = 101;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_MESSAGE:
                    int currentItem = AutoScrollViewPager.this.getCurrentItem();
                    int position = (currentItem + 1) % (mImageViews.size());
                    AutoScrollViewPager.this.setCurrentItem(position, false);
                    sendEmptyMessageDelayed(HANDLE_MESSAGE, 2000);
                    break;
                default:
                    break;
            }
        }
    };

    public AutoScrollViewPager(Context context) {
        super(context);
        /**
         * 设置轮播图页变化监听
         */
        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mDots.get(previousPosition).setBackgroundResource(R.drawable.dot_normal);
                previousPosition = position;
                mDots.get(position).setBackgroundResource(R.drawable.dot_focused);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        /**
         * 设置轮播图点击监听
         */
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下的时候，取消自动轮播
                        mHandler.removeCallbacksAndMessages(null);
                        downTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.sendEmptyMessageDelayed(HANDLE_MESSAGE, 2000);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(HANDLE_MESSAGE, 2000);
                        long upTime = System.currentTimeMillis();
                        if ((upTime - downTime) < 500) {//如果按下和抬起之间的时间间隔小于500毫秒，我们认为是点击事件
                            //处理轮播图的某个子图的点击事件
                            if (mOnItemClickListener != null) {
                                int position = AutoScrollViewPager.this.getCurrentItem();
                                mOnItemClickListener.onClick(position);
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;//事件不拦截
            }
        });
    }

    /**
     * 对外提供的轮播图子条目点击事件回调接口
     */
    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onClick(int position);
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置图片方法
     *
     * @param imageViews
     */
    public void setImageView(List<ImageView> imageViews) {
        this.mImageViews = imageViews;
    }

    /**
     * 设置指示点
     *
     * @param dots
     */
    public void setDots(List<ImageView> dots) {
        this.mDots = dots;
    }

    /**
     * 开启自动轮播
     */
    public void start() {
        if (mAdapter == null) {
            mAdapter = new AutoAdapter();
            this.setAdapter(mAdapter);
        } else {
            this.mAdapter.notifyDataSetChanged();
        }
        mHandler.sendEmptyMessageDelayed(HANDLE_MESSAGE, 2000);
    }

    /**
     * 适配器
     */
    private class AutoAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViews == null ? 0 : mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 事件分发，用来判断轮播图是上下滑动，还是左右滑动
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) ev.getX();
                mStartY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) ev.getX();
                int currentY = (int) ev.getY();
                if (Math.abs(currentX - mStartX) < Math.abs(currentY - mStartY)) {//横向没有纵向滑的多，认为是上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {//左右滑动
                    getParent().requestDisallowInterceptTouchEvent(true);//请求父控件不拦截
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
