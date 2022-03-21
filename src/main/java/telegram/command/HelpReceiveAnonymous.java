package telegram.command;

import inlineMenu.InlineBackToHelpMainMenuKeyBoard;
import properties.Property;
import telegram.BlueAnonymousBot;

public class HelpReceiveAnonymous extends Command{
    public HelpReceiveAnonymous(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        sendMessage.setText(Property.MESSAGES_P.get("help.receive_anonymous"));
        sendMessage.setReplyMarkup(InlineBackToHelpMainMenuKeyBoard.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
