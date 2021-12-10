package model;

import db.dao.ClientDao;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;

public class Client implements Serializable {
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
        ClientDao.getInstance().rewriteClients();
    }

    public boolean hasDeepLink() {
        return deepLink != null;
    }
}
