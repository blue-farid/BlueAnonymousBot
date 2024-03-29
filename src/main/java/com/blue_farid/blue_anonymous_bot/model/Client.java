package com.blue_farid.blue_anonymous_bot.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The Client class
 *
 * @author Farid Masjedi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
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

    @JoinColumn
    @OneToMany
    private List<Role> roles;
    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Transient
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    private String clientInfo;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date lastModifiedAt;
    private boolean newJoiner;
    private JoinMethod joinMethod;
    private String joinFrom;
    private String adminDescription;


    public Client(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.clientState = ClientState.NORMAL;
    }

    public boolean hasDeepLink() {
        return Strings.isNotEmpty(this.deepLink);
    }

    public String getClientInfo() {
        if (clientInfo == null) {
            this.clientInfo = "\t- firstName: " + this.firstname + "\n" + "\t- lastName: " + this.lastname + "\n" + "\t- username: " + "@" + this.username +
                    "\n\t- id: " + id;
        }
        return clientInfo;
    }

    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }
}

