package service;

import dao.ClientDao;
import model.Client;
import model.ClientState;

public class ClientService {
    private static ClientService instance;

    private ClientService() {}

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public void setDeepLink(Client client, String longDeepLink) {
        // short deeplink will be handled by this too
        client.setLongDeepLink(longDeepLink);
        ClientDao.getInstance().setDeepLink(client.getId(), longDeepLink, client.geId());
    }

    public void setAdmin(Client client, boolean admin) {
        client.setAdmin(admin);
        ClientDao.getInstance().setAdmin(client.getId(), admin);
    }

    public void setContact(Client client, long contact) {
        client.setContactId(contact);
        ClientDao.getInstance().setContact(client.getId(), contact);
    }


    public void setClientState(Client client, ClientState clientState) {
        client.setClientState(clientState);
        ClientDao.getInstance().setClientState(client.getId(), clientState);
    }
    public Client getContact(Client client) {
        return ClientDao.getInstance().searchById(client.getContactId());
    }
}
