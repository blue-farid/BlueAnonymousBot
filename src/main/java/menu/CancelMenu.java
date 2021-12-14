package menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.command.Command;

import java.util.ArrayList;
import java.util.List;

public class CancelMenu extends Menu{
    public static CancelMenu instance;
    public static CancelMenu getInstance() {
        if (instance == null) {
            instance = new CancelMenu();
        }
        return instance;
    }
    private CancelMenu(){
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(false);
        this.setKeyboard(creatKeyBoard());
    }
    @Override
    protected List<KeyboardRow> creatKeyBoard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(Command.getCANCEL());
        keyboard.add(keyboardFirstRow);
        return keyboard;
    }
}
