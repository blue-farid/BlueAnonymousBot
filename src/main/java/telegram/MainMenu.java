package telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.command.Command;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends SendMessage {
    public MainMenu(){
        this.setReplyMarkup(getMainMenuKeyboard());
    }
    private ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardFirstRow.add(Command.getAnonymousConnection());
        keyboardRow2.add(Command.getSpecificConnection());
        keyboardRow3.add(Command.getAnonymousLink());
        keyboardRow3.add(Command.getAnonymousToGroup());
        keyboardRow4.add(Command.getHELP());
        keyboardRow4.add(Command.getSCORE());

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        keyboard.add(keyboardRow3);
        keyboard.add(keyboardRow4);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}
