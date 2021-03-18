package com.gis.application.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
    /**
     * 时间戳转日期
     *
     * @param timestamp 时间戳，单位(ms)
     * @return 日期字符串，格式为yyyy-MM-dd
     */
    public static String transformTimestampToDateString(long timestamp, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
    }

    /**
     * 日期转时间戳
     *
     * @param date    日期字符串
     * @param pattern 日期字符串格式
     * @return 时间戳，单位(ms)
     */
    public static long transformDateStringToTimestamp(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dt = LocalDate.parse(date, formatter).atStartOfDay();
        return LocalDateTime.from(dt).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 日期字符串转日期
     *
     * @param date    日期字符串
     * @param pattern 日期字符串格式
     * @return 日期
     */
    public static LocalDateTime transformDateStringToDate(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dt = LocalDate.parse(date, formatter).atStartOfDay();
        return LocalDateTime.from(dt);
    }
}
