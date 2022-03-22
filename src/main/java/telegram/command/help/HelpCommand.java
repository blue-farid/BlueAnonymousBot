package telegram.command.help;

import inlineMenu.InlineHelpKeyBoard;
import properties.Property;
import telegram.BlueAnonymousBot;
import telegram.command.Command;

public class HelpCommand extends Command {
    public HelpCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        sendMessage.setText(Property.MESSAGES_P.get("help"));
        sendMessage.setReplyMarkup(InlineHelpKeyBoard.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
