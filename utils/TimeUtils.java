package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static long getCurrentUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public static String getCurrentTime(final String timeFormatTemplate) {
        return new SimpleDateFormat(timeFormatTemplate).format(new Date());
    }
}
