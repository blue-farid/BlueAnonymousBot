package log;

import dao.ClientDao;
import model.Client;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.command.Command;

import java.util.Collection;
import java.util.HashMap;

/**
 * Console class
 * this class used to print some information in console.
 */
public class Console {
    /**
     * print request info on console
     *
     * @param message
     */
    public static void printNewRequestInfo(Message message) {
        System.out.println("- new Request: " +

                "\n" + "\t" + "from: " +
                message.getFrom().getUserName());
    }

    public static void printNewRequestInfo(Message message, Command command) {
        System.out.println("- new Request: " +
                command.getClass().getSimpleName() +
                "\n" + "\t" + "from: " +
                message.getFrom().getUserName());
    }

    /**
     * print all of the users that use the bot on console
     */
    public static void printAllUsers() {
        HashMap<Long, Client> clients = ClientDao.getInstance().getClients();
        Collection<Client> clientsCollection = clients.values();
        for (Client client: clientsCollection) {
            System.out.println(client.getTelegramUser());
        }
    }

    public static void printUser(Client client) {
        System.out.println(client.getTelegramUser());
    }
}
