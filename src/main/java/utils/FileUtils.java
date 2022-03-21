package utils;

import model.Client;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

/**
 * the FileUtils singleton class
 *
 * @author Farid Masjedi
 */
public class FileUtils {
    private static FileUtils instance;
    private final File botClientsFile = new File(FilePath.BOT_CLIENTS.getValue());

    private FileUtils() {
    }public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    /**
     * write bot clients hashmap to the file.
     * @param clients the clients.
     * @deprecated now we use sqlite instead files.
     */
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

    /**
     * read bot clients from the file.
     * @return the clients hashmap.
     * @deprecated now we use sqlite instead file.
     */
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

    /**
     * create clients file if not exist.
     * @param file the file
     * @deprecated now we use sqlite instead file.
     */
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

    public void initializeDBFile() {
        File file = new File(FilePath.DATABASE.getValue());
        if (!file.exists()) {
            try {
                new File(file.getPath().replace(
                        "blue-anonymous-bot.db", "")).mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get output stream of a file.
     * @param file the file
     * @return ObjectOutputStream of the file
     */
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

    /**
     * get input stream of a file
     * @param file the file
     * @return ObjectInputStream of the file.
     */
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

    /**
     * @return clients file.
     * @deprecated now we use sqlite instead of file.
     */
    @Deprecated
    public File getBotClientsFile() {
        return botClientsFile;
    }

    /**
     * @return the database file (sqlite file).
     */
    public File getDatabaseFile() {
        return new File(FilePath.DATABASE.getValue());
    }


    /**
     * load the properties file.
     * @param filePath the properties file path.
     * @return the Properties.
     */
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

        BOT_CLIENTS("files\\db\\clients_bot.bin"),
        COMMAND_PROPERTIES("commands.properties"),
        DATABASE("files/db/sqlite/blue-anonymous-bot.db"),
        MESSAGE_PROPERTIES("messages.properties");

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
