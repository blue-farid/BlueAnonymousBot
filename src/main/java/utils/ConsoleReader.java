package utils;

import dao.ClientDao;
import model.Client;

import java.util.Scanner;

public class ConsoleReader implements Runnable{
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
     *     sleep -> shuts down the readMode
     *     set admin [client_id] [boolean] -> set admin true or false
     * }
     * @param in
     * @return
     */
    private static int read(String in) {
        String[] commands = in.split(" ");
        try {
            if (commands[0].equals("exit")) {
                System.exit(0);
            } else if (commands[0].equals("sleep")) {
                return 0;
            }
            if (commands[0].equals("set")) {
                if (commands[1].equals("admin")) {
                    int id = Integer.parseInt(commands[2]);
                    boolean b = Boolean.parseBoolean(commands[3]);
                    Client client = ClientDao.getInstance().searchById(id);
                    client.setAdmin(b);
                }
            }
        } catch (Exception e) {
            log.Console.println("- ConsoleReader: bad input!");
            return -1;
        }
        return 1;
    }
}
