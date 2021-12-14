package telegram;

import dao.ClientDao;
import exception.BadInputException;
import model.Client;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.handler.UpdateHandler;

/**
 * the BlueAnonymousBot class
 */
public class BlueAnonymousBot extends TelegramLongPollingBot {
    private static BlueAnonymousBot instance;
    private final UpdateHandler updateHandler = new UpdateHandler();
    private boolean readyForGetMessage = false;
    private String savedMessage;

    private BlueAnonymousBot() {
    }

    public static BlueAnonymousBot getInstance() {
        if (instance == null)
            instance = new BlueAnonymousBot();
        return instance;
    }

    @Override
    public String getBotUsername() {
        return "BChaatt_Bot";
    }

    @Override
    public String getBotToken() {
        return "5054557221:AAGCrXNySIyvbyHyaa6JHwrk7UX1_I_7ObI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!isReadyForGetMessage()) {
            newRequestReceived(update);
            try {
                updateHandler.processUpdate(update);
            } catch (BadInputException e) {
                executeSendMessage(e.getSendMessage());
            }
        } else {
            this.savedMessage = update.getMessage().getText();
            setReadyForGetMessage(false);
        }
    }

    public void newRequestReceived(Update update) {
        ClientDao.getInstance().addClient(new Client(update.getMessage().getFrom(),
                update.getMessage().getChatId()));
        log.Console.printAllUsers();
    }

    public void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setReadyForGetMessage(boolean readyForGetMessage) {
        this.readyForGetMessage = readyForGetMessage;
    }

    public boolean isReadyForGetMessage() {
        return readyForGetMessage;
    }

    public String getSavedMessage() {
        try {
            return savedMessage;
        } finally {
            savedMessage = null;
        }
    }
}
