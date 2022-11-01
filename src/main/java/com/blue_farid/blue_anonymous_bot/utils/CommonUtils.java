package com.blue_farid.blue_anonymous_bot.utils;

/**
 * The common utils
 * the usage methods that are not belong in any specific class, are here.
 *
 * @author Farid Masjedi
 */

public class CommonUtils {
    private static Boolean botRunsOnWindows;

    /**
     * @return operating system name.
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * @return true if bot runs on Windows, else false.
     */
    public static boolean isBotRunsOnWindows() {
        if (botRunsOnWindows == null) {
            botRunsOnWindows = getOsName().contains("Windows");
        }
        return botRunsOnWindows;
    }

    public static String readyForLog(String str) {
        return "\t- " + str + "\n";
    }
}
