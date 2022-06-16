package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import properties.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alireza Jabbari
 */

public class GenderSettingsMenu extends Menu{
    public static GenderSettingsMenu instance;

    private GenderSettingsMenu(){
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(false);
        this.setKeyboard(createKeyBoard());
    }

    public static GenderSettingsMenu getInstance() {
        if (instance == null) {
            instance = new GenderSettingsMenu();
        }
        return instance;
    }

    @Override
    protected List<KeyboardRow> createKeyBoard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(Property.COMMANDS_P.get("im_girl"));
        keyboardFirstRow.add(Property.COMMANDS_P.get("im_boy"));
        keyboard.add(keyboardFirstRow);
        return keyboard;
    }
}
