package log;

import dao.ClientDao;
import model.Client;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        List<Client> clients = ClientDao.getInstance().getClients();
        for (Client client: clients) {
            System.out.println(client.getTelegramUser());
        }
    }
}
