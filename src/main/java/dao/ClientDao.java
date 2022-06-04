package dao;

import model.Client;
import model.ClientState;
import utils.SQLiteUtils;

import java.util.Collection;

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

    /**
     * @return Clients
     */
    public Collection<Client> getClients() {
        return SQLiteUtils.getInstance().selectClients();
    }

    /**
     * add client
     * @param client the client
     * @return the result as int
     */
    public int addClient(Client client) {
        if (!exist(client.getId())) {
            return SQLiteUtils.getInstance().insertClient(client);
        } else {
            return 1;
        }
    }

    /**
     * check the existence of client with the specific id
     * @param id the client id.
     * @return existence boolean
     */
    public boolean exist(long id) {
        return SQLiteUtils.getInstance().selectClientChatId(id) != null;
    }

    /**
     * search for a client
     * @param id the client id
     * @return the client
     */
    public Client searchById(long id) {
        return SQLiteUtils.getInstance().selectClient(id);
    }

    /**
     * search for a client
     * @param username the client username
     * @return the client
     * @deprecated not efficient.
     */
    @Deprecated
    public Client searchByUsername(String username) {
        Collection<Client> clientsCollection = SQLiteUtils.getInstance().selectClients();
        for (Client client : clientsCollection) {
            try {
                if (client.getTelegramUser().getUserName().equalsIgnoreCase(username))
                    return client;
            } catch (NullPointerException e) {
               // who cares??
            }
        }
        return null;
    }

    /**
     * search for a client
     * @param shortDeepLink the client's short deeplink
     * @return the client.
     */
    public Client searchByDeepLink(String shortDeepLink) {
        return SQLiteUtils.getInstance().selectClient(shortDeepLink);
    }

    /**
     * set new deeplink for the client.
     * @param id the client's id.
     * @param lDeepLink long deeplink
     * @param shDeepLink short deeplink
     * @return the result as int
     */
    public int setDeepLink(long id, String lDeepLink, String shDeepLink) {
        return SQLiteUtils.getInstance().updateClientDeepLink(id, lDeepLink, shDeepLink);
    }

    /**
     * set admin ability boolean
     * @param id the client's id
     * @param admin the admin ability boolean
     * @return the result as int
     */
    public int setAdmin(long id, boolean admin) {
        return SQLiteUtils.getInstance().updateClientAdmin(id, admin);
    }

    /**
     * set contact for the client
     * @param id the client's id
     * @param contactId the contact id
     * @return the result as int
     */
    public int setContact(long id, long contactId) {
        return SQLiteUtils.getInstance().updateClientContact(id, contactId);
    }

    /**
     * set the client's state
     * @param id the client's id
     * @param clientState the client state
     * @return the result as int
     */
    public int setClientState(long id, ClientState clientState) {
        return SQLiteUtils.getInstance().updateClientState(id, clientState);
    }

    /**
     * set the client's contact message id
     * @param id the client's id
     * @param messageId the messgae id
     * @return the result as int
     */
    public int setContactMessageId(long id, Integer messageId) {
        return SQLiteUtils.getInstance().updateClientContactMessageId(id, messageId);
    }
}
