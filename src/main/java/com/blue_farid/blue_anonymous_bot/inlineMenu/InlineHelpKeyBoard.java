package com.blue_farid.blue_anonymous_bot.inlineMenu;

import com.blue_farid.blue_anonymous_bot.telegram.command.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 */

@Component
@RequiredArgsConstructor
public class InlineHelpKeyBoard extends InlineBlueKeyBoard {

    @PostConstruct
    public void init() {
        addButtonToList(0, "\uD83D\uDC48این روبات چیه؟ به چه درد میخوره؟" + "::" + CommandConstant.WHAT_FOR_HELP);
        addButtonToList(1, "\uD83D\uDC48چطوری به یه ناشناس تصادفی وصل بشم؟" + "::" + CommandConstant.CONNECT_RANDOM_ANONYMOUS_HELP);
        addButtonToList(2, "\uD83D\uDC48 چطوری نسخه ی رایگان" + "VIP" + " رو فعال کنم؟" + "::" + CommandConstant.FREE_VIP_HELP);
        addButtonToList(3, "\uD83D\uDC48چطوری به مخاطب خاصم وصل بشم؟" + "::" + CommandConstant.SPECIFIC_CONNECTION_HELP);
        addButtonToList(4, "\uD83D\uDC48چطوری پیام ناشناس دریافت کنم؟" + "::" + CommandConstant.RECEIVE_ANONYMOUS_MESSAGE_HELP);
        addButtonToList(5, "\uD83D\uDC48چطوری به یه گروه پیام ناشناس بفرستم؟" + "::" + CommandConstant.SEND_ANONYMOUS_MESSAGE_GROUP_HELP);
        setInlineBlueKeyBoard();
    }
}
