package telegram;

import exception.BadInputException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.UpdateHandler;

import java.util.ArrayList;
import java.util.List;

public class  BlueAnonymousBot extends TelegramLongPollingBot {
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
        MainMenu sendMessage = new MainMenu();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        String message;
        try {
            message = updateHandler.processUpdate(update);
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




}
