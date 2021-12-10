package model;

import org.telegram.telegrambots.meta.api.objects.User;

public class Client {
    private final User telegramUser;
    private String deepLink;


    public Client(User user) {
        this.telegramUser = user;
    }

    public User getTelegramUser() {
        return telegramUser;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }
}
