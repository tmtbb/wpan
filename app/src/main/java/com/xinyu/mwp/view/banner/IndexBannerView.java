package com.xinyu.mwp.view.banner;

import android.content.Context;
import android.util.AttributeSet;

import com.xinyu.mwp.entity.IndexBannerEntity;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-12-10 19:21
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class IndexBannerView extends CommonBannerView<IndexBannerEntity> {


    public IndexBannerView(Context context) {
        super(context);
    }

    public IndexBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        super.initView();
        sliderBannerController = new IndexBannerController(getContext(), sliderBanner);
    }

}
