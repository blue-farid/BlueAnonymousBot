package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Date getCurrentDate() {
        return new Date();
    }

    public String getCurrentDateAndTimeString() {
        return formatter.format(this.getCurrentDate());
    }
}
