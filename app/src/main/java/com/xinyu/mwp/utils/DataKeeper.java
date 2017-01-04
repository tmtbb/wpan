package com.xinyu.mwp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Don on 2016/11/11 14:06.
 * Describe：${数据存储工具类}
 * Modified：${TODO}
 * 注意： 1.将fileRootPath中的包名（这里是xxx.demo）改为你的应用包名
 * 2.在Application中调用init方法
 */

public class DataKeeper {

    private static final String TAG = "DataKeeper";

    /**
     * 不能实例化该对象
     */
    private DataKeeper() {
    }

    public static final String SAVE_SUCCEED = "保存成功";
    public static final String SAVE_FAILED = "保存失败";
    public static final String DELETE_SUCCEED = "删除成功";
    public static final String DELETE_FAILED = "删除失败";

    //文件缓存<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * @must 必须将fileRootPath中的包名（这里是xxx.demo）改为你的应用包名
     */
    public static final String fileRootPath = getSDPath() != null ? (getSDPath() + "/com.mcbn.jiacheng/") : null;
    public static final String accountPath = fileRootPath + "account/";
    public static final String audioPath = fileRootPath + "audio/";
    public static final String videoPath = fileRootPath + "video/";
    public static final String imagePath = fileRootPath + "image/";
    public static final String tempPath = fileRootPath + "temp/";
    //文件缓存>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 若存在SD 则获取SD卡的路径 不存在则返回null
     */
    public static String getSDPath() {
        File sdDir = null;
        String path = null;
        //判断sd卡是否存在
        boolean sdCardExist = hasSDCard();
        if (sdCardExist) {
            //获取跟目录
            sdDir = Environment.getExternalStorageDirectory();
            path = sdDir.toString();
        }
        return path;
    }

    /**
     * 判断是否有SD卡
     */
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private static Context context;

    /**
     * 初始化方法，仅执行一次
     *
     * @param instance
     */
    public static void init(Context instance) {
        //保存context，以便其他地方使用
        context = instance;
        if (hasSDCard()) {
            if (fileRootPath != null) {
                File file = new File(imagePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(videoPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                file = new File(audioPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                file = new File(fileRootPath + accountPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                file = new File(tempPath);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
        }
    }

    //存储文件的类型<<<<<<<<<<<<<<<<<<<<<<<<<
    public static final int TYPE_FILE_TEMP = 0;                             //保存保存临时文件
    public static final int TYPE_FILE_IMAGE = 1;                            //保存图片
    public static final int TYPE_FILE_VIDEO = 2;                            //保存视频
    public static final int TYPE_FILE_AUDIO = 3;                            //保存语音
    //存储文件的类型>>>>>>>>>>>>>>>>>>>>>>>>>

    //---------------外部存储缓存---------------

    /**
     * 存储缓存文件 返回文件绝对路径
     *
     * @param file 要存储的文件
     * @param type 文件的类型
     *             IMAGE = "imgae";					//图片
     *             VIDEO = "video";					//视频
     *             VOICE = "voice";					//语音
     * @return 存储文件的绝对路径名, 若SDCard不存在返回null
     */
    public static String storeFile(File file, int type) {
        if (!hasSDCard()) {
            return null;
        }
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);//后缀
        byte[] data = null;
        try {
            FileInputStream in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data, 0, data.length);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storeFile(data, suffix, type);
    }

    /**
     * @param data
     * @param suffix
     * @param type
     * @return 存储文件的绝对路径名, 若SDCard不存在返回null
     */
    public static String storeFile(byte[] data, String suffix, int type) {
        if (!hasSDCard()) {
            return null;
        }
        String path = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        switch (type) {
            case TYPE_FILE_IMAGE:
                path = imagePath + "IMG_" + time + "." + suffix;
                break;
            case TYPE_FILE_VIDEO:
                path = videoPath + "VIDEO_" + time + "." + suffix;
                break;
            case TYPE_FILE_AUDIO:
                path = audioPath + "AUDIO" + time + "." + suffix;
                break;
        }
        try {
            FileOutputStream out = new FileOutputStream(path);
            out.write(data, 0, data.length);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 获取缓存图片路径
     *
     * @param fileName
     * @return
     */
    public static String getImageFileCachePath(String fileName) {
        return getFileCachePath(TYPE_FILE_IMAGE, fileName, "jpg");
    }

    /**
     * 获取缓存视频路径
     *
     * @param fileName
     * @return
     */
    public static String getVideoFileCachePath(String fileName) {
        return getFileCachePath(TYPE_FILE_VIDEO, fileName, "mp4");
    }

    /**
     * 获取缓存音频路径
     *
     * @param fileName
     * @return
     */
    public static String getAudioFileCachePath(String fileName) {
        return getFileCachePath(TYPE_FILE_AUDIO, fileName, "mp3");
    }

    /**
     * 获取缓存文件路径
     *
     * @param fileName
     * @return
     */
    public static String getFileCachePath(int fileType, String fileName, String formSuffix) {
        switch (fileType) {
            case TYPE_FILE_IMAGE:
                return imagePath + fileName + "." + formSuffix;
            case TYPE_FILE_AUDIO:
                return audioPath + fileName + "." + formSuffix;
            case TYPE_FILE_VIDEO:
                return videoPath + fileName + "." + formSuffix;
            default:
                return tempPath + fileName + "." + formSuffix;
        }
    }

    //使用SharedPreferences保存 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String ROOT_SHARE_PREFS_ = "DEMO_SHARE_PREFS_";

    /**
     * 获取SharedPreferences实例
     *
     * @return
     */
    public static SharedPreferences getRootSharedPreferences() {
        return context.getSharedPreferences(ROOT_SHARE_PREFS_, Context.MODE_PRIVATE);
    }

    public static void save(String key, String value) {
        save(tempPath, key, value);
    }

    private static void save(String path, String key, String value) {
        save(context.getSharedPreferences(path, Context.MODE_PRIVATE), key, value);
    }

    public static void save(SharedPreferences sp, String key, String value) {
        if (sp == null || StringUtil.isNotEmpty(key, false) == false || StringUtil.isNotEmpty(value, false) == false) {
            return;
        }
        sp.edit().remove(key).putString(key, value).commit();
    }
}
