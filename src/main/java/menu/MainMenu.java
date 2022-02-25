package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import properties.Property;
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
        keyboardFirstRow.add(Property.COMMANDS_P.get("anonymous_connection"));
        keyboardRow2.add(Property.COMMANDS_P.get("specific_connection"));
        keyboardRow3.add(Property.COMMANDS_P.get("anonymous_link"));
        keyboardRow3.add(Property.COMMANDS_P.get("anonymous_to_group"));
        keyboardRow4.add(Property.COMMANDS_P.get("help"));
        keyboardRow4.add(Property.COMMANDS_P.get("score"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        keyboard.add(keyboardRow3);
        keyboard.add(keyboardRow4);
        return keyboard;
    }
}
