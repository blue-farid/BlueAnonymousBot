package log;

import dao.ClientDao;
import model.Client;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.command.Command;
import utils.TimeUtils;

import java.util.Collection;

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
    public static void printNewRequestInfo(Message message, boolean printTime) {
        System.out.print("- new Request: " +
                "\n" + "\t" + "from: " +
                message.getFrom().getUserName());
        if (printTime) {
            System.out.println("\t" + TimeUtils.getInstance().getCurrentDateAndTimeString());
        } else {
            System.out.println();
        }
    }

    public static void printNewRequestInfo(Message message, Command command
            , boolean printTime) {
        System.out.print("- new Request: " +
                command.getClass().getSimpleName() +
                "\n" + "\t" + "from: " +
                message.getFrom().getUserName());
        if (printTime) {
            System.out.println("\t" + TimeUtils.getInstance().getCurrentDateAndTimeString());
        } else {
            System.out.println();
        }
    }

    /**
     * print all of the users that use the bot on console
     */
    public static void printAllUsers() {
        Collection<Client> clientsCollection = ClientDao.getInstance().getClients();
        for (Client client: clientsCollection) {
            System.out.println("##############\n");
            System.out.println(client.getTelegramUser());
            System.out.println("\n##############");
        }
    }

    public static void printUser(Client client) {
        System.out.println(client.getTelegramUser());
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void print(Object opj) {
        System.out.print(opj);
    }

    public static void initMessage() {
        System.out.println("- the bot runs successfully!\n".concat(
                "- OS: ".concat(
                        utils.Common.getInstance().getOsName()
                )
        ));
    }
}
