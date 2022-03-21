package utils;

import java.io.*;

/**
 * The common utils
 * the usage methods that are not belong in any specific class, are here.
 *
 * @author Farid Masjedi
 */
public class Common {
    private static Common instance;
    private Boolean botRunsOnWindows;

    private Common() {
    }

    public static Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }

    /**
     * @return operating system name.
     */
    public String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * @return true if bot runs on Windows, else false.
     */
    public boolean isBotRunsOnWindows() {
        if (this.botRunsOnWindows == null) {
            this.botRunsOnWindows = getOsName().contains("Windows");
        }
        return this.botRunsOnWindows;
    }

    /**
     * convert an object to bytes.
     * @param o the object.
     * @return ByteArrayInputStream.
     */
    public ByteArrayInputStream objectToBinaryInputStream(Object o) {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bao);
            out.writeObject(o);
            return new ByteArrayInputStream(bao.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * convert bytes to an object.
     * @param inputStream the input stream that contain bytes.
     * @return the Object.
     */
    public Object binaryInputStreamToObject(InputStream inputStream) {
        try {
            ObjectInputStream in = new ObjectInputStream(inputStream);
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
