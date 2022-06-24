package console;

import dao.ClientDao;
import model.Client;
import service.ClientService;
import utils.FileUtils;

import java.util.Scanner;

/**
 * Console Reader class.
 * reads console inputs to interact with the admin.
 * @author Farid Masjedi
 */
public class ConsoleReader implements Runnable {
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int res = read(scanner.nextLine());
            if (res == 0) {
                return;
            }
        }
    }

    /**
     * commands {
     *     exit -> terminate the bot
     *     set admin [client_id] [boolean] -> set admin true or false
     *     clear -> clear the screen
     *     set deeplink [client_id] [deeplink] -> set deeplink
     * }
     * @param in the input
     * @return the result as int.
     */
    private static int read(String in) {
        String[] commands = in.split(" ");
        try {
            if (commands[0].equals("exit")) {
                System.exit(0);
            } else if (commands[0].equals("clear")) {
                ConsoleWriter.clearScreen();
            } else if (commands[0].equals("set")) {
                if (commands[1].equals("admin")) {
                    long id = Long.parseLong(commands[2]);
                    boolean b = Boolean.parseBoolean(commands[3]);
                    Client client = ClientDao.getInstance().searchById(id);
                    ClientService.getInstance().setAdmin(client, b);
                } else if (commands[1].equals("deeplink")) {
                    int id = Integer.parseInt(commands[2]);
                    String deeplink = commands[3];
                    Client client = ClientDao.getInstance().searchById(id);
                    ClientService.getInstance().setDeepLink(client, deeplink);
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            ConsoleWriter.println("- console.ConsoleReader: bad input!");
            return -1;
        }
        return 1;
    }
}
