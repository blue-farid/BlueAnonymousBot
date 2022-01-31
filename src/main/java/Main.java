import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.BlueAnonymousBot;

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
 * @version 1.0
 * <p>
 * Telegram_id: @blue_farid
 * github: https://github.com/blue-farid
 * @since 2021/8
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
