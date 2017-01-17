package com.xinyu.mwp.util;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/2/22 0022
 * @describe : ImageCallback
 */
public class ImageCallback implements ImageLoadingListener{

    protected void onImageFailed(String imageUri, View view, String failReason){

    }

    protected void onImageComplete(String imageUri, View view, Bitmap loadedImage){

    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        String message = "";
        if(failReason != null && failReason.getCause() != null){
            message = failReason.getCause().getMessage();
        }
        onImageFailed(imageUri, view, message);
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        onImageComplete(imageUri, view, loadedImage);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }
}
