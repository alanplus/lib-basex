package com.lib.basex.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alan
 * 时 间：2020/10/20
 * 简 述：<功能简述>
 */
public class LTimeUtils {

    public static final int ONE_SECOND_MILLIS = 1000;
    public static final int ONE_MINUTE_MILLIS = 60000;
    public static final int ONE_HOUR_MILLIS = 3600000;
    public static final int ONE_DAY_MILLIS = 86400000;

    @SuppressLint("SimpleDateFormat")
    public static String getTimeStr(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getDurationText(int duration) {
        return getDurationText(duration, "小时", "分", "秒");
    }

    public static String getDurationText(int duration, String hourAfter, String minuteAfter, String secondsAfter) {
        if (duration <= 0) {
            return "数据异常";
        }

        int seconds = duration % 60;
        int minute = duration % 3600 / 60;
        int hour = duration / 3600;

        StringBuilder sb = new StringBuilder();
        if (hour != 0) {
            sb.append(hour);
            sb.append(hourAfter);
        }

        if (minute != 0) {
            sb.append(minute);
            sb.append(minuteAfter);
        }

        if (seconds != 0) {
            sb.append(seconds);
            sb.append(secondsAfter);
        }

        if (sb.length() == 0) {
            sb.append("0");
            sb.append(secondsAfter);
        }
        return sb.toString();

    }
}
