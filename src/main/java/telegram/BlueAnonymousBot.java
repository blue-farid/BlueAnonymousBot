package telegram;

import dao.ClientDao;
import exception.BadInputException;
import model.Client;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.handler.UpdateHandler;
import utils.FileUtils;

import java.util.Properties;

/**
 * the BlueAnonymousBot class
 *
 * @author Farid Masjedi
 * @author Negar Anabestani
 * @author Alireza Jabbari
 */
public class BlueAnonymousBot extends TelegramLongPollingBot {

    private static BlueAnonymousBot instance;
    private final UpdateHandler updateHandler = new UpdateHandler();
    private String botUsername;
    private String botToken;

    private BlueAnonymousBot() {}

    public static BlueAnonymousBot getInstance() {
        if (instance == null)
            instance = new BlueAnonymousBot();
        return instance;
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            newRequestReceived(update);
        }
        if (update.hasMessage() || update.hasCallbackQuery()) {
            try {
                updateHandler.processUpdate(update);
            } catch (BadInputException e) {
                executeSendMessage(e.getSendMessage());
            }
        }

    }

    /**
     * do necessary things after receiving an update.
     * @param update the update.
     */
    public void newRequestReceived(Update update) {
        ClientDao.getInstance().addClient(new Client(update.getMessage().getFrom(),
                update.getMessage().getChatId()));
    }

    /**
     * send message to client.
     * @param sendMessage The Send message object.
     */
    public void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets bot token
     * @param botToken the bot token.
     */
    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    /**
     * sets bot username
     * @param botUsername the bot username
     */
    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

}
