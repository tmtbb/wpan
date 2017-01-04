package com.xinyu.mwp.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

    public static final String FORMAT1 = "yyyyMMddHHmmss";
    public static final String FORMAT2 = "yyyy/MM/dd HH:mm";
    public static final String FORMAT3 = "yyyy-MM-dd HH:mm";

    public static final String FORMAT4 = "MM-dd HH:mm";

    public static final String FORMAT5 = "yyyyMMdd";
    public static final String FORMAT6 = "yyyy-MM-dd";
    public static final String FORMAT7 = "MM-dd";
    public static final String FORMAT8 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT9 = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT10 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT11 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT12 = "yyyyMM";

    public static final String FORMAT13 = "yyyy年 MM月";
    public static final String FORMAT14 = "MM月dd日  HH:mm";
    public static final String FORMAT15 = "yyyy-MM";
    public static final String FORMAT16 = "yyyy年MM月dd日";
    public static final String FORMAT17 = "yyyy.MM.dd HH:mm";
    public static final String DATE_FORMATER_7 = "yyyy/MM/dd HH:mm";

    public static String getDateStringByFormat(Date date, String format) {
        String _d = "";
        try {
            _d = new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _d;
    }

    public static String getDateFromStr(String time, String dataFormater) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat(dataFormater, Locale.CHINA);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(time) * 1000);
            Date date = calendar.getTime();
            return sdf.format(date);
        } else {
            return null;
        }
    }

    public static String stringPattern(String date, String oldPattern,
                                       String newPattern) {

        if (date == null || oldPattern == null || newPattern == null
                || date.equals("null") || date.equals(""))
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);
        Date d = null;
        try {
            d = sdf1.parse(date);
            return sdf2.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Date getDateByString(String dateStr, String df) {
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentDatetimeString(String format) {
        format = (format == null || format.trim().length() <= 0) ? "yyyy-MM-dd HH:mm:ss"
                : format;
        return new SimpleDateFormat(format).format(new Date());
    }


    public static String getWeek(String strDate) {

        SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT1);
        Date date = null;
        String week = "";
        try {
            date = sdf1.parse(strDate);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            week = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return week;
    }

    public static String getWeek(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String longPattern(long millis, String newPattern) {
        if (millis < 1000)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(newPattern);
        Date dt = new Date(millis);
        return sdf.format(dt);
    }

    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }
    public static Long getMillByFormatDate(String payTime) {
        Long time = null;
        if (TextUtils.isEmpty(payTime)) {
            return time;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT3, Locale.CHINA);
        try {
            Date date = simpleDateFormat.parse(payTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
