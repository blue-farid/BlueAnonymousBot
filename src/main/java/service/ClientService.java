package service;

import dao.ClientDao;
import model.Client;
import model.ClientState;

import java.util.Collection;

/**
 * The Client service.
 */
public class ClientService {
    private static ClientService instance;

    private ClientService() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    /**
     * Sets deep link.
     *
     * @param client       the client
     * @param deepLink the long deep link
     */
    public void setDeepLink(Client client, String deepLink) {
        client.setDeepLink(deepLink);
        ClientDao.getInstance().setDeepLink(client.getId(), deepLink);
    }

    /**
     * Sets admin.
     *
     * @param client the client
     * @param admin  the admin
     */
    public void setAdmin(Client client, boolean admin) {
        client.setAdmin(admin);
        ClientDao.getInstance().setAdmin(client.getId(), admin);
    }

    /**
     * Sets contact.
     *
     * @param client  the client
     * @param contact the contact
     */
    public void setContact(Client client, long contact) {
        client.setContactId(contact);
        ClientDao.getInstance().setContact(client.getId(), contact);
    }


    /**
     * Sets client state.
     *
     * @param client      the client
     * @param clientState the client state
     */
    public void setClientState(Client client, ClientState clientState) {
        client.setClientState(clientState);
        ClientDao.getInstance().setClientState(client.getId(), clientState);
    }

    /**
     * Gets contact.
     *
     * @param client the client
     * @return the contact
     */
    public Client getContact(Client client) {
        return ClientDao.getInstance().searchById(client.getContactId());
    }

    /**
     * Sets contact message id.
     *
     * @param client    the client
     * @param messageId the message id
     */
    public void setContactMessageId(Client client, Integer messageId) {
        client.setContactMessageId(messageId);
        ClientDao.getInstance().setContactMessageId(client.getId(), messageId);
    }

    /**
     * Gets clients.
     *
     * @return the clients
     */
    public Collection<Client> getClients() {
        return ClientDao.getInstance().getClients();
    }

    public int addClient(Client client) {
        if (!ClientDao.getInstance().clientExist(client.getId())) {
            return ClientDao.getInstance().insertClient(client);
        }
        return 1;
    }

    public Client getClientById(long id) {
        return ClientDao.getInstance().searchById(id);
    }

    public Client getClientByUsername(String username) {
        return ClientDao.getInstance().searchByUsername(username);
    }

    public Client getClientByDeepLink(String deepLink) {
        return ClientDao.getInstance().searchByDeepLink(deepLink);
    }
}
