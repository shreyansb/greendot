package org.shreyans.greendot.util;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;

import java.util.Locale;


public class CalendarHelper {

    private static final String TAG = CalendarHelper.class.getSimpleName();

    // Returns the string to display in the app header
    public static String getWeekHeaderString() {
        LocalDateTime dt = new LocalDateTime();
        Log.d(TAG, "local date time: " + dt);
        int daysLeft = 8 - dt.getDayOfWeek();
        String daysString = getDaysString(daysLeft);
        String dayOfWeek = dt.toString("EEEE");
        String prefixEmoji = "\uD83C\uDF1E";

        return String.format(
                Locale.getDefault(),
                "%s It's %s. %d %s left",
                prefixEmoji, dayOfWeek, daysLeft, daysString);
    }

    public static int getCurrentWeekNumber() {
        DateTime dt = new DateTime(Instant.now());
        return dt.getWeekOfWeekyear();
    }

    private static String getDaysString(int daysLeft) {
        String daysString;
        if (daysLeft == 1) {
            daysString = "day";
        } else {
            daysString = "days";
        }
        return daysString;
    }
}