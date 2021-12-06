package telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.UpdateHandler;

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
        updateHandler.processUpdate(update);
    }
}
