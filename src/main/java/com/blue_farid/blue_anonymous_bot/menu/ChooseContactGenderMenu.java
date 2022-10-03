package com.blue_farid.blue_anonymous_bot.menu;

import com.blue_farid.blue_anonymous_bot.telegram.command.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * The Choose Contact Sex menu
 *
 * @author Farid Masjedi
 */
@Component
@RequiredArgsConstructor
public class ChooseContactGenderMenu extends Menu {
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
        keyboardFirstRow.add(CommandConstant.CHOOSE_GENDER_MALE);
        keyboardFirstRow.add(CommandConstant.CHOOSE_GENDER_FEMALE);
        keyboardRow2.add(CommandConstant.CHOOSE_GENDER_BI);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardRow2);
        return keyboard;
    }
}
