package telegram.command;

import inlineMenu.InlineBackToHelpMainMenuKeyBoard;
import properties.Property;
import telegram.BlueAnonymousBot;

public class HelpAddToGroupCommand extends Command{
    public HelpAddToGroupCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        sendMessage.setText(Property.MESSAGES_P.get("help.add_to_group"));
        sendMessage.setReplyMarkup(InlineBackToHelpMainMenuKeyBoard.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
