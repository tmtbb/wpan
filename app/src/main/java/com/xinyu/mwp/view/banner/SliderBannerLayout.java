package com.xinyu.mwp.view.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xinyu.mwp.R;
import com.xinyu.mwp.view.BaseRelativeLayout;

import java.lang.reflect.Field;


/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-30 16:34
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class SliderBannerLayout extends BaseRelativeLayout {

    protected int idForViewPager;
    protected int idForIndicator;
    protected int timeInterval = 2000;
    private MyViewPager_1 viewPager;
    private CommonBannerAdapter bannerAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private PagerIndicator pagerIndicator;
    private AutoPlayer autoPlayer;
    private View.OnTouchListener viewPagerOnTouchListener;
    private int mIndex = 0;
    private int position = 0;
    private AutoPlayer.Playable galleryPlayable = new AutoPlayer.Playable() {

        @Override
        public void playTo(int to) {
            mIndex = to;
            viewPager.setCurrentItem(to, true);
        }

        @Override
        public void playNext() {
            int index = viewPager.getCurrentItem() + 1;
            mIndex = index % bannerAdapter.getRealCount();
            viewPager.setCurrentItem(index, true);
        }

        @Override
        public void playPrevious() {
            int index = viewPager.getCurrentItem() - 1;
            mIndex = index % bannerAdapter.getRealCount();
            viewPager.setCurrentItem(index, true);
        }

        @Override
        public int getTotal() {
            return bannerAdapter.getCount();
        }

        @Override
        public int getCurrent() {
            return viewPager.getCurrentItem();
        }
    };

    public SliderBannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }


    @Override
    protected int layoutId() {
        return 0;
    }


    protected void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SliderBanner, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.SliderBanner_slider_banner_pager)) {
                idForViewPager = arr.getResourceId(R.styleable.SliderBanner_slider_banner_pager, 0);
            }
            if (arr.hasValue(R.styleable.SliderBanner_slider_banner_indicator)) {
                idForIndicator = arr.getResourceId(R.styleable.SliderBanner_slider_banner_indicator, 0);
            }
            timeInterval = arr.getInt(R.styleable.SliderBanner_slider_banner_time_interval, timeInterval);
            arr.recycle();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (autoPlayer != null) {
                    autoPlayer.pause();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (autoPlayer != null) {
                    autoPlayer.resume();
                }
                break;
            default:
                break;
        }
        if (viewPagerOnTouchListener != null) {
            viewPagerOnTouchListener.onTouch(this, ev);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setViewPagerOnTouchListener(View.OnTouchListener onTouchListener) {
        viewPagerOnTouchListener = onTouchListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewPager = (MyViewPager_1) findViewById(idForViewPager);
        initViewPagerScroll();
//        viewPager.setPageTransformer(true, new AccordionTransformer());
        pagerIndicator = (DotView) findViewById(idForIndicator);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(i, v, i2);
                }
            }

            @Override
            public void onPageSelected(int position) {
                SliderBannerLayout.this.position = position;
                if (pagerIndicator != null) {
                    mIndex = position % bannerAdapter.getRealCount();
                    pagerIndicator.setSelected(bannerAdapter.getPositionForIndicator(position));
                }
                autoPlayer.skipNext();

                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(i);
                }
            }
        });

        autoPlayer = new AutoPlayer(galleryPlayable).setPlayRecycleMode(AutoPlayer.PlayRecycleMode.repeat_from_start);
        autoPlayer.setTimeInterval(timeInterval);
    }

    public void setTimeInterval(int interval) {
        autoPlayer.setTimeInterval(interval);
    }

    public void setAdapter(CommonBannerAdapter adapter) {
        bannerAdapter = adapter;
        viewPager.setAdapter(adapter);
    }

    public void beginPlay() {
        int number = 0;
        if (bannerAdapter.getRealCount() > 0) {
            number = position / bannerAdapter.getRealCount() * bannerAdapter.getRealCount();
            number += bannerAdapter.getRealCount();
        }
        autoPlayer.play(number);
    }

    public void pausePlay() {
        autoPlayer.pause();
    }

    public void stopPlay() {
        autoPlayer.stop();
    }

    public void resumePlay() {
        autoPlayer.resume();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        onPageChangeListener = listener;
    }

    public void setDotNum(int num) {
        if (pagerIndicator != null) {
            pagerIndicator.setNum(num, mIndex);
//            pagerIndicator.setHidden(num <= 1);
        }
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public MyViewPager_1 getViewPager() {
        return viewPager;
    }
}
