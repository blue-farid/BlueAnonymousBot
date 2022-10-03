package com.blue_farid.blue_anonymous_bot.inlineMenu;

import com.blue_farid.blue_anonymous_bot.telegram.command.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author negar anabestani
 */
@Component
@RequiredArgsConstructor
public class InlineBackToHelpMainMenuKeyBoard extends InlineBlueKeyBoard {
    private final Environment env;

    @PostConstruct
    public void init() {
        addButtonToList(0, "بازگشت به صفحه راهنما" + "::" + CommandConstant.BACK_HELP_MAIN_MENU);
    }
}
