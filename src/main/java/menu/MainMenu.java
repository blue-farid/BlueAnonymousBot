package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import properties.Commands;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Negar Anabestani
 */
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
        keyboardFirstRow.add(Commands.ANONYMOUS_CONNECTION.get());
        keyboardRow2.add(Commands.SPECIFIC_CONNECTION.get());
        keyboardRow3.add(Commands.ANONYMOUS_LINK.get());
        keyboardRow3.add(Commands.ANONYMOUS_TO_GROUP.get());
        keyboardRow4.add(Commands.HELP.get());
        keyboardRow4.add(Commands.SCORE.get());
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        keyboard.add(keyboardRow3);
        keyboard.add(keyboardRow4);
        return keyboard;
    }
}
