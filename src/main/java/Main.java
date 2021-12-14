import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.BlueAnonymousBot;

/**
 * The BlueAnonymousBot Project
 * This is a Fake-Copy of a persian telegram ChatBot called 'برنامه ناشناس'
 * Written by two CE Student, Farid Masjedi and Negar Anabestani
 * @ AUT (AmirKabir University of Technology)
 *
 * @author Farid Masjedi
 * @author Negar Anabestani
 * @since 2021/8
 * @version 1.0
 *
 * Telegram_id: @blue_farid
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
