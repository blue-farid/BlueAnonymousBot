package db;

import org.telegram.telegrambots.meta.api.objects.User;

import java.io.*;
import java.util.List;

enum FilePath {
    TELEGRAM_USERS("\\files\\db\\telegram_users.bin");

    private final String value;

    FilePath(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

public class FileUtils {
    private File telegramUsersFile;
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
        initializeFile(telegramUsersFile, FilePath.TELEGRAM_USERS.getValue());
        ObjectOutputStream out = getObjectOutputStream(telegramUsersFile);
        try {
            assert out != null;
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> readTelegramUsers() {
        initializeFile(telegramUsersFile, FilePath.TELEGRAM_USERS.getValue());
        ObjectInputStream in = getObjectInputStream(telegramUsersFile);
        List<User> users = null;
        try {
            assert in != null;
            users = (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void initializeFile(File file, String path) {
        if (file == null) {
            file = new File(path);
        }
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
            out = new BufferedOutputStream(new FileOutputStream(file));
            return new ObjectOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ObjectInputStream getObjectInputStream(File file) {
        InputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(telegramUsersFile));
            return new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
