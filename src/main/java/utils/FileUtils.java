package utils;

import model.Client;
import org.apache.commons.io.output.FileWriterWithEncoding;
import telegram.command.Command;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

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

    public void monitorSendMessageToContact(String className, String message, Client client) {
        new Thread(new MonitorMessagesHandler(client, className, message)).start();
    }

    /**
     * the FilePath enum
     * all the file paths should be defined here
     */
    public enum FilePath {

        BOT_CLIENTS("files/db/clients_bot.bin"),
        COMMAND_PROPERTIES("commands.properties"),
        DATABASE("files/db/sqlite/blue-anonymous-bot.db"),
        MONITORING("files/monitor/"),
        MESSAGE_PROPERTIES("messages.properties");

        private String value;


        FilePath(String value) {
            this.value = value;
            formatBasedOnOs();
        }

        public String getValue() {
            return value;
        }

        private void formatBasedOnOs() {
            if (utils.Common.getInstance().isBotRunsOnWindows()) {
                this.value = value.replace("/", "\\");
            }
        }
    }

    private record MonitorMessagesHandler(Client client, String className,
                                          String message) implements Runnable {

        @Override
        public void run() {
            String path = FilePath.MONITORING.getValue().concat(className.concat("/"));
            long firstId = client.getId();
            long secondId = client.getContactId();
            if (firstId > secondId) {
                long temp = secondId;
                secondId = firstId;
                firstId = temp;
            }
            // create dirs at first .
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            // now create the file.
            path = path.concat(String.valueOf(firstId)).concat("-").concat(String.valueOf(secondId).concat(".txt"));
            file = new File(path);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try(FileWriterWithEncoding out = new FileWriterWithEncoding(file, StandardCharsets.UTF_8, true);) {
                String str = TimeUtils.getInstance().getCurrentDateAndTimeString().
                        concat(" " + String.valueOf(client.getId()).concat(":{\n").concat("\t" + message)
                                .concat("\n}\n"));
                out.write(str);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
