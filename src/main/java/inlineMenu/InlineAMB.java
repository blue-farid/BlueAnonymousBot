package inlineMenu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InlineAMB extends InlineBlueKeyBoard {

    public InlineAMB(String senderDeepLink) {

       addButtonToList(0,"⛔️بلاک"+"::"+"block");
       addButtonToList(0,"✍\uD83C\uDFFB پاسخ"+"::"+ "answer " + senderDeepLink);
       setInlineBlueKeyBoard();
    }

}
