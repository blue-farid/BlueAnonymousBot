package telegram.command;

import inlineMenu.InlineHelpKeyBoard;
import properties.Property;
import telegram.BlueAnonymousBot;

public class HelpCommand extends Command {
    public HelpCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        sendMessage.setText(Property.MESSAGES_P.get("help"));
        sendMessage.setReplyMarkup(new InlineHelpKeyBoard());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
