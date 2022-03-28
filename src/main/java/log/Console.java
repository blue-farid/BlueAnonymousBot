package log;

import model.Client;
import org.telegram.telegrambots.meta.api.objects.Message;
import service.ClientService;
import telegram.command.Command;
import utils.TimeUtils;

import java.io.IOException;
import java.util.Collection;

/**
 * Console class
 * this class used to print some information in console.
 *
 * @author Farid Masjedi
 * @author Negar Anabestani
 * @author Aliereza Jabbari
 */
public class Console {
    /**
     * prints request info on the console
     *
     * @param message   the message
     * @param printTime print time boolean
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

    /**
     * prints request info
     *
     * @param message   the message
     * @param command   the command
     * @param printTime print time boolean
     */
    public static void printNewRequestInfo(Message message, Command command
            , boolean printTime) {
        System.out.print("- new Request: " +
                command +
                "\n" + "\t" + "username: " +
                message.getFrom().getUserName() +
                "\n" + "\t" + "name: " +
                message.getFrom().getFirstName() +
                " " + message.getFrom().getLastName());
        if (printTime) {
            System.out.println("\t" + TimeUtils.getInstance().getCurrentDateAndTimeString());
        } else {
            System.out.println();
        }
    }

    /**
     * print all the clients.
     */
    public static void printAllUsers() {
        Collection<Client> clientsCollection = ClientService.getInstance().getClients();
        for (Client client : clientsCollection) {
            System.out.println("##############\n");
            System.out.println(client.getTelegramUser());
            System.out.println("\n##############");
        }
    }

    /**
     * print a client info
     *
     * @param client the client.
     */
    public static void printUser(Client client) {
        System.out.println(client.getTelegramUser());
    }

    /**
     * print an object (end = \n)
     *
     * @param obj the object.
     */
    public static void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * print an object (end = "")
     *
     * @param opj the object.
     */
    public static void print(Object opj) {
        System.out.print(opj);
    }

    /**
     * prints the initial message after running the bot.
     *
     * @param version the version of the bot.
     */
    public static void initMessage(String version) {
        System.out.println("- the bot is running!\n".concat(
                "- Version: ".concat(version.concat("\n")).concat(
                        "- OS: ".concat(
                                utils.Common.getInstance().getOsName()
                        )
                )
        ));
    }

    public static void printAsciiBlueAnonymousBot(){
        System.out.println(" ____  _                 _                                                        ____        _   \n" +
                "| __ )| |_   _  ___     / \\   _ __   ___  _ __  _   _ _ __ ___   ___  _   _ ___  | __ )  ___ | |_ \n" +
                "|  _ \\| | | | |/ _ \\   / _ \\ | '_ \\ / _ \\| '_ \\| | | | '_ ` _ \\ / _ \\| | | / __| |  _ \\ / _ \\| __|\n" +
                "| |_) | | |_| |  __/  / ___ \\| | | | (_) | | | | |_| | | | | | | (_) | |_| \\__ \\ | |_) | (_) | |_ \n" +
                "|____/|_|\\__,_|\\___| /_/   \\_\\_| |_|\\___/|_| |_|\\__, |_| |_| |_|\\___/ \\__,_|___/ |____/ \\___/ \\__|\n" +
                "                                                |___/     ");
    }

    public static void printAsciiLogo() {
        System.out.println(
                "               .:lodoc:.                          \n" +
                "             cOKKKKKKOxKk;                        \n" +
                "            xKKKKKKKKKdkKKk'                      \n" +
                "           :KKKKKKKKKK0OKKK0,                     \n" +
                "           OKKKKKKKKKKKKKKKK0'                    \n" +
                "          'KKKKKKKKKKKKKKKKKK0'                   \n" +
                "          lKKKKKOxoxO:c.ldx;dxk.                  \n" +
                "          dKKK0d..x,',  l' :KKKo                  \n" +
                "          xKKKk .; ,;;cll;;0KKd.                  \n" +
                "          oKKK0l:   .''','kK0;                    \n" +
                "          .0KKo:oo,.  ;k.kKKl                     \n" +
                "         .'dkOk' .c;;d:00OK0:x:ll;..              \n" +
                "        c00d0kxkk, ,x. xc00cldddddddoood:c        \n" +
                "       .0KK:0KKK0x. :. .00x0KKKKKKK00KKKO;c.      \n" +
                "       lKKK:kKKKKKO. . dxKK0'.....''..x:,,:o      \n" +
                "      .0K0k,dKKKKKKk: 'c,KK0  .  ...  :c'':d      \n" +
                "      dKkoxkclxdOKKc.lc .0K0       ..  k00x       \n" +
                "      dKKKo,cl::OKKo  '  xK0       .   kKKd       \n" +
                "       xlc00OOl,lKKo  ,  :K0  .  ..    xKKc       \n" +
                "       .:dl;.'d00KKl  c. .00.          xOK.       \n" +
                "         ,dccl..:okc      dK0kkxxddooloOoO        \n" +
                "                          .''',;:codxO0:Ol        \n" +
                "                                       .;   ");
    }

    /**
     * clears the console.
     */
    public static void clearScreen() {
        try {
            if (utils.Common.getInstance().isBotRunsOnWindows()) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ignored) {

        }
    }
}
