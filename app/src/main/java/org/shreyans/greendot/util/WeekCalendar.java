package org.shreyans.greendot.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeekCalendar {

    public static int getCurrentWeek() {
        Calendar cal = new GregorianCalendar();
        Date date = new Date();
        cal.setTime(date);
        int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
        return currentWeek;
    }
}