import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.BlueAnonymousBot;

/**
 * The BlueAnonymousBot Project
 * This is a Fake-Copy of a persian telegram ChatBot called 'برنامه ناشناس'
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
            telegramBotsApi.registerBot(BlueAnonymousBot.getInstance());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
