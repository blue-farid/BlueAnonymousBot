package com.blue_farid.blue_anonymous_bot.menu;

import com.blue_farid.blue_anonymous_bot.telegram.command.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Negar Anabestani
 */

@RequiredArgsConstructor
@Component
public class MainMenu extends Menu {
    private final Environment env;

    @PostConstruct
    public void init() {
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(false);
        this.setKeyboard(creatKeyBoard());
    }

    @Override
    protected List<KeyboardRow> creatKeyBoard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardFirstRow.add(CommandConstant.ANONYMOUS_CONNECTION);
        keyboardRow2.add(CommandConstant.SPECIFIC_CONNECTION);
        keyboardRow3.add(CommandConstant.ANONYMOUS_LINK);
        keyboardRow3.add(CommandConstant.ANONYMOUS_TO_GROUP);
        keyboardRow4.add(CommandConstant.HELP);
        keyboardRow4.add(CommandConstant.SCORE);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        keyboard.add(keyboardRow3);
        keyboard.add(keyboardRow4);
        return keyboard;
    }
}
