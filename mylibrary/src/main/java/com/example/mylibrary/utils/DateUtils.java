package com.example.mylibrary.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/12/7.
 */

public class DateUtils {

    /**
     * 指定日期格式 yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_1 = "yyyyMMddHHmmss";

    /**
     * 指定日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 指定日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 指定日期格式 yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * 指定日期格式 yyyy-MM-dd
     */
    public static final String DATE_FORMAT_4 = "yyyy-MM-dd";

    /**
    * 根据时间戳转成指定的format格式
    * @param timeMillis
    * @param format
    * @return
            */
    public static String formatDate(String timeMillis, String format) {
        Date date = null;
        if (!TextUtils.isEmpty(timeMillis)) {
            try
            {
                date = new Date(Long.parseLong(timeMillis));
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
                date = new Date();
            }
        } else {
            date = new Date();
        }

        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(date);
    }

    /**
     * 获取时间
     * @param timeMillis
     * @param format
     * @return
     */
    public static String formatDate(long timeMillis, String format) {
        Date date = null;
        if (timeMillis > 0) {
            date = new Date(timeMillis);
        } else {
            date = new Date();
        }

        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(date);
    }
    public static String getCurrentDate(){
        Date data = new Date(System.currentTimeMillis());
        final  SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return format.format(data);
    }
}
