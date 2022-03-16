package dao;

import model.Client;
import utils.FileUtils;
import utils.SQLiteUtils;

import java.util.Collection;
import java.util.HashMap;

/**
 * the UserDao class
 */
public class ClientDao {
    private static ClientDao instance;

    public static ClientDao getInstance() {
        if (instance == null)
            instance = new ClientDao();
        return instance;
    }

    public Collection<Client> getClients() {
        return SQLiteUtils.getInstance().selectClients();
    }

    public int addClient(Client client) {
        if (clients.containsValue(client)) {
            return 1;
        }

        clients.put(client.getTelegramUser().getId(), client);
        this.rewriteClients();
        return 0;
    }

    public Client searchById(long id) {
        return clients.get(id);
    }

    public Client searchByUsername(String username) {
        Collection<Client> clientsCollection = clients.values();
        for (Client client: clientsCollection) {
            if (client.getTelegramUser().getUserName().equals(username))
                return client;
        }
        return null;
    }

    public Client searchByDeepLink(String deepLink) {
        Collection<Client> clientsCollection = clients.values();
        for (Client client: clientsCollection) {
            if (client.hasDeepLink() && client.getShortDeepLink().equals(deepLink))
                return client;
        }
        return null;
    }

    public void rewriteClients() {
        FileUtils.getInstance().writeTelegramUsers(clients);
    }
}
