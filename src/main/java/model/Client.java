package model;

import dao.ClientDao;
import org.telegram.telegrambots.meta.api.objects.User;
import telegram.command.AnonymousLinkCommand;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {
    private final User telegramUser;
    private String longDeepLink;
    private String shortDeepLink;
    private final Long chatId;
    private ClientState clientState;
    private Client Contact;
    private boolean admin;

    public Client(User user, Long chatId) {
        this.telegramUser = user;
        this.chatId = chatId;
        this.clientState = ClientState.NORMAL;
        this.admin = false;
    }

    public User getTelegramUser() {
        return telegramUser;
    }

    public String getLongDeepLink() {
        if (longDeepLink == null) {
            setLongDeepLink(AnonymousLinkCommand.
                    generateAnonymousLink());
        }
        return longDeepLink;
    }

    public String getShortDeepLink() {
        if (shortDeepLink == null) {
            setShortDeepLink(getLongDeepLink().substring(
                    getLongDeepLink().indexOf("=") + 1));
        }
        return shortDeepLink;
    }


    private void setShortDeepLink(String shortDeepLink) {
        this.shortDeepLink = shortDeepLink;
        ClientDao.getInstance().rewriteClients();
    }

    public void setLongDeepLink(String longDeepLink) {
        this.longDeepLink = longDeepLink;
        ClientDao.getInstance().rewriteClients();
    }

    public Long getChatId() {
        return chatId;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    public void setContact(Client contact) {
        Contact = contact;
    }

    public Client getContact() {
        return Contact;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o) ||
                ((o instanceof Client) &&
                        ((Client) o).telegramUser.getId().equals(
                                this.telegramUser.getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramUser);
    }

    public boolean hasDeepLink() {
        return longDeepLink != null;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return telegramUser.getUserName();
    }
}

