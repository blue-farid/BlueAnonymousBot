package dao;

import utils.FileUtils;
import model.Client;

import java.util.List;

/**
 * the UserDao class
 */
public class ClientDao {
    private static ClientDao instance;
    private final List<Client> clients;

    private ClientDao() {
        clients = FileUtils.getInstance().readTelegramUsers();
    }

    public static ClientDao getInstance() {
        if (instance == null)
            instance = new ClientDao();
        return instance;
    }

    public List<Client> getClients() {
        return clients;
    }

    public int addClient(Client client) {
        if (clients.contains(client)) {
            return 1;
        }

        clients.add(client);
        FileUtils.getInstance().writeTelegramUsers(clients);
        return 0;
    }

    public Client searchById(long id) {
        for (Client client : clients) {
            if (client.getTelegramUser().getId().equals(id))
                return client;
        }
        return null;
    }

    public Client searchByUsername(String username) {
        for (Client client : clients) {
            if (client.getTelegramUser().getUserName().equals(username))
                return client;
        }
        return null;
    }

    public Client searchByDeepLink(String deepLink) {
        for (Client client: clients) {
            if (client.hasDeepLink() && client.getShortDeepLink().equals(deepLink))
                return client;
        }
        return null;
    }

    public void rewriteClients() {
        FileUtils.getInstance().writeTelegramUsers(clients);
    }
}
