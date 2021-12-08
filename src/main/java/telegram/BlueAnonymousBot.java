package telegram;

import exception.BadInputException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.UpdateHandler;

/**
 * the BluAnonymousBot class
 */
import java.util.ArrayList;
import java.util.List;

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
        getMainMenuKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(getMainMenuKeyboard());
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        String message;
        try {
            message = this.updateHandler.processUpdate(update);
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

    private static ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("fuck it");
        keyboardFirstRow.add("fuck life");
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        keyboardSecondRow.add(getSettingsCommand(language));
//        keyboardSecondRow.add(getRateCommand(language));
        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }


}
