package com.xinyu.mwp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-08-24 13:44
 * @describe : CropImageUtil
 * @for your attention : none
 * @revise : none
 */
public class CropImageUtil {
    private int maxWidth = 1080;
    private int maxHeight = 1920;
    private int maxQuality = 50;
    private int maxImageSize = 1024 * 250;
    private int maxQualityNum = 4;
    public CropImageUtil() {
    }

    public CropImageUtil(int maxWidth, int maxHeight, int maxImageSize, int maxQuality, int maxQualityNum) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.maxImageSize = maxImageSize;
        this.maxQuality = maxQuality;
        this.maxQualityNum = maxQualityNum;
    }

    private int getSampleSize(int srcWidth, int srcHeight) {
        int sampleSize = 1;
        if (srcWidth < srcHeight && srcWidth > maxWidth) {
            sampleSize = (srcWidth / maxWidth);
        } else if (srcHeight < srcWidth && srcHeight > maxHeight) {
            sampleSize = (srcHeight / maxHeight);
        }
        return sampleSize;
    }


    private long getFileSize(String path) {
        File file = new File(path);
        long size = 0;
        if (file.exists()) {
            size = file.length();
        }
        return size;
    }

    private Bitmap getBitmap(String path, BitmapFactory.Options options) {
        try {
            return BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError e) {
        }
        return null;
    }

    private long compress(Bitmap bitmap, int quality, String outPath) {
        File outFile = new File(outPath);
        try {
            if (outFile.exists()) {
                outFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outFile);
            if (bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
            return outFile.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public void cropImage(String inImagePath, String outImagePath) {
        if (inImagePath != null) {
            long size = getFileSize(inImagePath);
            if (size > 0) {
                if (size > maxImageSize) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inPurgeable = true;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    BitmapFactory.decodeFile(inImagePath, options);
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = getBitmap(inImagePath, options);
                    if (bitmap == null) {
                        options.inSampleSize = getSampleSize(options.outWidth, options.outHeight);
                        bitmap = getBitmap(inImagePath, options);
                    }
                    if (bitmap != null) {
                        int quality = maxQuality;
                        for (int i = 0; i < maxQualityNum; ++i) {
                            size = compress(bitmap, quality, outImagePath);
                            if (size > maxImageSize) {
                                options.inSampleSize = getSampleSize(options.outWidth, options.outHeight);
                                bitmap = getBitmap(inImagePath, options);
                                quality -= 10;

                            } else
                                break;
                        }
                    }
                } else if (size != 0) {
                    FileCacheUtil.getInstance().copyFile(inImagePath, outImagePath);
                }
                int degree = ImageTools.getBitmapDegree(outImagePath);
                if (degree != 0) {
                    Bitmap bitmapByDegree = ImageTools.rotateBitmapByDegree(ImageTools.getBitmap(outImagePath), degree);
                    ImageTools.savaBitmap(bitmapByDegree, outImagePath);
                }
            }
            LogUtil.i("src=>size: " + getFileSize(inImagePath) + " crop=>size: " + getFileSize(outImagePath));
        }
    }

}
