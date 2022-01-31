package utils;

/**
 * The common utils
 * the usage methods that are not belong in any specific class, are here.
 * @author Farid Masjedi
 */
public class Common {
    private static Common instance;

    private Common() {}

    public static Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }

    public String getOsName() {
        return System.getProperty("os.name");
    }
}
