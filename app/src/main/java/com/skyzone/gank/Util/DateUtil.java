package com.skyzone.gank.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Skyzone on 11/24/2016.
 */
public class DateUtil {

    /***
     * return date of format:yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String parseDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static boolean isSameDay(Date date0, Date date1) {
        Calendar calendar0 = Calendar.getInstance();
        calendar0.setTime(date0);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        return calendar0.compareTo(calendar1) == 0 ? true : false;
    }
}
