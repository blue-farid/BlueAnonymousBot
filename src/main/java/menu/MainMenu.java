package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.BlueAnonymousBot;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Menu {
    public static MainMenu instance;

    private MainMenu() {
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(false);
        this.setKeyboard(creatKeyBoard());
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    @Override
    protected List<KeyboardRow> creatKeyBoard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardFirstRow.add(BlueAnonymousBot.getInstance().
                getProperties().getProperty("command.anonymous_connection"));
        keyboardRow2.add(BlueAnonymousBot.getInstance().
                getProperties().getProperty("command.specific_connection"));
        keyboardRow3.add(BlueAnonymousBot.getInstance().
                getProperties().getProperty("command.anonymous_link"));
        keyboardRow3.add(BlueAnonymousBot.getInstance().
                getProperties().getProperty("command.anonymous_to_group"));
        keyboardRow4.add(BlueAnonymousBot.getInstance().
                getProperties().getProperty("command.help"));
        keyboardRow4.add(BlueAnonymousBot.getInstance().
                getProperties().getProperty("command.score"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        keyboard.add(keyboardRow3);
        keyboard.add(keyboardRow4);
        return keyboard;
    }
}
