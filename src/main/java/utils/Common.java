package utils;

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
