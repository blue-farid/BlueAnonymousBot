package com.blue_farid.blue_anonymous_bot.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils class.
 *
 * @author Farid Masjedi.
 */

@Component
public class TimeUtils {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * @return the current date as Date object.
     */
    public Date getCurrentDate() {
        return new Date();
    }

    /**
     * @return the current date and time as string.
     */
    public String getCurrentDateAndTimeString() {
        return formatter.format(this.getCurrentDate());
    }
}
