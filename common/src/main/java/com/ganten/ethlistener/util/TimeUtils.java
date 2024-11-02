package com.ganten.ethlistener.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TimeUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将时间戳转换为日期
     *
     * @param date 时间戳
     * @return 日期字符串
     */
    public static String toString(Date date) {
        return sdf.format(date);
    }

    public static Date toDate(String dateStr) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("Failed to parse date string: {}", dateStr, e);
            return null;
        }
    }
}
