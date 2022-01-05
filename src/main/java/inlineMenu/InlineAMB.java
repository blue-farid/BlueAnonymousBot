package inlineMenu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineAMB extends InlineKeyboardMarkup {

    public InlineAMB(String senderDeepLink) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(creatButton("⛔️بلاک", "block"));
        rowInline.add(creatButton("✍\uD83C\uDFFB پاسخ", "answer " + senderDeepLink));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        this.setKeyboard(rowsInline);
    }

    private InlineKeyboardButton creatButton(String text, String callBack) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callBack);
        return inlineKeyboardButton;
    }
}
