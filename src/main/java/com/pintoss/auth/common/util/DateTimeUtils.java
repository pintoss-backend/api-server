package com.pintoss.auth.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter KOREAN_DATE_FORMAT =
        DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String formatKorean(LocalDateTime dateTime) {
        return dateTime.format(KOREAN_DATE_FORMAT);
    }
}
