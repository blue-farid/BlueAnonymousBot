package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import properties.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Negar anabestani
 */
public class CancelMenu extends Menu {
    public static CancelMenu instance;

    private CancelMenu() {
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(false);
        this.setKeyboard(creatKeyBoard());
    }

    public static CancelMenu getInstance() {
        if (instance == null) {
            instance = new CancelMenu();
        }
        return instance;
    }

    @Override
    protected List<KeyboardRow> creatKeyBoard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(Property.COMMANDS_P.get("cancel"));
        keyboard.add(keyboardFirstRow);
        return keyboard;
    }
}
