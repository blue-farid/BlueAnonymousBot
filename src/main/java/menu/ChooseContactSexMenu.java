package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import properties.Commands;

import java.util.ArrayList;
import java.util.List;

/**
 * The Choose Contact Sex menu
 * @author Farid Masjedi
 */
public class ChooseContactSexMenu extends Menu {

    private static ChooseContactSexMenu instance;

    private ChooseContactSexMenu() {
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(false);
        this.setKeyboard(creatKeyBoard());
    }

    public static ChooseContactSexMenu getInstance() {
        if (instance == null) {
            instance = new ChooseContactSexMenu();
        }
        return instance;
    }

    @Override
    protected List<KeyboardRow> creatKeyBoard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardFirstRow.add(Commands.CHOOSE_CONTACT_SEX_MALE.get());
        keyboardFirstRow.add(Commands.CHOOSE_CONTACT_SEX_MALE.get());
        keyboardRow2.add(Commands.CHOOSE_CONTACT_SEX_BI.get());
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        return keyboard;
    }
}
