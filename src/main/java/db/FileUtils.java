package db;

import org.telegram.telegrambots.meta.api.objects.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * the FilePath enum
 * all of the file paths should be defined here
 */
enum FilePath {
    TELEGRAM_USERS("files\\db\\telegram_users.bin");

    private final String value;

    FilePath(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

/**
 * the FileUtils singleton class
 */
public class FileUtils {
    private final File telegramUsersFile = new File(FilePath.TELEGRAM_USERS.getValue());
    private static FileUtils instance;

    private FileUtils() {
    }

    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    public void writeTelegramUsers(List<User> users) {
        initializeFile(telegramUsersFile);
        ObjectOutputStream out = getObjectOutputStream(telegramUsersFile);
        try {
            assert out != null;
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> readTelegramUsers() {
        initializeFile(telegramUsersFile);
        ObjectInputStream in = getObjectInputStream(telegramUsersFile);
        List<User> users = new ArrayList<>();
        try {
            users = (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void initializeFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ObjectOutputStream getObjectOutputStream(File file) {
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            return new ObjectOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ObjectInputStream getObjectInputStream(File file) {
        InputStream in;
        try {
            in = new FileInputStream(file);
            return new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
