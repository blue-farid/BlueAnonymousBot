package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.BlueAnonymousBot;
import java.util.ArrayList;
import java.util.List;

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
        keyboardFirstRow.add(BlueAnonymousBot.getInstance().
                getProperty("command.choose_contact_sex_male"));
        keyboardFirstRow.add(BlueAnonymousBot.getInstance().
                getProperty("command.choose_contact_sex_female"));
        keyboardRow2.add(BlueAnonymousBot.getInstance()
                .getProperty("command.choose_contact_sex_bi"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        return keyboard;
    }
}
