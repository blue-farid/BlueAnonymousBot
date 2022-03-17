package dao;

import model.Client;
import model.ClientState;
import utils.FileUtils;
import utils.SQLiteUtils;

import java.util.Collection;
import java.util.HashMap;

/**
 * the ClientDao class
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
        if (!exist(client.getId())) {
            return SQLiteUtils.getInstance().insertClient(client);
        } else {
            return 1;
        }
    }

    public boolean exist(long id){
        return SQLiteUtils.getInstance().selectClientChatId(id) != null;
    }

    public Client searchById(long id) {
        return SQLiteUtils.getInstance().selectClient(id);
    }

    @Deprecated
    public Client searchByUsername(String username) {
        Collection<Client> clientsCollection = SQLiteUtils.getInstance().selectClients();
        for (Client client: clientsCollection) {
            if (client.getTelegramUser().getUserName().equals(username))
                return client;
        }
        return null;
    }

    public Client searchByDeepLink(String shortDeepLink) {
        return SQLiteUtils.getInstance().selectClient(shortDeepLink);
    }

    public int setDeepLink(long id, String lDeepLink, String shDeepLink) {
        return SQLiteUtils.getInstance().updateClientDeepLink(id, lDeepLink, shDeepLink);
    }

    public int setAdmin(long id, boolean admin) {
        return SQLiteUtils.getInstance().updateClientAdmin(id, admin);
    }

    public int setContact(long id, long contactId) {
        return SQLiteUtils.getInstance().updateClientContact(id, contactId);
    }

    public int setClientState(long id, ClientState clientState) {
        return SQLiteUtils.getInstance().updateClientState(id, clientState);
    }

    public int setContactMessageId(long id, Integer messageId) {
        return SQLiteUtils.getInstance().updateClientContactMessageId(id, messageId);
    }
}
