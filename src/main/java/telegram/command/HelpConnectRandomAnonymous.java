package telegram.command;

import inlineMenu.InlineBackToHelpMainMenuKeyBoard;
import properties.Property;
import telegram.BlueAnonymousBot;

public class HelpConnectRandomAnonymous extends Command{
    public HelpConnectRandomAnonymous(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        sendMessage.setText(Property.MESSAGES_P.get("help.random_anonymous"));
        sendMessage.setReplyMarkup(InlineBackToHelpMainMenuKeyBoard.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
