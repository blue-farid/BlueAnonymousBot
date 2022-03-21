package telegram.command;

import inlineMenu.InlineBackToHelpMainMenuKeyBoard;
import properties.Property;
import telegram.BlueAnonymousBot;

public class HelpWhatForCommand extends Command{
    public HelpWhatForCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        sendMessage.setText(Property.MESSAGES_P.get("help.what_for"));
        sendMessage.setReplyMarkup(InlineBackToHelpMainMenuKeyBoard.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
