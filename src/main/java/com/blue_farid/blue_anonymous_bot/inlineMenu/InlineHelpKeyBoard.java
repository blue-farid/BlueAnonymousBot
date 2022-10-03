package com.blue_farid.blue_anonymous_bot.inlineMenu;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Negar Anabestani
 */

@Component
@PropertySource("classpath:buttons.properties")
@RequiredArgsConstructor
public class InlineHelpKeyBoard extends InlineBlueKeyBoard {
    private final Environment env;

    @PostConstruct
    public void init() {
        addButtonToList(0, "\uD83D\uDC48این روبات چیه؟ به چه درد میخوره؟" + "::" + env.getProperty("help_what_for"));
        addButtonToList(1, "\uD83D\uDC48چطوری به یه ناشناس تصادفی وصل بشم؟" + "::" + env.getProperty("help_connect_random_anonymous"));
        addButtonToList(2, "\uD83D\uDC48 چطوری نسخه ی رایگان" + "VIP" + " رو فعال کنم؟" + "::" + env.getProperty("help_free_vip"));
        addButtonToList(3, "\uD83D\uDC48چطوری به مخاطب خاصم وصل بشم؟" + "::" + env.getProperty("help_specific_connection"));
        addButtonToList(4, "\uD83D\uDC48چطوری پیام ناشناس دریافت کنم؟" + "::" + env.getProperty("help_receive_anonymous_message"));
        addButtonToList(5, "\uD83D\uDC48چطوری به یه گروه پیام ناشناس بفرستم؟" + "::" + env.getProperty("help_send_anonymous_message_group"));
        setInlineBlueKeyBoard();
    }
}
