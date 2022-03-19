package utils;

import model.Client;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    @Deprecated
    public synchronized void writeTelegramUsers(HashMap<Long, Client> clients) {
        initializeClientBotFile(botClientsFile);
        ObjectOutputStream out = getObjectOutputStream(botClientsFile);
        try {
            if (out == null)
                return;
            out.writeObject(clients);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public HashMap<Long, Client> readTelegramUsers() {
        initializeClientBotFile(botClientsFile);
        ObjectInputStream in = getObjectInputStream(botClientsFile);
        HashMap<Long, Client> clients = new HashMap<>();
        try {
            clients = (HashMap<Long, Client>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Deprecated
    private void initializeClientBotFile(File file) {
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

    @Deprecated
    public File getBotClientsFile() {
        return botClientsFile;
    }

    public File getDatabaseFile() {
        return new File(FilePath.DATABASE.getValue());
    }


    public Properties loadProperties(FilePath filePath) {
        Properties properties = new Properties();
        try (InputStream in = ClassLoader
                .getSystemResourceAsStream(filePath.getValue())) {
            assert in != null;
            properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * the FilePath enum
     * all the file paths should be defined here
     */
    public enum FilePath {
        BOT_CLIENTS("files/db/clients_bot.bin"),
        BOT_PROPERTIES("blue_anonymous_bot.properties"),
        DATABASE("files/db/sqlite/blue-anonymous-bot.db");

        private final String value;

        FilePath(String value) {
            if (!utils.Common.getInstance().isBotRunsOnWindows()) {
                this.value = value;
            } else {
                this.value = value.replace("/", "\\");
            }
        }

        public String getValue() {
            return value;
        }
    }
}
