package com.xinyu.mwp.util;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.xinyu.mwp.R;

public class DisplayImageOptionsUtil {

    private static DisplayImageOptionsUtil instance = null;

    public static synchronized DisplayImageOptionsUtil getInstance() {
        if (instance == null) {
            instance = new DisplayImageOptionsUtil();
        }
        return instance;
    }

    private DisplayImageOptions userHeaderOptions;

    private DisplayImageOptions.Builder getBuilder() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true).cacheOnDisk(true);
        return builder;
    }

    public DisplayImageOptions getUserHeaderOptions() {
        if (userHeaderOptions == null)
            userHeaderOptions = getBuilder().showImageOnLoading(R.mipmap.icon_default_head)
                    .showImageForEmptyUri(R.mipmap.icon_default_head)
                    .showImageOnFail(R.mipmap.icon_default_head).build();
        return userHeaderOptions;
    }
}
