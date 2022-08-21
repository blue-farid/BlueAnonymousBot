package model;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.User;
import utils.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The Client class
 *
 * @author Farid Masjedi
 * @author Alireza Jabbari
 */
@Entity
@Table(name = "CLIENT")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @NotNull
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    private long id;
    @NotNull
    @Column(name = "USERNAME", unique = true)
    private String username;
    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstname;
    @NotNull
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "DEEPLINK", unique = true)
    private String deepLink;
    @NotNull
    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private ClientState clientState;
    @Column(name = "CONTACT")
    private Long contactId;
    @Column(name = "CONTACT_MESSAGE")
    @ToString.Exclude
    private Integer contactMessageId;
    @NotNull
    @Column(name = "ADMIN")
    private boolean admin;
    @NotNull
    @Column(name = "TELEGRAM_USER")
    private User telegramUser;
    @Transient
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    private String clientInfo;


    public Client(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.clientState = ClientState.NORMAL;
        this.admin = false;
        this.telegramUser = user;
    }

    public Client(long id, User telegramUser, String deepLink, ClientState clientState, boolean admin, long contactId) {
        this.id = id;
        this.username = telegramUser.getUserName();
        this.firstname = telegramUser.getFirstName();
        this.lastname = telegramUser.getLastName();
        this.deepLink = deepLink;
        this.clientState = clientState;
        this.admin = admin;
        this.contactId = contactId;
    }

    public boolean hasDeepLink() {
        return !StringUtils.getInstance().emptyOrNull(this.deepLink);
    }

    public String getClientInfo() {
        if (clientInfo == null) {
            this.clientInfo = "\t- firstName: " + this.firstname + "\n" + "\t- lastName: " + this.lastname + "\n" + "\t- username: " + this.username;
        }
        return clientInfo;
    }
}

