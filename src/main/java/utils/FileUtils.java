package utils;

import model.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * the FilePath enum
 * all of the file paths should be defined here
 */
enum FilePath {
    BOT_CLIENTS("files\\db\\clients_bot.bin");

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
    private static FileUtils instance;
    private final File botClientsFile = new File(FilePath.BOT_CLIENTS.getValue());

    private FileUtils() {
    }

    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    public synchronized void writeTelegramUsers(List<Client> clients) {
        initializeFile(botClientsFile);
        ObjectOutputStream out = getObjectOutputStream(botClientsFile);
        try {
            assert out != null;
            out.writeObject(clients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Client> readTelegramUsers() {
        initializeFile(botClientsFile);
        ObjectInputStream in = getObjectInputStream(botClientsFile);
        List<Client> clients = new ArrayList<>();
        try {
            clients = (List<Client>) in.readObject();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private void initializeFile(File file) {
        if (!file.exists()) {
            try {
                new File(file.getPath().replace(
                        "clients_bot.bin", "")).mkdirs();
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
