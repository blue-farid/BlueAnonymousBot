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
    private final UpdateHandler updateHandler = new UpdateHandler();

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
        newRequestReceived(update);
        SendMessage sendMessage;
        try {
            sendMessage = this.updateHandler.processUpdate(update);
        } catch (BadInputException e) {
            sendMessage = e.getSendMessage();
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void newRequestReceived(Update update) {
        ClientDao.getInstance().addClient(new Client(update.getMessage().getFrom()));
        log.Console.printNewRequestInfo(update);
    }

}
