import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.BlueAnonymousBot;
import utils.SQLiteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *     ! The BlueAnonymousBot Project !
 *
 * This is a Fake-Copy of a persian telegram ChatBot called 'برنامه ناشناس'
 * The original idea for the robot came about
 * when we wanted to know who was sending us anonymous messages
 * using 'برنامه ناشناس' bot.
 * SO WE WROTE OUR OWN COPY!
 *
 * @author Farid Masjedi
 * @author Negar Anabestani
 * @author Alireza Jabbari
 * @version 5.3.0
 * <p>
 * Telegram_id: @blue_farid
 * github: https://github.com/blue-farid
 * @since 2021/8
 */
public class Main {
    /**
     * the entry point of the Application.
     * @param args the initial arguments.
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0 && args[0].equals("test")) {
               loadTestBot();
            } else {
                loadOriginalBot();
            }
            loadDatabase();
            log.Console.clearScreen();
            log.Console.printAsciiBlueAnonymousBot();
            log.Console.printAsciiLogo();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(BlueAnonymousBot.getInstance());
            new Thread(new ConsoleReader()).start();
            log.Console.initMessage("5.3.0");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-2);
        }
    }

    /**
     * loads test bot.
     * @throws IOException
     */
    private static void loadTestBot() throws IOException {
        Properties pr = new Properties();
        try (InputStream in = ClassLoader.getSystemResourceAsStream("test_config.properties")){
            pr.load(in);
        }
        BlueAnonymousBot.getInstance().setBotUsername(pr.getProperty("bot.username"));
        BlueAnonymousBot.getInstance().setBotToken(pr.getProperty("bot.token"));
    }

    /**
     * loads original bot.
     * @throws IOException
     */
    private static void loadOriginalBot() throws IOException {
        Properties pr = new Properties();
        try (InputStream in = ClassLoader.getSystemResourceAsStream("original_config.properties")){
            pr.load(in);
        }
        BlueAnonymousBot.getInstance().setBotUsername(pr.getProperty("bot.username"));
        BlueAnonymousBot.getInstance().setBotToken(pr.getProperty("bot.token"));
    }

    /**
     * connects the database.
     */
    private static void loadDatabase() {
        SQLiteUtils.getInstance();
    }
}
