package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.ImageItem;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.MyImageUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/3/16 0016
 * @describe : ChooseImgsAdapter
 */
public class ChooseUploadImgsAdapter extends BaseListViewAdapter<ImageItem> {

    public ChooseUploadImgsAdapter(Context context) {
        super(context);
    }

    public ChooseUploadImgsAdapter(Context context, List<ImageItem> list) {
        super(context, list);
    }

    @Override
    protected BaseViewHolder<ImageItem> getViewHolder(int position) {
        return new ChooseImgsViewHolder(context);
    }

    class ChooseImgsViewHolder extends BaseViewHolder<ImageItem> {

        @ViewInject(R.id.imv_img)
        private ImageView imv_img;

        public ChooseImgsViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_chooseuploadimgs_small;
        }

        @Override
        protected void update(ImageItem data) {
            ViewGroup.LayoutParams params = imv_img.getLayoutParams();
            params.height = DisplayUtil.getScreenWidth(context) / 3;
            imv_img.setLayoutParams(params);
            if (position == 0) {
                ImageLoader.getInstance().cancelDisplayTask(imv_img);
                imv_img.setImageResource(R.mipmap.icon_camera);
//                imv_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
//                imv_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                MyImageUtils.loadImage(imv_img, "file://" + data.imagePath);
            }
        }

    }
}
