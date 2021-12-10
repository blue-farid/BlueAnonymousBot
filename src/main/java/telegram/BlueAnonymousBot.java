package telegram;

import db.dao.ClientDao;
import exception.BadInputException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
        MainMenu sendMessage = new MainMenu();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        String message;
        try {
            message = this.updateHandler.processUpdate(update);
            if (message == null) {
                message = "I'M NOT DONE YET!";
            }
            sendMessage.setText(message);
        } catch (BadInputException e) {
            sendMessage.setText(e.getMessage());
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void newRequestReceived(Update update) {
        ClientDao.getInstance().addUser(update.getMessage().getFrom());
        log.Console.printNewRequestInfo(update);
    }

}
