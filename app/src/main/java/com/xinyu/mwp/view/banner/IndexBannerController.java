package com.xinyu.mwp.view.banner;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.activity.IndexBannerActivity;
import com.xinyu.mwp.constant.ActionConstant;
import com.xinyu.mwp.entity.IndexBannerEntity;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;
import com.xinyu.mwp.util.LogUtil;

public class IndexBannerController extends BaseBannerController<IndexBannerEntity> {

    public IndexBannerController(Context context, SliderBannerLayout sliderBanner) {
        super(context, sliderBanner);
    }

    @Override
    protected void displayImage(IndexBannerEntity entity, ImageView imageView) {
        ImageLoader.getInstance().displayImage(entity.getImg(), imageView, DisplayImageOptionsUtil.getInstance().getBannerOptions());
    }

    @Override
    public void imageClick(IndexBannerEntity entity, int position) {
        if (entity == null)
            return;
        try {
            int jumpType = Integer.parseInt(entity.getJumpType());
            String jumpCategory = entity.getJumpCategory();   //轮播图片的种类

            switch (jumpCategory) {
                case ActionConstant.JumpCategory.INDEX_POSITION_INFO:  //首页
                    Intent intent = new Intent(context, IndexBannerActivity.class);
                    intent.putExtra("entity", entity);

                    context.startActivity(intent);
                    break;
                case ActionConstant.JumpCategory.RECHARGE_BANNER:  //充值界面

                    break;
//                case ActionConstant.Fix.CONSTANT_1://跳转内容
//                    try {
//                        SkipHelper skipHelper = new SkipHelper(context);
//                        skipHelper.setTrackPathAndPos("1", String.valueOf(position));
//                        skipHelper.nextWithType(Integer.parseInt(entity.getJumpCategory()), entity.getJumpId(), entity.getJumpUrl(), entity.getGname());
//                    } catch (Exception e) {
//                        LogUtil.showException(e);
//                    }
//                    break;
//                case ActionConstant.Fix.CONSTANT_2://跳转网页
//                    if (!TextUtils.isEmpty(entity.getJumpUrl()))
//                        ActivityUtil.nextWeb(context, entity.getJumpUrl(), Constant.LinkType.NORMAL_TYPE);
//                    break;
            }
        } catch (Exception e) {
            LogUtil.showException(e);
        }

    }

//    private void skipContent(IndexBannerEntity entity) {
//        try {
//            int jumpCategory = Integer.parseInt(entity.getJumpCategory());
//            int jumpId = Integer.parseInt(entity.getJumpId());
//            Map<String, Object> map = new HashMap<>();
//            switch (jumpCategory) {
//                case ActionConstant.Fix.CONSTANT_1://游戏
//                    map.put(TrackConstant.PropertyKey.GAME_ID, entity.getJumpId());
//                    ActivityUtil.nextNewDetails(context, entity.getJumpId(), false, TrackConstant.PageName.INDEX);
//                    break;
//                case ActionConstant.Fix.CONSTANT_2://礼包
//                    map.put(TrackConstant.PropertyKey.GIFT_ID, entity.getJumpId());
//                    ActivityUtil.nextGiftDetail(context, jumpId);
//                    break;
//                case ActionConstant.Fix.CONSTANT_3://公会
//                    map.put(TrackConstant.PropertyKey.SOCIATY_ID, entity.getJumpId());
//                    ActivityUtil.nextSociatyMaster(context, entity.getJumpId());
//                case ActionConstant.Fix.CONSTANT_4://活动
//                    map.put(TrackConstant.PropertyKey.ACTIVITY_URL, entity.getJumpUrl());
//                    ActivityUtil.nextMarket(context, entity.getJumpUrl());
//                    break;
//            }
//            Track.track(context, TrackConstant.Click.INDEX_BANNER, map);
//        } catch (Exception e) {
//            LogUtil.showException(e);
//        }
//
//    }
}
