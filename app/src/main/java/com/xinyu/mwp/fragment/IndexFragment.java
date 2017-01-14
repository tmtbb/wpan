package com.xinyu.mwp.fragment;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.IndexItemEntity;
import com.xinyu.mwp.fragment.base.BaseControllerFragment;
import com.xinyu.mwp.fragment.base.BaseRefreshFragment;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.ImageUtil;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.view.IndexItemView;
import com.xinyu.mwp.view.banner.IndexBannerView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Benjamin on 17/1/10.
 */

public class IndexFragment extends BaseRefreshFragment {

    @ViewInject(R.id.refreshFrameLayout)
    private PtrFrameLayout refreshFrameLayout;
    @ViewInject(R.id.bannerView)
    private IndexBannerView bannerView;
    @ViewInject(R.id.itemLayout)
    private LinearLayout itemLayout;
    @ViewInject(R.id.bottomImageView)
    private ImageView bottomImageView;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView() {
        super.initView();
        bannerView.centerDot();
        bannerView.setRefreshLayout(refreshFrameLayout);
        bannerView.update(TestDataUtil.getIndexBanners(3));
        ImageLoader.getInstance().displayImage(ImageUtil.getRandomUrl(), bottomImageView, DisplayImageOptionsUtil.getInstance().getBannerOptions());
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemLayout.removeAllViews();
                        for (int i = 0; i < 3; i++){
                            IndexItemEntity indexItemEntity = new IndexItemEntity();
                            if(i == 0){
                                indexItemEntity.setTitle("白银");
                                indexItemEntity.setUnit("(元/千克)");
                                indexItemEntity.setPrice("3780");
                                indexItemEntity.setPercent("-0.47%");
                                indexItemEntity.setValue("-18");
                                indexItemEntity.setHighPrice("3900");
                                indexItemEntity.setLowPrice("3650");
                                indexItemEntity.setTodayPrice("3755");
                                indexItemEntity.setYesterdayPrice("3600");
                            }else if(i == 1){
                                indexItemEntity.setTitle("原油");
                                indexItemEntity.setUnit("(元/千克)");
                                indexItemEntity.setPrice("3780");
                                indexItemEntity.setPercent("+0.47%");
                                indexItemEntity.setValue("+18");
                                indexItemEntity.setHighPrice("3900");
                                indexItemEntity.setLowPrice("3650");
                                indexItemEntity.setTodayPrice("3755");
                                indexItemEntity.setYesterdayPrice("3600");
                            }else {
                                indexItemEntity.setTitle("咖啡");
                                indexItemEntity.setUnit("(元/千克)");
                                indexItemEntity.setPrice("3780");
                                indexItemEntity.setPercent("-0.47%");
                                indexItemEntity.setValue("-18");
                                indexItemEntity.setHighPrice("3900");
                                indexItemEntity.setLowPrice("3650");
                                indexItemEntity.setTodayPrice("3755");
                                indexItemEntity.setYesterdayPrice("3600");
                            }

                            IndexItemView indexItemView = new IndexItemView(context);
                            indexItemView.update(indexItemEntity);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(96, context));
                            params.topMargin = DisplayUtil.dip2px(1, context);
                            itemLayout.addView(indexItemView, params);
                        }
                        getRefreshController().refreshComplete();
                    }
                }, 500);
            }
        });
    }
}
