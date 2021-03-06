package telegram;

import exception.BadInputException;
import model.Client;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import service.ClientService;
import telegram.handler.UpdateHandler;

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

    private BlueAnonymousBot() {
    }

    public static BlueAnonymousBot getInstance() {
        if (instance == null)
            instance = new BlueAnonymousBot();
        return instance;
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    /**
     * sets bot username
     *
     * @param botUsername the bot username
     */
    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    /**
     * sets bot token
     *
     * @param botToken the bot token.
     */
    public void setBotToken(String botToken) {
        this.botToken = botToken;
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
     *
     * @param update the update.
     */
    public void newRequestReceived(Update update) {
        ClientService.getInstance().addClient(new Client(update.getMessage().getFrom()));
    }

    /**
     * send message to client.
     *
     * @param sendMessage The Send message object.
     */
    public int executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
            return 0;
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
            return e.getErrorCode();
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
