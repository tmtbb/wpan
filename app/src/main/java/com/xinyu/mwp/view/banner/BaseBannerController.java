package com.xinyu.mwp.view.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseController;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;

import java.util.List;


public abstract class BaseBannerController<Entity> extends BaseController {

    protected SliderBannerLayout sliderBanner;
    protected InnerAdapter bannerAdapter = new InnerAdapter();

    public BaseBannerController(Context context, SliderBannerLayout sliderBanner) {
        super(context);
        this.context = context;
        this.sliderBanner = sliderBanner;
        sliderBanner.setAdapter(bannerAdapter);
    }


    public void play(List<Entity> list) {
        bannerAdapter.setEntityList(list);
        bannerAdapter.notifyDataSetChanged();
        sliderBanner.beginPlay();
        sliderBanner.setDotNum(list.size());
//        if (list.size() > 1) {
//        } else {
//            sliderBanner.stopPlay();
//        }
//        sliderBanner.getViewPager().setNoScroll(list.size() > 1);

    }

    public void pause() {
        if (sliderBanner != null)
            sliderBanner.pausePlay();
    }

    public void resume() {
        if (sliderBanner != null)
            sliderBanner.resumePlay();
    }

    private class InnerAdapter extends CommonBannerAdapter {
        private List<Entity> entityList;

        public void setEntityList(List<Entity> entityList) {
            this.entityList = entityList;
        }

        public Entity getItem(int position) {
            if (entityList == null || entityList.size() == 0)
                return null;
            return entityList.get(getPositionForIndicator(position));
        }

        @Override
        public int getPositionForIndicator(int position) {
            if (null == entityList || entityList.size() == 0) {
                return 0;
            }
            return position % entityList.size();
        }

        @Override
        public View getView(LayoutInflater layoutInflater, final int position) {
            View convertView = layoutInflater.inflate(R.layout.item_banner, null);

            try {
                final Entity entity = getItem(position);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.bannerImage);
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (entity == null) {
                    ImageLoader.getInstance().displayImage("", imageView, DisplayImageOptionsUtil.getInstance().getBannerOptions());
                    return convertView;
                }
                displayImage(entity, imageView);
                convertView.setTag(entity);
                convertView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_UP:
                                imageClick(entity, position);
                                break;
                        }
                        return false;
                    }
                });
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public int getRealCount() {
            if (entityList != null) {
                return entityList.size();
            } else {
                return 0;
            }
        }

        @Override
        public int getCount() {
            if (entityList == null) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }
    }

    protected abstract void displayImage(Entity entity, ImageView imageView);

    /**
     * 点击图片
     *
     * @param entity
     */
    protected void imageClick(Entity entity, int position) {

    }
}
