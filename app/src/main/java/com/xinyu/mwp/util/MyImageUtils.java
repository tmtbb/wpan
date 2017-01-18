package com.xinyu.mwp.util;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/2/22 0022
 * @describe : MyImageUtils
 */
public class MyImageUtils {

    /**
     * 加载图片
     *
     * @param imageView
     * @param imageUrl
     */
    public static void loadImage(ImageView imageView, String imageUrl) {
        loadImage(imageView, imageUrl, R.mipmap.icon_default_head, R.mipmap.icon_default_head, R.mipmap.icon_default_head);
    }

    public static void loadHeadImage(ImageView imageView, String imageUrl) {
        loadHeadImage(imageView, imageUrl, null);
    }

    public static void loadHeadImage(ImageView imageView, String imageUrl, ImageCallback imageCallback) {
        loadImage(imageView, imageUrl, R.mipmap.icon_default_head, R.mipmap.icon_default_head, R.mipmap.icon_default_head, imageCallback);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param imageUrl
     * @param loadingResId
     * @param emptyResId
     * @param failureResId
     */
    public static void loadImage(ImageView imageView, String imageUrl, int loadingResId, int emptyResId, int failureResId) {
        loadImage(imageView, imageUrl, loadingResId, emptyResId, failureResId, null);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param imageUrl
     * @param loadingResId
     * @param emptyResId
     * @param failureResId
     * @param imageCallback
     */
    public static void loadImage(ImageView imageView, String imageUrl, int loadingResId, int emptyResId, int failureResId, ImageCallback imageCallback) {
        ImageLoader.getInstance().displayImage(imageUrl, imageView, DisplayImageOptionsUtil.getInstance().getImageOptions(loadingResId, emptyResId, failureResId), imageCallback);
    }

    public static void loadImage(ImageView imageView, String imageUrl, ImageCallback imageCallback) {
        loadImage(imageView, imageUrl, R.mipmap.icon_default_head, R.mipmap.icon_default_head, R.mipmap.icon_default_head, imageCallback);
    }
}
