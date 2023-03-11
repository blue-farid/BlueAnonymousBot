package com.blue_farid.blue_anonymous_bot.menu;

import com.blue_farid.blue_anonymous_bot.utils.LocaleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetGenderMenu extends Menu {

    private final MessageSource source;
    private final LocaleUtils localeUtils;

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
        keyboardFirstRow.add(source.getMessage("gender.male", null, localeUtils.getLocale()));
        keyboardFirstRow.add(source.getMessage("gender.female", null, localeUtils.getLocale()));
        keyboard.add(keyboardFirstRow);
        return keyboard;
    }
}
