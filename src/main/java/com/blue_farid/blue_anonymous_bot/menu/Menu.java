package com.blue_farid.blue_anonymous_bot.menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

/**
 * @author Negar Anabestani
 */

public abstract class Menu extends ReplyKeyboardMarkup {
    protected abstract List<KeyboardRow> creatKeyBoard();
}
