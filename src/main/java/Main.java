import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.BlueAnonymousBot;

/**
 * The BlueAnonymousBot Project
 * This is Fake-Copy of a persian telegram ChatBot called 'برنامه ناشناس'
 * Written by two Student, Farid Masjedi and Negar Anabestani
 * @ AUT (AmirKabir University of Technology)
 *
 * @author blue-farid
 * @author negar-anabestani
 * @since 2021/8
 * @version 1.0
 *
 * github: https://github.com/blue-farid
 *
 */
public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            BlueAnonymousBot bot = new BlueAnonymousBot();
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
