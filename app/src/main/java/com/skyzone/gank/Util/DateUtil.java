package com.skyzone.gank.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
}
