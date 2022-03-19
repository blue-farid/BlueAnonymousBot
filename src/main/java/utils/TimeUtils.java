package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils class.
 *
 * @author Farid Masjedi.
 */
public class TimeUtils {
    private static TimeUtils instance;
    private final SimpleDateFormat formatter;
    private TimeUtils() {
        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public static TimeUtils getInstance() {
        if (instance == null)
            instance = new TimeUtils();
        return instance;
    }

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
