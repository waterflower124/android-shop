package com.anas.fishday.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Anas on 3/16/2018.
 */

public class DateUtil {

    public static String changeDateFormat(String date) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:sss.SSSZZ")
                .withLocale(Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM  hh:mm a", Locale.getDefault());
        DateTimeZone current = DateTimeZone.getDefault();
        DateTime dateTime = new DateTime(fmt.parseDateTime(date), DateTimeZone.forID(current.getID()));
        Calendar cal = dateTime.toCalendar(Locale.getDefault());
        return simpleDateFormat.format(cal.getTime());
    }
}
