package dao;

import model.Client;
import model.ClientState;
import utils.HibernateUtils;

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
        return HibernateUtils.getInstance().selectClients();
    }

    /**
     * add client
     * @param client the client
     * @return the result as int
     */
    public int insertClient(Client client) {
        return HibernateUtils.getInstance().insertClient(client);
    }

    /**
     * check the existence of client with the specific id
     * @param id the client id.
     * @return existence boolean
     */
    public boolean clientExist(long id) {
        return HibernateUtils.getInstance().selectClientById(id) != null;
    }

    /**
     * search for a client
     * @param id the client id
     * @return the client
     */
    public Client searchById(long id) {
        return HibernateUtils.getInstance().selectClientById(id);
    }

    /**
     * search for a client
     * @param username the client username
     * @return the client
     */
    public Client searchByUsername(String username) {
        return HibernateUtils.getInstance().selectClientByUsername(username);
    }

    /**
     * search for a client
     * @param shortDeepLink the client's short deeplink
     * @return the client.
     */
    public Client searchByDeepLink(String shortDeepLink) {
        return HibernateUtils.getInstance().selectClientByDeepLink(shortDeepLink);
    }

    /**
     * set new deeplink for the client.
     * @param id the client's id.
     * @param dee long deeplink
     * @param shDeepLink short deeplink
     * @return the result as int
     */
    public int setDeepLink(long id, String deepLink) {
        return HibernateUtils.getInstance().updateClientDeepLink(id, deepLink);
    }

    /**
     * set admin ability boolean
     * @param id the client's id
     * @param admin the admin ability boolean
     * @return the result as int
     */
    public int setAdmin(long id, boolean admin) {
        return HibernateUtils.getInstance().updateClientAdmin(id, admin);
    }

    /**
     * set contact for the client
     * @param id the client's id
     * @param contactId the contact id
     * @return the result as int
     */
    public int setContact(long id, long contactId) {
        return HibernateUtils.getInstance().updateClientContact(id, contactId);
    }

    /**
     * set the client's state
     * @param id the client's id
     * @param clientState the client state
     * @return the result as int
     */
    public int setClientState(long id, ClientState clientState) {
        return HibernateUtils.getInstance().updateClientState(id, clientState);
    }

    /**
     * set the client's contact message id
     * @param id the client's id
     * @param messageId the messgae id
     * @return the result as int
     */
    public int setContactMessageId(long id, Integer messageId) {
        return HibernateUtils.getInstance().updateClientContactMessageId(id, messageId);
    }

    public int updateClient(Client client) {
        return HibernateUtils.getInstance().updateClient(client);
    }
}
