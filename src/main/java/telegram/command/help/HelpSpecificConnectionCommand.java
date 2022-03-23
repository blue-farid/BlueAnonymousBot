package telegram.command.help;

import telegram.command.Command;

/**
 * @author Negar Anabestani
 */
public class HelpSpecificConnectionCommand extends Command {
    public HelpSpecificConnectionCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
//        sendMessage.setText(Property.MESSAGES_P.get("help.specific_connection"));
//        sendMessage.setReplyMarkup(InlineBackToHelpMainMenuKeyBoard.getInstance());
//        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
