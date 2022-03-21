package telegram.command;

import inlineMenu.InlineBackToHelpMainMenuKeyBoard;
import properties.Property;
import telegram.BlueAnonymousBot;

public class HelpSpecificConnection extends Command{
    public HelpSpecificConnection(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        sendMessage.setText(Property.MESSAGES_P.get("help.specific_connection"));
        sendMessage.setReplyMarkup(InlineBackToHelpMainMenuKeyBoard.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
