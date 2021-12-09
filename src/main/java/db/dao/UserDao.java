package db.dao;

import db.FileUtils;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public class UserDao {
    private final List<User> users;

    public UserDao() {
        users = FileUtils.getInstance().readTelegramUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public int addUser(User user) {
        if (users.contains(user)) {
            return 1;
        }

        users.add(user);
        FileUtils.getInstance().writeTelegramUsers(users);
        return 0;
    }

    public User searchById(long id) {
        for (User user : users) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }

    public User searchByUsername(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username))
                return user;
        }
        return null;
    }
}
