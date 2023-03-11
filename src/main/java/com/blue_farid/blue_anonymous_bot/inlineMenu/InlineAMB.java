package com.blue_farid.blue_anonymous_bot.inlineMenu;

/**
 * answer and block inline keyboard for anonymous messages
 */
public class InlineAMB extends InlineBlueKeyBoard {

    public InlineAMB(long senderId, int messageId) {

        addButtonToList(0, "⛔️بلاک" + "::" + "block " + senderId);
        addButtonToList(0, "✍\uD83C\uDFFB پاسخ" + "::" + "answer " + senderId + " " + messageId);
        setInlineBlueKeyBoard();
    }

}
