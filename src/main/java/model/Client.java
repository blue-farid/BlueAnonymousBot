package model;

import dao.ClientDao;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {
    private final User telegramUser;
    private String longDeepLink;
    private String shortDeepLink;
    private final Long chatId;

    public Client(User user, Long chatId) {
        this.telegramUser = user;
        this.chatId = chatId;
    }

    public User getTelegramUser() {
        return telegramUser;
    }

    public String getLongDeepLink() {
        return longDeepLink;
    }

    public String getShortDeepLink() {
        return shortDeepLink;
    }

    private void setShortDeepLink(String shortDeepLink) {
        this.shortDeepLink = shortDeepLink;
    }

    public void setLongDeepLink(String longDeepLink) {
        this.longDeepLink = longDeepLink;
        this.setShortDeepLink(longDeepLink.substring(
                longDeepLink.indexOf("=") + 1
        ));
        ClientDao.getInstance().rewriteClients();
    }

    public Long getChatId() {
        return chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return telegramUser.equals(client.telegramUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramUser);
    }

    public boolean hasDeepLink() {
        return longDeepLink != null;
    }
}
