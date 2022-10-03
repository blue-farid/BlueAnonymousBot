package com.blue_farid.blue_anonymous_bot.inlineMenu;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author negar anabestani
 */
@Component
@PropertySource("classpath:buttons.properties")
@RequiredArgsConstructor
public class InlineBackToHelpMainMenuKeyBoard extends InlineBlueKeyBoard {
    private final Environment env;

    @PostConstruct
    public void init() {
        addButtonToList(0, "بازگشت به صفحه راهنما" + "::" + env.getProperty("back_help_main_menu"));
    }
}
