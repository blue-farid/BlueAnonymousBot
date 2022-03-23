package telegram.command.help;

import telegram.command.Command;

/**
 * @author Negar Anabestani
 */
public class HelpReceiveAnonymousCommand extends Command {
    public HelpReceiveAnonymousCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
//        sendMessage.setText(Property.MESSAGES_P.get("help.receive_anonymous"));
//        sendMessage.setReplyMarkup(InlineBackToHelpMainMenuKeyBoard.getInstance());
//        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
