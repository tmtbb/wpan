package com.xinyu.mwp.view.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xinyu.mwp.R;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.view.BaseRelativeLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-07-06 18:02
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CommonBannerView<Entity> extends BaseRelativeLayout {
    @ViewInject(R.id.bannerLayout)
    protected LinearLayout bannerLayout;
    @ViewInject(R.id.banner_indicator)
    protected DotView dotView;
    protected SliderBannerLayout sliderBanner;
    protected PtrFrameLayout refreshFrameLayout;
    protected BaseBannerController sliderBannerController;
    public CommonBannerView(Context context) {
        super(context);
    }
    public CommonBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        super.initView();
        sliderBanner = (SliderBannerLayout) rootView.findViewById(R.id.sliderBanner);
        LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , DisplayUtil.getScreenWidth(getContext()) * 12 / 25);
        sliderBanner.setLayoutParams(lyp);
//        rightBottomDot();
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_banner;
    }

    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            if (sliderBannerController != null)
                sliderBannerController.pause();
        } else {
            if (sliderBannerController != null)
                sliderBannerController.resume();
        }
    }

    /**
     * 更新banner高度
     * @param height
     */
    public void updateSliderBannerHeight(int height){
        LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        sliderBanner.setLayoutParams(lyp);
    }

    public void update(List<Entity> bannerEntityList) {
        if(bannerEntityList == null || bannerEntityList.size() ==0)
        {
            this.setVisibility(GONE);
            return;
        }
        this.setVisibility(VISIBLE);
        sliderBannerController.play(bannerEntityList);
    }

    @Override
    public void setVisibility(int visibility) {
        if (bannerLayout != null) {
            bannerLayout.setVisibility(visibility);
        }
        //super.setVisibility(visibility);
    }

    /**
     * dot水平居中对齐
     */
    public void centerDot(){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dotView.getLayoutParams();
        if(params != null) {
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            dotView.setLayoutParams(params);
        }
    }

    /**
     * dot右对齐
     */
    public void rightBottomDot(){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dotView.getLayoutParams();
        if(params != null) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            dotView.setLayoutParams(params);
        }
    }

    public void setRefreshLayout(PtrFrameLayout refreshFrameLayout) {
        this.refreshFrameLayout = refreshFrameLayout;
        if (refreshFrameLayout != null) {
            sliderBanner.setViewPagerOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            if (CommonBannerView.this.refreshFrameLayout.isEnabled())
                                CommonBannerView.this.refreshFrameLayout.setEnabled(false);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            CommonBannerView.this.refreshFrameLayout.setEnabled(true);
                            break;
                    }
                    return false;
                }
            });
        }
    }
}
