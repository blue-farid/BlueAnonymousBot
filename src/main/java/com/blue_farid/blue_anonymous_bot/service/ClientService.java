package com.blue_farid.blue_anonymous_bot.service;

import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.ClientState;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import com.blue_farid.blue_anonymous_bot.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * The Client service.
 */
@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository repository;

    /**
     * Sets deep link.
     *
     * @param client   the client
     * @param deepLink the long deep link
     */
    public void setDeepLink(Client client, String deepLink) {
        client.setDeepLink(deepLink);
        repository.save(client);
    }

    /**
     * Sets admin.
     *
     * @param client the client
     * @param admin  the admin
     */
    public void setAdmin(Client client, boolean admin) {
        client.setAdmin(admin);
        repository.save(client);
    }

    /**
     * Sets contact.
     *
     * @param client  the client
     * @param contact the contact
     */
    public void setContact(Client client, long contact) {
        client.setContactId(contact);
        repository.save(client);
    }


    /**
     * Sets client state.
     *
     * @param client      the client
     * @param clientState the client state
     */
    public void setClientState(Client client, ClientState clientState) {
        client.setClientState(clientState);
        repository.save(client);
    }

    /**
     * Gets contact.
     *
     * @param client the client
     * @return the contact
     */
    public Client getContact(Client client) {
        return repository.getReferenceById(client.getContactId());
    }

    /**
     * Sets contact message id.
     *
     * @param client    the client
     * @param messageId the message id
     */
    public void setContactMessageId(Client client, Integer messageId) {
        client.setContactMessageId(messageId);
        repository.save(client);
    }

    public void setGender(Client client, Gender gender) {
        client.setGender(gender);
        repository.save(client);
    }

    /**
     * Gets clients.
     *
     * @return the clients
     */
    public List<Client> getClients() {
        return repository.findAll();
    }

    /**
     * add client
     *
     * @param client the client
     */
    public void addClient(Client client) {
        repository.save(client);
    }

    public void updateClient(Client client) {
        repository.updateFirstnameAndLastnameAndTelegramUserAndUsername(client);
    }

    public List<Client> getAllNewJoiners() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        return repository.findAllNewJoiners(calendar.getTime());
    }

    /**
     * returns client by id
     *
     * @param id the id
     * @return the client
     */
    public Client getClientById(long id) {
        return repository.getReferenceById(id);
    }

    /**
     * retrun the client by username
     *
     * @param username the username
     * @return the client
     */
    public Client getClientByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * return the client by deeplink
     *
     * @param deepLink the deeplink
     * @return the client
     */
    public Client getClientByDeepLink(String deepLink) {
        return repository.findByDeepLinkIgnoreCase(deepLink);
    }

    /**
     * return the counts of client
     *
     * @return the count
     */
    public long getClientsCount() {
        return repository.count();
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
