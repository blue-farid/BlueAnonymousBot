package model;

import org.telegram.telegrambots.meta.api.objects.User;
import utils.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Column(name = "ID")
    private long id;
    @NotNull
    @Column(name = "USERNAME")
    private String username;
    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstname;
    @NotNull
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "SHORT_DEEPLINK")
    private String deepLink;
    @NotNull
    @Column(name = "STATE")
    private ClientState clientState;
    @Column(name = "CONTACT")
    private Long contactId;
    @Column(name = "CONTACT_MESSAGE")
    private Integer contactMessageId;
    @NotNull
    @Column(name = "ADMIN")
    private boolean admin;
    @NotNull
    @Column(name = "TELEGRAM_USER")
    private User telegramUser;
    @Transient
    private String clientInfo;

    public Client() {

    }

    public Client(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.clientState = ClientState.NORMAL;
        this.admin = false;
        this.telegramUser = user;
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

    public String getFirstname() {
        return firstname;
    }

    public String getUsername() {
        return username;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getContactMessageId() {
        return contactMessageId;
    }

    public void setContactMessageId(Integer contactMessageId) {
        this.contactMessageId = contactMessageId;
    }

    public User getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(User telegramUser) {
        this.telegramUser = telegramUser;
    }
}

