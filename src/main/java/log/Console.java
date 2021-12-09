package log;

import db.FileUtils;
import db.dao.UserDao;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

/**
 * Console class
 * this class used to print some information in console.
 */
public class Console {
    /**
     * print request info on console
     * @param update
     */
    public static void printNewRequestInfo(Update update) {
        System.out.println("- new Request:" +
                "\n" + "\t" + "from: " +
                update.getMessage().getFrom().getUserName());
    }

    /**
     * print all of the users that use the bot on console
     */
    public static void printAllUsers() {
        List<User> users = UserDao.getInstance().getUsers();
        for (User user: users) {
            System.out.println(user);
        }
    }
}
