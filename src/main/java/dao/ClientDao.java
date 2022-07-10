package dao;

import model.Client;
import model.ClientState;
import utils.SQLUtils;

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
        return SQLUtils.getInstance().selectClients();
    }

    /**
     * add client
     * @param client the client
     * @return the result as int
     */
    public int addClient(Client client) {
        if (!exist(client.getId())) {
            return SQLUtils.getInstance().insertClient(client);
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
        return SQLUtils.getInstance().selectClientChatId(id) != null;
    }

    /**
     * search for a client
     * @param id the client id
     * @return the client
     */
    public Client searchById(long id) {
        return SQLUtils.getInstance().selectClient(id);
    }

    /**
     * search for a client
     * @param username the client username
     * @return the client
     * @deprecated not efficient.
     */
    @Deprecated
    public Client searchByUsername(String username) {
        Collection<Client> clientsCollection = SQLUtils.getInstance().selectClients();
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
        return SQLUtils.getInstance().selectClient(shortDeepLink);
    }

    /**
     * set new deeplink for the client.
     * @param id the client's id.
     * @param lDeepLink long deeplink
     * @param shDeepLink short deeplink
     * @return the result as int
     */
    public int setDeepLink(long id, String lDeepLink, String shDeepLink) {
        return SQLUtils.getInstance().updateClientDeepLink(id, lDeepLink, shDeepLink);
    }

    /**
     * set admin ability boolean
     * @param id the client's id
     * @param admin the admin ability boolean
     * @return the result as int
     */
    public int setAdmin(long id, boolean admin) {
        return SQLUtils.getInstance().updateClientAdmin(id, admin);
    }

    /**
     * set contact for the client
     * @param id the client's id
     * @param contactId the contact id
     * @return the result as int
     */
    public int setContact(long id, long contactId) {
        return SQLUtils.getInstance().updateClientContact(id, contactId);
    }

    /**
     * set the client's state
     * @param id the client's id
     * @param clientState the client state
     * @return the result as int
     */
    public int setClientState(long id, ClientState clientState) {
        return SQLUtils.getInstance().updateClientState(id, clientState);
    }

    /**
     * set the client's contact message id
     * @param id the client's id
     * @param messageId the messgae id
     * @return the result as int
     */
    public int setContactMessageId(long id, Integer messageId) {
        return SQLUtils.getInstance().updateClientContactMessageId(id, messageId);
    }
}
