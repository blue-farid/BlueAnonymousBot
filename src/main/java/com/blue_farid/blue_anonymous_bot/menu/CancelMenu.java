package com.blue_farid.blue_anonymous_bot.menu;

import com.blue_farid.blue_anonymous_bot.telegram.command.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Negar anabestani
 */
@Component
@RequiredArgsConstructor
public class CancelMenu extends Menu {

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
        keyboardFirstRow.add(CommandConstant.CANCEL);
        keyboard.add(keyboardFirstRow);
        return keyboard;
    }
}
