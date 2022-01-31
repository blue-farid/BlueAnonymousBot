package utils;

import model.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the FilePath enum
 * all of the file paths should be defined here
 */
enum FilePath {
    BOT_CLIENTS("files\\db\\clients_bot.bin");

    private final String value;

    FilePath(String value) {
        if (Common.getInstance().getOsName().contains("Windows")) {
            this.value = value;
        } else {
            this.value = value.replace("\\", "/");
        }
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

    public synchronized void writeTelegramUsers(HashMap<Long, Client> clients) {
        initializeFile(botClientsFile);
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

    public HashMap<Long, Client> readTelegramUsers() {
        initializeFile(botClientsFile);
        ObjectInputStream in = getObjectInputStream(botClientsFile);
        HashMap<Long, Client> clients = new HashMap<>();
        try {
            clients = (HashMap<Long, Client>) in.readObject();
            in.close();
        } catch (ClassCastException e) {
            try {
                in = getObjectInputStream(botClientsFile);
                clients = (HashMap<Long, Client>)
                        listToMap((ArrayList<Client>) in.readObject());
                in.close();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
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

    public File getBotClientsFile() {
        return botClientsFile;
    }

    private Map<Long, Client> listToMap(List<Client> clientList) {
        Map<Long, Client> map = new HashMap<>();
        for (Client client: clientList) {
            map.put(client.getTelegramUser().getId(), client);
        }
        return map;
    }
}
