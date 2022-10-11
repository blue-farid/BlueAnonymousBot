package com.blue_farid.blue_anonymous_bot.model;

import lombok.*;
import org.apache.logging.log4j.util.Strings;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The Client class
 *
 * @author Farid Masjedi
 */
@Entity
@Table(name = "CLIENT")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @NotNull
    @Column(name = "ID")
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
    @ToString.Exclude
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

    public boolean hasDeepLink() {
        return Strings.isNotEmpty(this.deepLink);
    }

    public String getClientInfo() {
        if (clientInfo == null) {
            this.clientInfo = "\t- firstName: " + this.firstname + "\n" + "\t- lastName: " + this.lastname + "\n" + "\t- username: " + this.username +
            "- id: " + id;
        }
        return clientInfo;
    }
}

