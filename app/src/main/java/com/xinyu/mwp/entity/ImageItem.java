package com.xinyu.mwp.entity;

import android.graphics.Bitmap;

import com.xinyu.mwp.util.Bimp;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/3/14 0014
 * @describe : ImageItem
 */
public class ImageItem implements Serializable {
    public static final String TAKE_PIC = "TAKE_PIC";
    public String imageId = "";
    public String thumbnailPath = "";
    public String imagePath= "";
    private Bitmap bitmap;
    public boolean isSelected = false;
    private String tag;
    private long modifyTime;

    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getThumbnailPath() {
        return thumbnailPath;
    }
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    public Bitmap getBitmap() {
        if(bitmap == null){
            try {
                bitmap = Bimp.revitionImageSize(imagePath);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ImageItem) {
            ImageItem imageItem = (ImageItem) o;
            return imagePath.equals(imageItem.getImagePath());
        }
        return false;
    }
}
