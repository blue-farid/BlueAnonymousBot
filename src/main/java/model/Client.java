package model;

import org.telegram.telegrambots.meta.api.objects.User;
import utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Client class
 * @author Farid Masjedi
 * @author Alireza Jabbari
 */
@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {
    @Id
    @Column(name = "ID")
    private final long id;
    @Column(name = "USERNAME")
    private final String username;
    @Column(name = "FIRSTNAME")
    private final String firstname;
    @Column(name = "LASTNAME")
    private final String lastname;
    @Column(name = "SHORT_DEEPLINK")
    private String deepLink;
    @Column(name = "STATE")
    private ClientState clientState;
    @Column(name = "CONTACT")
    private long contactId;
    @Column(name = "ADMIN")
    private boolean admin;
    private String clientInfo;

    public Client(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.clientState = ClientState.NORMAL;
        this.admin = false;
    }

    public Client(long id, User telegramUser, String deepLink,
                  ClientState clientState, boolean admin, long contactId) {
        this.id = id;
        this.username = telegramUser.getUserName();
        this.firstname = telegramUser.getFirstName();
        this.lastname = telegramUser.getLastName();
        this.deepLink = deepLink;
        this.clientState = clientState;
        this.admin = admin;
        this.contactId = contactId;
    }



    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public long getContactId() {
        return contactId;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o) || ((o instanceof Client) && ((Client) o).getId() == this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public boolean hasDeepLink() {
        return !StringUtils.getInstance().emptyOrNull(this.deepLink);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return this.username;
    }

    public long getId() {
        return id;
    }

    public String getClientInfo() {
        if(clientInfo == null) {
            this.clientInfo = "\t- firstName: " + this.firstname +
                    "\n" + "\t- lastName: " + this.lastname +
                    "\n" + "\t- username: " + this.username;
        }
        return clientInfo;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }
}

